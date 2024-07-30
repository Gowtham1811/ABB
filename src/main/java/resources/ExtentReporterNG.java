package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	static ExtentReports extent;
	
	public static ExtentReports getReportObject()
	{
		
		String path =System.getProperty("user.dir")+"\\report\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("BackOffice App Automation results");
		reporter.config().setDocumentTitle("Automation Test Results");
		
		 extent =new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Gowtham Venkatesan");
		return extent;
		
	}
}
