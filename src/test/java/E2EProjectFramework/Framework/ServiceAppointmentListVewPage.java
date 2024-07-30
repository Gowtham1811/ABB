package E2EProjectFramework.Framework;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import  E2EProjectFramework.Framework.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjectModel.EngServiceAppoinment;
import pageObjectModel.SalesforcePages;
import resources.base;
import resources.getDataFromExcel;

public class ServiceAppointmentListVewPage extends base {
	public WebDriver driver;
	public SalesforcePages salesforcePages;
	public BrowserAction browserAction;
	public Object[][] dataArray;
	public List<List<String>> data;
	getDataFromExcel getDataFromExcel;
	@BeforeTest
	public void excelDataProvider() throws IOException {
        String excelFilePath = System.getProperty("user.dir")+"\\src\\main\\java\\resources\\Data.xlsx";
        getDataFromExcel = new getDataFromExcel(excelFilePath);
        data = getDataFromExcel.getSheetData("EngServiceAppointmentTab");
        getDataFromExcel.close();
		driver = initializeDriver("browser");
		browserAction = new BrowserAction(driver);
		//driver=initializeRemoteDriver("chrome");
		salesforcePages = new SalesforcePages(driver);
		salesforcePages.LoginPage().appLogin();
		salesforcePages.SetupPage().openSalesforceApp();
    }
	
	@BeforeMethod
	public void selectServiceAppBeforeTest() throws IOException {
		browserAction.waitforElement(salesforcePages.EngServiceAppoinment().EngServiceAppointmentTab);
		browserAction.clickElement(salesforcePages.EngServiceAppoinment().EngServiceAppointmentTab);
	}
	
	@Test(enabled = true)
	public void verifyListViewOptions() {
		browserAction.clickElement(salesforcePages.EngServiceAppoinment().listViewOptions);
		ArrayList<String> listViewValues = salesforcePages.EngServiceAppoinment().getlistViewValues();
		getDataFromExcel.validateData(data,listViewValues,"ListViewOptions");
	}
	@Test(enabled = true)
	public void verifyColumnHeaders() {
		ArrayList<String> listViewValues = salesforcePages.EngServiceAppoinment().tableHeaders();
		getDataFromExcel.validateData(data,listViewValues,"TableHeaders");
	}
	@Test(enabled = true)
	public void verifySearchServiceAppointment() {
		ArrayList<String> searchData = getDataFromExcel.getData(data,"SearchData");
		for(String search: searchData) {
			salesforcePages.EngServiceAppoinment().searchServiceAppointment().click();
			salesforcePages.EngServiceAppoinment().searchServiceAppointment().sendKeys(search);
			salesforcePages.EngServiceAppoinment().searchResults(search);
			salesforcePages.EngServiceAppoinment().searchServiceAppointment().clear();
		}
	}
	@Test(enabled = false)
	public void createTSE() throws InterruptedException {
		//browserAction.findElementBy(salesforcePages.EngServiceAppoinment().EngServiceAppointmentTab).click();
		ArrayList<String> CreateTSE = getDataFromExcel.getData(data,"CreateTSE");
		browserAction.findElementBy(By.xpath("//button[@title='"+ CreateTSE.get(0) +"']"));
		browserAction.clickElement(By.xpath("//button[@title='"+ CreateTSE.get(0) +"']"));
		browserAction.waitforElement(By.xpath("//*[@title='"+CreateTSE.get(0)+"']"));
		salesforcePages.WorkOrderNavigation().WorkOrderTimeBtn().click();	
		browserAction.waitforElement(salesforcePages.WorkOrderTimesheet().actualHoursColmnBy);
		salesforcePages.WorkOrderTimesheet().createNewTSE().click();
		browserAction.waitforElement(salesforcePages.WorkOrderTimesheet().inlineFormBy);
		browserAction.findElementBy(By.xpath("//div[@class='label' and text()='Subject']/parent::div //input")).sendKeys(CreateTSE.get(1));
		Select TimeEntryType = new Select(browserAction.findElementBy(By.xpath("//select")));
		TimeEntryType.selectByValue(CreateTSE.get(2));
		browserAction.findElementBy(By.xpath("//input[@class='slds-input picker']")).click();
		browserAction.findElementBy(By.xpath("//div[@class='label' and text()='Date']/parent::div //input")).sendKeys(Keys.ENTER);	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='save']")));
		browserAction.clickElement(By.xpath("//button[@name='save']"));
	}
	
	@Test(enabled = true)
	public void deleteTSE() throws InterruptedException {
		ArrayList<String> CreateTSE = resources.getDataFromExcel.getData(data,"CreateTSE");
		browserAction.findElementBy(By.xpath("//button[@title='"+ CreateTSE.get(0) +"']"));
		browserAction.clickElement(By.xpath("//button[@title='"+ CreateTSE.get(0) +"']"));
		browserAction.waitforElement(By.xpath("//*[@title='"+CreateTSE.get(0)+"']"));
		salesforcePages.WorkOrderNavigation().WorkOrderTimeBtn().click();	
		browserAction.waitforElement(salesforcePages.WorkOrderTimesheet().actualHoursColmnBy);
		browserAction.waitforElement(By.xpath("//a[contains(text(),'Jul 30')]"));
		browserAction.findElementBy(By.xpath("//a[contains(text(),'Jul 30')]/ancestor::p //span[@class='timesheet_expand_collapsed']")).click();
		browserAction.clickElement(By.xpath("//lightning-button-icon[@data-form-view='delete']"));
		browserAction.clickElement(By.xpath("//button[text()='DELETE']"));
		Thread.sleep(3000);
	}

	@AfterTest
	public void tearDown() {
	driver.quit();;
	}
}
