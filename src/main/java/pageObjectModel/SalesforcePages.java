package pageObjectModel;

import org.openqa.selenium.WebDriver;
import resources.base;

public  class SalesforcePages {
	
	
	public   WebDriver driver;
	public SalesforcePages(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
		public   LoginPage LoginPage() { return new LoginPage(driver);}
		public  SetupPage SetupPage() { return new SetupPage(driver);}
		public EngServiceAppoinment EngServiceAppoinment() {return new EngServiceAppoinment(driver);}
		public WorkOrderTimesheet WorkOrderTimesheet() {return new WorkOrderTimesheet(driver);}
		public WorkOrderNavigation WorkOrderNavigation() {return new WorkOrderNavigation(driver);}
}



