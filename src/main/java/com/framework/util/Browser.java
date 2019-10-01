/**
 * 
 */
package com.framework.util;

/**
 * @author kiran.gummadi
 *
 */
public enum Browser {
	CHROME("chrome"),
	INTERNET_EXPLORER("IE"), 
	FIREFOX("Firefox");
	
	private String browser;
	
	Browser(String browser){
		this.browser = browser;
	}
	
	public String getBrowser() {
		return this.browser;
	}
	
}
