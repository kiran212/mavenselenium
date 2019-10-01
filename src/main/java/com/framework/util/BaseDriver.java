package com.framework.util;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.SkipException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseDriver {

	
	private WebDriver driver;
	
	public WebDriver initilizeDriver(String _BrowserType) {
		Browser browser = Browser.valueOf(_BrowserType);
		switch (browser) {
							case CHROME:
//											String chromeDriverPath  = "";
//											System.setProperty("webdriver.chrome.driver", chromeDriverPath);
											WebDriverManager.chromedriver().setup();
											driver = new ChromeDriver();
											return driver;
					
							case FIREFOX:
//											String geckoDriverPath  = "";
//											System.setProperty("webdriver.gecko.driver", geckoDriverPath);
											WebDriverManager.firefoxdriver().setup();
											driver = new FirefoxDriver();
											return driver;
								
							case INTERNET_EXPLORER:
//											String ieDriverPath  = "";
//											System.setProperty("webdriver.gecko.driver", ieDriverPath);
											WebDriverManager.iedriver().setup();
											driver = new InternetExplorerDriver();
											return driver;
								
							default:		throw new SkipException("Invalid Browser Type");
		
		}
	}
	
	
	

	    
}
