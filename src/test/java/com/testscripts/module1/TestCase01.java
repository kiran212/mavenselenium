package com.testscripts.module1;

import org.testng.annotations.Test;

import com.framework.util.Status;
import com.framework.util.TestManager;

public class TestCase01 extends TestManager{
	
	
	@Test(dataProvider = "testDataProvider")
	public void testCase1(int runCount) {
		driver.manage().window().maximize();
		report.log("Maximize browser", "Browser maximized successful", Status.PASS);
	}

}
