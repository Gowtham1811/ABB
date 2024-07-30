package pageObjectModel;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import E2EProjectFramework.Framework.BrowserAction;

public class WorkOrderNavigation {
	public WebDriver driver;
	public BrowserAction browserAction;
	public WebDriverWait wait;
	public WorkOrderNavigation(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		browserAction = new BrowserAction(driver);
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	//By Element
	public By WorkOrderTimeBtnBy = By.xpath("//button[text()='Work Order Time']");
	
	//Webelement
	public WebElement WorkOrderTimeBtn() {
		return browserAction.findElementBy(WorkOrderTimeBtnBy);
	}
}
