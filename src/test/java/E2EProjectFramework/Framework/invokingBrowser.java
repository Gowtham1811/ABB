package E2EProjectFramework.Framework;
import java.io.IOException;
import java.util.HashMap;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjectModel.SalesforcePages;
import resources.base;

public class invokingBrowser extends base {
	public WebDriver driver;
	public SalesforcePages salesforcePages;

	@BeforeMethod
	public void launchBrowser() throws IOException {
		driver = initializeDriver("browser");
		salesforcePages = new SalesforcePages(driver);
	}

	@Test(dataProvider = "invokingBrowserData")
	public void launchBrowser(HashMap<String, String> input) throws IOException {
		driver.get(getGlobalVariable("applicationUrl"));
		System.out.println(input.get("username") + "  " + input.get("password"));
		//salesforcePages.LoginPage().appLogin(input.get("username"), input.get("password"));
		System.out.println(input.get("testvalue"));
	}

	@DataProvider
	public Object[][] invokingBrowserData() throws IOException {
		//Object[][] da = new Object[2][2];

		/*da[0][0] = "gautigv@wise-shark-6l4phv.com";
		da[0][1] = "Veyhaan@182494";
		da[1][0] = "gaufeafwfti@mysfdc.com";
		da[1][1] = "saffVeyhaan@293004";*/
		java.util.List<HashMap<String, String>> fromJson=jsonToString(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\testData.json");
		return new Object[][] {{fromJson.get(0)},{fromJson.get(1)}};
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}

}
