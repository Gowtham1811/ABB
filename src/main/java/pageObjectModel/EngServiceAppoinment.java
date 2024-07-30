package pageObjectModel;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import E2EProjectFramework.Framework.BrowserAction;
import resources.base;

public class EngServiceAppoinment extends base{
	public WebDriver driver;
	public BrowserAction browserAction;
	public WebDriverWait wait;
	public EngServiceAppoinment(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		browserAction = new BrowserAction(driver);
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	//By Elements for ListView
	public By EngServiceAppointmentTab = By.xpath("//a[@title='Eng Service Appointments']");
	public By pageTitle = By.xpath("//h2[contains(@class,'title')] //*[text()='Service Appointments']");
	public By listViewTitle = By.xpath("//div[@class='allSALabel addPadding']/h1");
	public By listViewOptions = By.xpath("//button[contains(@aria-label,'Service Appointments')]");
	public By searchServiceAppointmentBy = By.xpath("//input[@placeholder='Search Service Appointment']");
	public By listViewValuesBy = By.xpath("//button[contains(@aria-label,'Service Appointments')]/parent::div/following-sibling::div //span[@title]");
	public By tableHeadersBy = By.xpath("//thead/tr/th //span[@title]");
	public By WeeklyTimesheetBtn = By.xpath("//button[@title='Weekly Timesheet']");
	//WebElement
	public WebElement searchServiceAppointment() {
		return browserAction.findElementBy(searchServiceAppointmentBy);
	}
	public WebElement searchServiceAppointment(String appointmentNumber) {
		return browserAction.findElementBy(By.xpath("//button[text()='"+appointmentNumber+"']"));
	}
	//WebElement methods
	@SuppressWarnings("unchecked")
	public void searchResults(String searchText) {
		List<WebElement> results=null;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
			if(searchText.contains("SA-"))
			{
				wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='"+searchText+"']")));
				results = driver.findElements(By.xpath("//button[text()='"+searchText+"']"));
			}
			else {
				if(searchText.contains("-"))
				{
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(@data-cell-value,'"+searchText+"')]")));
					results = driver.findElements(By.xpath("//td[contains(@data-cell-value,'"+searchText+"')]"));
				}
				else {
				wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//lightning-base-formatted-text[contains(text(),'"+searchText+"')]")));
				results=driver.findElements(By.xpath("//lightning-base-formatted-text[contains(text(),'"+searchText+"')]"));
				}
			}
		assertEquals(true, results.size()>0);
	}
	public List<String> listViewValues() {
		List<String> listValues = null;
		List<WebElement> listViewValuesElements= browserAction.findElementsBy(listViewValuesBy);
		for(WebElement webElement: listViewValuesElements) {
			listValues.add(webElement.getAttribute("title"));
				}
		return listValues;
	}
	public ArrayList<String> tableHeaders() {
		List<WebElement> listOfColumnHeadersElements=browserAction.findElementsBy(tableHeadersBy);
		ArrayList<String> listOfColumnHeaders = new ArrayList<String>();
		for(WebElement element:listOfColumnHeadersElements) {
			listOfColumnHeaders.add(element.getAttribute("title"));
		}
		return listOfColumnHeaders;
	}
	//methos specific to this page
	public ArrayList<String> getlistViewValues() {
		List<WebElement> listViewOptionsElements = browserAction.findElementsBy(listViewValuesBy);
		ArrayList<String> listViewVlaues = new ArrayList<String>();
		for(WebElement element:listViewOptionsElements) {
			listViewVlaues.add(element.getAttribute("title"));
		}
		return listViewVlaues;
		
	}
}
