package com.testscripts.module1;

import org.testng.annotations.Test;

import com.framework.util.TestManager;

public class TestCase01 extends TestManager{
	
	
	@Test
	public void testCase1() {
		driver.manage().window().maximize();
	}

}
