package pageObjectModel;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import E2EProjectFramework.Framework.BrowserAction;

public class WorkOrderTimesheet {
	public WebDriver driver;
	public BrowserAction browserAction;
	public WebDriverWait wait;
	public WorkOrderTimesheet(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		browserAction = new BrowserAction(driver);
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	//By elements
	public By actualHoursColmnBy= By.xpath("//u[text()='Actual Hours']");
	public By createNewTSEBy = By.xpath("//button[@title='Create New']");
	public By inlineFormBy = By.xpath("//div[contains(@class,'inline-form-container')]");
	//By Elements for TSE create 
	public By subjectBy =By.xpath("//div[@class='label' and text()='Subject']/parent::div //input");
	
	//Webelements
	public WebElement createNewTSE() {
		return browserAction.findElementBy(createNewTSEBy);
	}
	public void createTSE(ArrayList<String> CreateTSE) {
		browserAction.waitforElement(actualHoursColmnBy);
		createNewTSE().click();
		browserAction.waitforElement(inlineFormBy);
		browserAction.findElementBy(By.xpath("//div[@class='label' and text()='Subject']/parent::div //input")).sendKeys(CreateTSE.get(1));
		Select TimeEntryType = new Select(browserAction.findElementBy(By.xpath("//select")));
		TimeEntryType.selectByValue(CreateTSE.get(2));
		browserAction.findElementBy(By.xpath("//input[@class='slds-input picker']")).click();
		browserAction.findElementBy(By.xpath("//div[@class='label' and text()='Date']/parent::div //input")).sendKeys(Keys.ENTER);	
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//button[@name='save']")));
		 * browserAction.clickElement(By.xpath("//button[@name='save']"));
		 */
	}
}
