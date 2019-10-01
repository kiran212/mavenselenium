package com.testscripts.module1;

import org.testng.annotations.Test;

import com.framework.util.TestManager;

public class TestCase02 extends TestManager{
	
	

	@Test(dataProvider = "testDataProvider")
	public void testCase2(int runCount) {
		driver.manage().window().maximize();
	}

}
