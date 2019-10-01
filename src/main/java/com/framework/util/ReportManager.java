package com.framework.util;

public class ReportManager extends TestManager{

	
	public void log(String _ExpectedMsg, String _ActualMsg, Status status) {
		HTMLReportGenerator html = new HTMLReportGenerator();
		String timestamp = SuiteUtil.getCurrentDateTime();
		String screenshotpath = "";
		switch (status) {
				case PASS:
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "PASS", "NA");
					break;
				case FAIL:
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "FAIL", "NA");
					break;
				case SKIP:
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "SKIP", "NA");
					break;
				case DONE:
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "DONE", "NA");
					break;
				case PASS_SCREENSHOT:
					screenshotpath = takeScreenshot();
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "PASSED", screenshotpath);
					break;
					
				case FAIL_SCREENSHOT:
					screenshotpath = takeScreenshot();
					html.appendTestLog("", 1, 1, _ExpectedMsg, _ActualMsg, timestamp, "PASSED", screenshotpath);
					break;
		}
		
		
	}
}
