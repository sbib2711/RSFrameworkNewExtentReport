package com.test.automation.selenium.testScripts.loginLogout;

import org.openqa.selenium.WebDriver;

import com.test.automation.selenium.businesscomponents.*;
import com.test.automation.selenium.framework.Browser;
import com.test.automation.selenium.framework.logResult;

public class TC_Login {
	
	Browser browser;
	logResult logresult;
	
	public WebDriver driver;
	
	public void ExecuteTest(String browserType, String strProgramDetails, String strTestEnvDetails, logResult result) throws Exception 
	{
				
		this.logresult = result;
		browser=new Browser(this.logresult);
		this.driver = browser.Open(browserType,BCEnvironment.appURL);
		
		Login login = Login.getInstance();
		
		String credentials = login.run(6, browser, logresult);
		Thread.sleep(4000);
		
	}

}
