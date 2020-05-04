package com.test.automation.selenium.businesscomponents;
import org.openqa.selenium.WebDriver;

import com.test.automation.selenium.framework.Browser;
import com.test.automation.selenium.framework.logResult;

public class Logout {
	public static int intRowNum;
	public static WebDriver driver;
	public Browser browser;
	public  void run(int rownum,Browser bwr,logResult result){
		intRowNum= rownum;
		try {
			this.browser = bwr;
			Thread.sleep(40);
			browser.InputData("Logout_Logout", intRowNum);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
