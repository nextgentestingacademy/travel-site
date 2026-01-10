package utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;

public class TestNGListener implements ITestListener{
	
	//Create a HTML report 
	ExtentReports extent;
	
	//represents each @Test annotation as a separate Test Case in Extent Report
	ExtentTest test;
	
	//this will ensure reports are generated to the specific test case running parallely 
	static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	@Override
	public void onStart(ITestContext context) {
		String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Automation Test Report");
		reporter.config().setDocumentTitle("District.in Report");
		
		extent = new ExtentReports();
		
		//attaching the report to my ExtentReport
		extent.attachReporter(reporter);
		
		extent.setSystemInfo("QA Name", "Rahul");
		extent.setSystemInfo("Environment", ConfigReader.get("env"));
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExcelUtils.updateResult(result.getMethod().getMethodName(), "PASS", "Test passed successfully");
		extentTest.get().log(Status.PASS, "Test passed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExcelUtils.updateResult(result.getMethod().getMethodName(), "FAILED", result.getThrowable().getMessage());
		Object currentClass = result.getInstance();
		WebDriver driver = DriverFactory.getDriver();
		extentTest.get().fail(result.getThrowable());
		String path = ScreenshotUtils.capture(driver, result.getName());
		extentTest.get().addScreenCaptureFromPath(path,"Failed Test Screenshot");
		System.out.println("Screenshot saved for failed test: " + result.getName());
	}	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		ExcelUtils.updateResult(result.getMethod().getMethodName(), "SKIPPED", "Test skipped");
		extentTest.get().log(Status.SKIP, "Test Skipped");
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
