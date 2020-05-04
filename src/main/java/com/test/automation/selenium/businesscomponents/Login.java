package com.test.automation.selenium.businesscomponents;

import com.test.automation.selenium.framework.Browser;
import com.test.automation.selenium.framework.Environment;
import com.test.automation.selenium.framework.logResult;

public class Login {
	public static int intRowNum;
	private static final Login login;
	CredentialManager manager = new CredentialManager();

	static {
		login = new Login();
	}
	Browser browser;

	public synchronized String run(int rownum, Browser bwr, logResult result) {

		String loginData = CredentialManager.getInstance().getCredentials();
		System.out.println("Data obtained from Credntial Manager is : " + loginData);
		this.browser = bwr;
		
		try {
			if(System.getProperty("os.name").equalsIgnoreCase("Linux")) {
			browser.excel.setCellData(System.getProperty("user.dir") + "/testdata/Login.xlsx", "Login_Login", loginData.split(":")[0], rownum-1, 2);
			browser.excel.setCellData(System.getProperty("user.dir") + "/testdata/Login.xlsx", "Login_Login", loginData.split(":")[1], rownum-1, 3);
			}
			else {
				browser.excel.setCellData(System.getProperty("user.dir") + "\\testdata\\Login.xlsx", "Login_Login", loginData.split(":")[0], rownum-1, 2);
				browser.excel.setCellData(System.getProperty("user.dir") + "\\testdata\\Login.xlsx", "Login_Login", loginData.split(":")[1], rownum-1, 3);
			}
				
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		try {
			this.browser = bwr;

			Thread.sleep(4000);
			System.out.println("Starting login with " + rownum);
			browser.InputData("Login_Login", rownum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginData;
	}

	public void goBackToLoginAgain(int rownum) {
		intRowNum = rownum;
		try {
			Thread.sleep(4000);
			browser.InputData("Login_Login", intRowNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Login getInstance() {
		return login;
	}

}
