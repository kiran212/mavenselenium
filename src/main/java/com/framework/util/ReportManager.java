package com.framework.util;

public class ReportManager extends TestManager{
	public void log(String _ExpectedMsg, String _ActualMsg, Status status) {
		HTMLReportGenerator html = new HTMLReportGenerator();
		String timestamp = SuiteUtil.getCurrentDateTime();
		String screenshotpath = "";
		switch (status) {
				case SKIP:
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "SKIP", "None");
					break;

				case DONE:
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "DONE", "None");
					break;
				
				case PASS:
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "PASS", "None");
					++_PassStepNum;
					break;
				
				case PASS_SCREENSHOT:
					screenshotpath = takeScreenshot();
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "PASS", screenshotpath);
					++_PassStepNum;
					break;
				
				case FAIL:
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "FAIL", "None");
					++_FailStepNum;
					break;
				
				case FAIL_SCREENSHOT:
					screenshotpath = takeScreenshot();
					html.appendTestLog("", 1, ++stepnum, _ExpectedMsg, _ActualMsg, timestamp, "FAIL", screenshotpath);
					++_FailStepNum;
					break;
		}
		
		
	}
}
