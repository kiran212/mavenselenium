package com.testscripts.module1;

import org.testng.annotations.Test;

import com.framework.util.ExcelManager;
import com.framework.util.Status;
import com.framework.util.TestManager;

public class TestCase01 extends TestManager{
	
	
	@Test(dataProvider = "testDataProvider")
	public void testCase1(int runCount) {
		driver.manage().window().maximize();
		report.log("Maximize browser", "Browser maximized successful", Status.PASS);
		report.log("Open application", "Google page is open successful", Status.FAIL_SCREENSHOT);
		report.log("Open application", "Google page is open successful", Status.FAIL);
			ExcelManager excel = new ExcelManager();
			String data = excel.getData("TestData", "UserName");
			System.out.println("Data: "+data);
			excel.putData("TestData", "UserName", "Admin100");
			data = excel.getData("TestData", "UserName");
			System.out.println("updated data: "+data);
			
	}

}
