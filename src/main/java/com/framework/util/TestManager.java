package com.framework.util;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestManager {
	protected WebDriver driver;
	protected String currentBrowser;
	protected static Properties properties;
	protected HTMLReportGenerator html;
	protected ExcelManager _ExelUtil;
	private int _Passed=0;
	private int _Failed=0;
	private int _Total=0;
	private String _SuiteDuration;
	private String _SuiteStartTime;
	private String _SuiteEndTime;
	private String Scenario;
	private String currentTestCaseName;
	private String _Description;
	private String _TestStartTime;
	private String _TestEndtTime;
	private String _TestDuration;
	private String _Status;
	
	@BeforeSuite(alwaysRun = true)
	public void setUpSuite() {
		 properties = SuiteUtil.initilizeProperties(); 
		 _SuiteStartTime = SuiteUtil.getCurrentDateTime();
		 SuiteUtil.createResultsFolder();
		 _ExelUtil = new ExcelManager();
		 String projectName = properties.getProperty("PROJECT");
		 String _Environment = properties.getProperty("ENVIRONMENT");
		 String _SuiteType = properties.getProperty("SUITE_TYPE");
		 String _OS = System.getProperty("os.name");
		 String _DateAndTime = SuiteUtil.getCurrentDateTime();
		 createSummaryHtmlHeader(projectName, _Environment, _SuiteType, _OS, _DateAndTime);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUpTest(ITestResult result) {
		currentTestCaseName = this.getClass().getSimpleName();
		 _ExelUtil = new ExcelManager();
		String runMode = _ExelUtil.getRunMode(currentTestCaseName);
		
		if(runMode.equals("Yes")) {
		
				BaseDriver baseDriver = new BaseDriver();
				currentBrowser = properties.getProperty("BROWSER_TYPE");
				_TestStartTime = SuiteUtil.getCurrentDateTime();
				currentTestCaseName = this.getClass().getSimpleName();
				Scenario = "Sample Scenario";
				_Description = "Sample test case description";
				driver = baseDriver.initilizeDriver(currentBrowser);
				
		}
		else {
			throw new SkipException("Test Case: "+currentTestCaseName+" Run Mode is not enabled");
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void wrapUp(ITestResult result) {
		if(result.getStatus() != ITestResult.SKIP) {
			properties = SuiteUtil.initilizeProperties();
			String broserClose = properties.getProperty("BROWSER_CLOSE");
			if(broserClose.equalsIgnoreCase("True") && driver!=null) {
				driver.close();
			}
			if(result.isSuccess()) {
				_Status = "PASSED";
				++_Passed;	
			}
			else {
				_Status = "FAILED";
				++_Failed;
			}
			_Total = _Passed+_Failed;
			_TestEndtTime = SuiteUtil.getCurrentDateTime();
			_TestDuration = SuiteUtil.getTimeDifference(_TestStartTime, _TestEndtTime);
			appendTestCaseToSummaryHtml(Scenario, currentTestCaseName, _Description, _TestDuration, currentBrowser, _Status, "");
		}
	}
	
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
			_SuiteEndTime = SuiteUtil.getCurrentDateTime();
			_SuiteDuration = SuiteUtil.getTimeDifference(_SuiteStartTime, _SuiteEndTime);
			createSummaryHtmlFooter(_SuiteDuration, _Total, _Passed, _Failed);
			SuiteUtil.launchResults();
	}
	
	public void createSummaryHtmlHeader(String projectName, String _Environment, String _SuiteType, String _OS, String _DateAndTime) {
		HTMLReportGenerator html = new HTMLReportGenerator();
		html.createSummaryHtmlHeader(projectName, _Environment, _SuiteType, _OS, _DateAndTime);
	}
	
	public void appendTestCaseToSummaryHtml(String Scenario, String currentTestCaseName, String _Description, String _TestDuration, String currentBrowser, String _Status, String link) {
		HTMLReportGenerator html = new HTMLReportGenerator();
		html.appendTestCaseToSummaryHtml(Scenario, currentTestCaseName, _Description, _TestDuration, currentBrowser, _Status, link);
	}
	
	public void createSummaryHtmlFooter(String _SuiteDuration, int _Total, int _Passed, int _Failed) {
		HTMLReportGenerator html = new HTMLReportGenerator();
		html.createSummaryHtmlFooter(_SuiteDuration, _Total, _Passed, _Failed);
	}

}
