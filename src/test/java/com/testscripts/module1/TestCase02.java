package com.testscripts.module1;

import org.testng.annotations.Test;

import com.framework.util.Status;
import com.framework.util.TestManager;

public class TestCase02 extends TestManager{
	
	

	@Test(dataProvider = "testDataProvider")
	public void testCase2(int runCount) {
		driver.manage().window().maximize();
		driver.get("http://www.google.com");
		report.log("Open application", "Google page is open successful", Status.PASS);
		report.log("Open application", "Google page is open successful", Status.PASS_SCREENSHOT);
		report.log("Open application", "Google page is open successful", Status.FAIL_SCREENSHOT);
		report.log("Open application", "Google page is open successful", Status.FAIL);
		report.log("Open application", "Google page is open successful", Status.DONE);
		report.log("Open application", "Google page is open successful", Status.SKIP);
	}

}
