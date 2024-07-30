package pageObjectModel;
import  E2EProjectFramework.Framework.*;
import resources.base;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends base{
	
	public WebDriver driver;
	public BrowserAction browserAction;
public LoginPage(WebDriver driver) {
		this.driver=driver;
		browserAction =new BrowserAction(driver);
		// TODO Auto-generated constructor stub
	}
	//By Elements
	public By userName= By.xpath("//*[@id='username']");
	public By passWord= By.xpath("//*[@id='password']");
	public By loginBtn= By.xpath("//*[@name='Login']");
	
	//Webelement methods
	public WebElement userName() {
		return driver.findElement(userName);
	}
	public WebElement passWord() {
		return driver.findElement(passWord);
	}
	public WebElement loginBtn() {
		return driver.findElement(loginBtn);
	}
	
	//page specific methos
	
			public void appLogin() throws IOException {
				driver.get(getGlobalVariable("applicationUrl"));
				browserAction.waitforElement(userName);
				userName().sendKeys(getGlobalVariable("username"));
				passWord().sendKeys(getGlobalVariable("password"));
				//loginBtn().click();
				browserAction.clickElement(loginBtn);
			}
			public void applicationLogin(String username, String password) throws IOException {
				driver.get(getGlobalVariable("applicationUrl"));
				browserAction.waitforElement(userName);
				userName().sendKeys(username);
				passWord().sendKeys(password);
				//loginBtn().click();
				browserAction.clickElement(loginBtn);
			}
}
