package E2EProjectFramework.Framework;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import E2EProjectFramework.Framework.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectModel.EngServiceAppoinment;
import pageObjectModel.SalesforcePages;
import resources.base;
import resources.getDataFromExcel;

public class createTSEMultiCountry extends base {
	public WebDriver driver;
	public SalesforcePages salesforcePages;
	public BrowserAction browserAction;
	public Object[][] dataArray;
	public List<List<String>> data;
	getDataFromExcel getDataFromExcel;

	@BeforeTest
	public void excelDataProvider() throws IOException {
		String excelFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\Data.xlsx";
		getDataFromExcel = new getDataFromExcel(excelFilePath);
		data = getDataFromExcel.getSheetData("MultiCountries_WO");
		getDataFromExcel.close();
		driver = initializeDriver("browser");
		browserAction = new BrowserAction(driver);
		// driver=initializeRemoteDriver("chrome");
		salesforcePages = new SalesforcePages(driver);
	}

	@DataProvider(name = "dataProvider")
	public Object[][] dataProvider() {
		List<List<String>> data2 = getDataFromExcel.getSheetData("MultiCountries_WO");
		Object[][] dataArray = new Object[data2.size() - 1][];
		for (int i = 1, j = 0; i < data2.size(); i++, j++) {
			dataArray[j] = data2.get(i).toArray(new String[0]);
		}
		return dataArray;
	}

	@Test(dataProvider = "dataProvider")
	public void createTSEforMultiCountries(String country, String username, String password, String serviceAppointment,
			String subject, String date, String startTime, String duration, String endTime, String internalHourlyRate,
			String timeEntryType, String workOrderLineItem, String allowanceType, String allowanceAmount,
			String activityType, String absenceType) throws IOException, InterruptedException {
		List<List<String>> data2 = getDataFromExcel.getSheetData("EngServiceAppointmentTab");
		getDataFromExcel.close();
		salesforcePages.LoginPage().applicationLogin(username, password);
		salesforcePages.SetupPage().openSalesforceApp();
		browserAction.waitforElement(salesforcePages.EngServiceAppoinment().EngServiceAppointmentTab);
		browserAction.clickElement(salesforcePages.EngServiceAppoinment().EngServiceAppointmentTab);
		browserAction.findElementBy(By.xpath("//button[@title='" + serviceAppointment + "']"));
		browserAction.clickElement(By.xpath("//button[@title='" + serviceAppointment + "']"));
		browserAction.waitforElement(By.xpath("//button[@title='" + serviceAppointment + "']"));
		salesforcePages.WorkOrderNavigation().WorkOrderTimeBtn().click();
		browserAction.waitforElement(salesforcePages.WorkOrderTimesheet().actualHoursColmnBy);
		salesforcePages.WorkOrderTimesheet().createNewTSE().click();
		ArrayList<String> CreateTSE = getDataFromExcel.getData(data2, country);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		browserAction.waitforElement(salesforcePages.WorkOrderTimesheet().inlineFormBy);
		List<WebElement> tseFields = browserAction
				.findElementsBy(By.xpath("//div[@class='tbody table-row active']//div[@class='table-cell']"));
		for (WebElement tseField : tseFields) {
			String attribute;
			WebElement labels = tseField.findElement(By.xpath(".//*[@class='label']"));
			String label = (String) js.executeScript("return arguments[0].textContent;", labels);
			try {
				if(label.toLowerCase().equals("activity type")) {
					throw new RuntimeException();
				}
				attribute = tseField.findElement(By.xpath(".//*[@required]")).getAttribute("required");
			} catch (Exception e) {
				attribute = null;
			}
			switch (label.toLowerCase()) {
			case "subject":
				if (attribute != null) {
					Assert.assertTrue((attribute != null && subject.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
					browserAction
							.findElementBy(By.xpath("//div[@class='label' and text()='Subject']/parent::div //input"))
							.sendKeys(CreateTSE.get(1));
				} else
					Assert.assertTrue((attribute == null && subject.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "date":
				if (attribute != null) {
					browserAction.findElementBy(By.xpath("//input[@class='slds-input picker']")).click();
					browserAction.findElementBy(By.xpath("//div[@class='label' and text()='Date']/parent::div //input"))
							.sendKeys(Keys.ENTER);
					Assert.assertTrue((attribute != null && date.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				} else
					Assert.assertTrue((attribute == null && date.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "start time":
				if (attribute != null)
					Assert.assertTrue((attribute != null && startTime.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && startTime.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "duration (hours)":
				if (attribute != null)
					Assert.assertTrue((attribute != null && duration.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && duration.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "internal hourly rate":
				if (attribute != null)
					Assert.assertTrue((attribute != null && internalHourlyRate.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && internalHourlyRate.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "time entry type":
				if (attribute != null) {
					Assert.assertTrue((attribute != null && timeEntryType.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
					Select TimeEntryType = new Select(browserAction.findElementBy(By.xpath("//select")));
					TimeEntryType.selectByValue(CreateTSE.get(2));
				} else
					Assert.assertTrue((attribute == null && internalHourlyRate.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "work order line item":
				if (attribute != null)
					Assert.assertTrue((attribute != null && workOrderLineItem.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && internalHourlyRate.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "allowance type":
				if (attribute != null)
					Assert.assertTrue((attribute != null && allowanceType.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && allowanceType.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "allowance amount":
				if (attribute != null)
					Assert.assertTrue((attribute != null && allowanceAmount.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && allowanceAmount.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "activity type":
				if (attribute != null)
					Assert.assertTrue((attribute != null && activityType.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
				else
					Assert.assertTrue((attribute == null && activityType.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			case "absence type/attendance type":
				if (attribute != null) {
					Assert.assertTrue((attribute != null && absenceType.equalsIgnoreCase("Mandatory")),
							"Field " + label + " is Mandatory for the country " + country);
					 browserAction.clickElement(By.xpath(
							"//*[@data-sfapi='FSLABB_Absence_Type_ref__c']//input"));
					 WebElement absense=browserAction.findElementBy(By.xpath("//li[@data-sfid='aEi3Y000000TNB3SAO']"));
					js.executeScript("return arguments[0].click();", absense);
				} else
					Assert.assertTrue((attribute == null && absenceType.equalsIgnoreCase("Visible")),
							"Field " + label + " is visible for the country " + country);
				break;
			}
		}	
		  WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		  //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='save']")));
		  browserAction.clickElement(By.xpath("//button[@name='save']"));
		  wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@name='save']")));
	}

	@AfterMethod
	public void logOut() {
		browserAction.clickElement(By.xpath("//a[contains(@href,'logout')]"));
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
		;
	}
}
