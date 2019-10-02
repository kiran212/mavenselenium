package com.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class TestManager {
	protected static WebDriver driver;
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
	protected ReportManager report;
	protected String screenshotPath;
	protected static int stepnum=0;
	protected static int _PassStepNum=0;
	protected static int _FailStepNum=0;
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
		stepnum=0;
		 _ExelUtil = new ExcelManager();
		 report = new ReportManager();
				BaseDriver baseDriver = new BaseDriver();
				currentBrowser = properties.getProperty("BROWSER_TYPE");
				_TestStartTime = SuiteUtil.getCurrentDateTime();
//				currentTestCaseName = this.getClass().getSimpleName();
				Scenario = "Sample Scenario";
				_Description = "Sample test case description";
				SuiteUtil.currentTestCase = currentTestCaseName;
				SuiteUtil.currentBrowser = currentBrowser;
				String projectName = properties.getProperty("PROJECT");
				String scenario = "Sample Scenario";
				String executeMode = properties.getProperty("EXECUTION_MODE");
				createTestLogHeader(projectName, scenario, currentTestCaseName, 1, currentBrowser, _TestStartTime, executeMode);
				driver = baseDriver.initilizeDriver(currentBrowser);
				
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void wrapUp(ITestResult result) {
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
			String lnk = SuiteUtil.getCurrentResultsPath()+"/HTML Reports/"+currentTestCaseName+"_"+currentBrowser+"_iteration1.html";
			appendTestCaseToSummaryHtml(Scenario, currentTestCaseName, _Description, _TestDuration, currentBrowser, _Status, lnk);
			_ExelUtil.updateTestStatusInTestManager(currentTestCaseName, _Status);
			createTestLogFooter(_TestDuration, _PassStepNum, _FailStepNum);
			
	}
	
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
			_SuiteEndTime = SuiteUtil.getCurrentDateTime();
			_SuiteDuration = SuiteUtil.getTimeDifference(_SuiteStartTime, _SuiteEndTime);
			createSummaryHtmlFooter(_SuiteDuration, _Total, _Passed, _Failed);
			SuiteUtil.launchResults();
	}
	
	
	@DataProvider
	public Object[][] testDataProvider() {
		 _ExelUtil = new ExcelManager();
		 currentTestCaseName = this.getClass().getSimpleName();
			String runMode = _ExelUtil.getRunMode(currentTestCaseName);
			if(!runMode.equals("Yes")) {
				_ExelUtil.updateTestStatusInTestManager(currentTestCaseName, "SKIPPED");
					throw new SkipException("Test Case: "+currentTestCaseName+" Run Mode is not enabled");
			}
			Object[][] dataProvider = new Object[1][1];
			dataProvider[0][0] =1;
			return dataProvider;
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

	public String takeScreenshot() {
		screenshotPath = SuiteUtil.getCurrentResultsPath()+"Screenshots/"+SuiteUtil.currentTestCase+"_"+SuiteUtil.currentBrowser+System.currentTimeMillis();
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotPath;
	}
	
	private void createTestLogHeader(String projectName, String scenario, String testcase,
			int iteration, String browser, String dateAndTime, String executeMode){
		HTMLReportGenerator html = new HTMLReportGenerator();
		html.createTestLogHeader(projectName, scenario, testcase, iteration, browser, dateAndTime, executeMode);
	}
	
	private void createTestLogFooter(String totalDurtion, int stepsPassed, int stepsFailed){
		HTMLReportGenerator html = new HTMLReportGenerator();
		html.createTestLogFooter(totalDurtion, stepsPassed, stepsFailed);
	}
}
