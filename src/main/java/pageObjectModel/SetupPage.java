package pageObjectModel;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import E2EProjectFramework.Framework.BrowserAction;
import resources.base;

public class SetupPage extends base {
	public WebDriver driver;
	public BrowserAction browserAction;
	public SetupPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		browserAction = new BrowserAction(driver);
	}
	//By Elements
	public By appLauncher = By.xpath("//div[@class='slds-icon-waffle']");
	public By appSearchbox= By.xpath("//input[@placeholder='Search apps and items...']");
	public By ServiceApp = By.xpath("//a[@data-label='Service']/div/lightning-formatted-rich-text");
	public By AppName = By.xpath("//*[contains(@class,'appName')]");
	public By ABBFSLEngWeb=By.xpath("//a[@data-label='ABB FSL EngWeb']");
	//Webelement methods
	
	public WebElement appLauncher() {
		return driver.findElement(appLauncher);
	}
	public WebElement ServiceApp() {
		return driver.findElement(ServiceApp);
	}
	public WebElement AppName() {
		return driver.findElement(AppName);
	}

	
	//page specific methods
	
	public void openSalesforceApp() throws IOException {
		browserAction.waitforElement(AppName);
		if(!browserAction.findElementBy(AppName).getText().equalsIgnoreCase(getGlobalVariable("salesforceApp"))) {
    	switch(getGlobalVariable("salesforceApp").toLowerCase()){
    	case "service":
    		browserAction.waitforElement(appLauncher);
    		browserAction.clickElement(appLauncher);
    		browserAction.waitforElement(ServiceApp);
    		browserAction.clickElement(ServiceApp);
    		break;
    	case "abb fsl engweb":
    		browserAction.waitforElement(appLauncher);
    		browserAction.clickElement(appLauncher);
    		browserAction.clickElement(appSearchbox);
    		browserAction.enterTextBy(appSearchbox, getGlobalVariable("salesforceApp"));
    		browserAction.waitforElement(ABBFSLEngWeb);
    		browserAction.clickElement(ABBFSLEngWeb);
    		break;
    	
    	}
		}
	}
	//page specific methods
}
