package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import E2EProjectFramework.Framework.BrowserAction;
import io.github.bonigarcia.wdm.WebDriverManager;

public class base {
	public  WebDriver driver;
	DesiredCapabilities cap;
	//public WebDriverWait wait;
	//public SalesforcePages salesforcePages=new SalesforcePages(this.driver);
	public String getGlobalVariable(String variable) throws IOException 
	{
    FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\gloablVariables.properties");
	Properties prop = new Properties();
	prop.load(fis);
	String globalVariable=prop.getProperty(variable);
	return globalVariable;
	}
	
	public WebDriver initializeDriver(String browser) throws IOException {
		switch(getGlobalVariable(browser).toLowerCase()) {
		case "chrome" :
			WebDriverManager.chromedriver().setup();
			//System.getProperty("webdriver.chrome.driver","\\chrome-win64\\chromedriver.exe" );
			ChromeOptions p = new ChromeOptions();
			p.addArguments("--disable-notifications");
			p.addArguments("--remote-allow-origins=*");
			//p.addArguments("headless");
			//System.setProperty("webdriver.chrome.verboseLogging", "true");
			driver=new ChromeDriver(p);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().window().maximize();
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
			//salesforcePages=new SalesforcePages(driver);
			break;
		case "firefox" :
			WebDriverManager.firefoxdriver().setup();
			
			break;
		default :
			System.getProperty("webdriver.chrome.driver","\\chromedriver.exe" );
			driver=new ChromeDriver();
			break;
		}
		return this.driver=driver;
	}
	//global wait
	public static String getRandomString(int size) {
	  	// The string that we will return
		String rand = "Autotest-";
	  	// The chars that are used to generate the random string
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	  	// Loop based on the requested size
	  	for (int i = 0; i < size; i++) {
	      	// Add a random char from the chars string to the rand string
			rand += chars.toCharArray()[new Random().nextInt(chars.length())];
		}
	  	// Return the random string
		return rand;
	}
	public static String getRandomNumber(int size) {
	  	// The string that we will return
		String rand = "";
	  	// The chars that are used to generate the random string
		String chars = "1234567890";
	  	// Loop based on the requested size
	  	for (int i = 0; i < size; i++) {
	      	// Add a random char from the chars string to the rand string
			rand += chars.toCharArray()[new Random().nextInt(chars.length())];
		}
	  	// Return the random string
		return rand;
	}
    
	public String takeScreenshot(String testName, WebDriver driver2) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver2;
		String FilePath=System.getProperty("user.dir")+"\\report\\"+testName+".png";
		File screenshot=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(FilePath));
		return FilePath;
	}
	public List<HashMap<String, String>> jsonToString(String Path) throws IOException {
		String testDataFromJson=FileUtils.readFileToString(new File(Path),StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data=mapper.readValue(testDataFromJson,new TypeReference<List<HashMap<String, String>>>(){		
		});
		return data;
	}
	public WebDriver initializeRemoteDriver(String browserName) throws MalformedURLException
	{
		cap=new DesiredCapabilities();
		cap.setBrowserName(browserName);
		cap.setPlatform(Platform.WIN10);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver= new RemoteWebDriver(new URL("http://192.168.1.9:4444"), cap);
		return driver;
	}

}
