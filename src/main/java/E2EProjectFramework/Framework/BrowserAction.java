package E2EProjectFramework.Framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjectModel.SalesforcePages;

public class BrowserAction  {
	
	public WebDriver driver;
public BrowserAction(WebDriver driver) {
		// TODO Auto-generated constructor stub
	this.driver=driver;
	}

	public void waitforElement(By Locator) {
		try {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(Locator));
		}
		catch(Exception e) {
			
		}
	}
//	public String getGlobalVariable(String variable) throws IOException 
//	{
//    FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\gloablVariables.properties");
//	Properties prop = new Properties();
//	prop.load(fis);
//	String globalVariable=prop.getProperty(variable);
//	return globalVariable;
//	}
	public void clickElement(By Element) {
    	WebElement webElement=findElementBy(Element);
    	try {
    		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.elementToBeClickable(webElement));
    		webElement.click();
    	}
    	catch(JavascriptException je) {
    		jClick(webElement);
    	}
	
	}
	public void jClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		
	}
	public WebElement findElementBy(By Element) {
    	WebElement webElement = null;
    	try {
    		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
    		webElement=driver.findElement(Element);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		return webElement;
    	
    }
	public List<WebElement> findElementsBy(By Element) {
    	List<WebElement> webElements = null;
    	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));;
    	try {
    		webElements=driver.findElements(Element);   		
    	}
    	catch(Exception e) {
    		wait.until(ExpectedConditions.presenceOfElementLocated(Element));
    		webElements=driver.findElements(Element);  
    	}
		return webElements;
    	
    }
	public void enterTextBy(By Element, String Text) {
		findElementBy(Element).sendKeys(Text);
	}
	public void enterTextByWithEnter(By Element, String Text) {
		findElementBy(Element).sendKeys(Text+ Keys.ENTER);
	}
	public void loginAs(String userName) {
		waitforElement(By.xpath("//div[@title='Show Salesforce details (Alt+I / Shift+Alt+I)']"));
		clickElement(By.xpath("//div[@title='Show Salesforce details (Alt+I / Shift+Alt+I)']"));
		clickElement(By.xpath("//li[text()='Users']"));
		enterTextByWithEnter(By.xpath("//input[contains(@placeholder,'Username')]"),userName);
		clickElement(By.xpath("//a[text()='Try login as']"));
		waitforElement(By.xpath("//span[contains(text(),'"+userName+"')]"));
	}
}
