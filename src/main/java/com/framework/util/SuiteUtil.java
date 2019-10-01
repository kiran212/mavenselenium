package com.framework.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class SuiteUtil {
	private static String resultsPath;
	public static String currentTestCase;
	public static String currentBrowser;
	
	public static Properties initilizeProperties() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			fis = new FileInputStream("./src/test/resources/Settings.properties");
			props.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return props;
	}
	
	
	public static String getCurrentDateTime() {
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
		
	}
	
	
	public static String createResultsFolder() {
		String timeStamp = getCurrentDateTime().replaceAll(":", "_");
		String _ResultsFolderPath = System.getProperty("user.dir")+"/Results/Run_"+timeStamp+"/";
		File dir = new File(_ResultsFolderPath);
		if(!dir.exists()) {
			new File(_ResultsFolderPath).mkdirs();
		}
		resultsPath= _ResultsFolderPath;
		
		String screenshotsFolder = SuiteUtil.getCurrentResultsPath()+"/Screenshots/";
		File dir2 = new File(screenshotsFolder);
		if(!dir2.exists()) {
			new File(screenshotsFolder).mkdirs();
		}
		String HTMLReports = SuiteUtil.getCurrentResultsPath()+"/HTML Reports/";
		File dir3 = new File(HTMLReports);
		if(!dir3.exists()) {
			new File(HTMLReports).mkdirs();
		}
		return _ResultsFolderPath;
	}

	public static String getCurrentResultsPath() {
		return resultsPath;
	}
	
	public static String getTimeDifference(String startTime, String endTime) {
		///String dateStart = "01/14/2012 09:29:58";
		//String dateStop = "01/15/2012 10:31:48";
		String timeDiff = "" ;
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(startTime);
			d2 = format.parse(endTime);

			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);

			timeDiff += Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours ";
			timeDiff += Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes ";
			timeDiff += Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.";

		 } catch (Exception e) {
			e.printStackTrace();
		 }
		
		return timeDiff;

	  }
	
	public static void launchResults()  {
		
		
		File htmlFile = new File(resultsPath+"Summary Report.html");
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
//	public String getCurrentTestCase() {
//		return currentTestCase;
//	}
//	
//	public String getCurrentBrowser() {
//		return currentBrowser;
//	}
}
