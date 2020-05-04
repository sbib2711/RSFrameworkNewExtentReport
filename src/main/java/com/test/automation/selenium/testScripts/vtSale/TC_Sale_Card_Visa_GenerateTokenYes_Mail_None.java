package com.test.automation.selenium.testScripts.vtSale;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.test.automation.selenium.businesscomponents.*;
import com.test.automation.selenium.framework.Browser;
import com.test.automation.selenium.framework.logResult;


public class TC_Sale_Card_Visa_GenerateTokenYes_Mail_None {
	
	Browser browser;
	logResult logresult;
	
	public WebDriver driver;
	String txtTxnID = null;
	String txtCardNumber = null;
	String txtAmount = null;
	String txtPaymentType = null;
	String txtDate = null;
	String txtTime = null;
	String txtDateTime = null;
				
	public void ExecuteTest(String browserType, String strProgramDetails, String strTestEnvDetails, logResult result) throws Exception 
	{
		this.logresult = result;
		browser=new Browser(this.logresult);
		this.driver = browser.Open(browserType,BCEnvironment.appURL);
		
		Login login = Login.getInstance();
		VTSale vt = new VTSale();
		Logout logout = new Logout();
		
		String credentials = login.run(6, browser, logresult);
		Thread.sleep(4000);
		
		vt.vtCard(4, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCard(6, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCard(8, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCard(10, browser, logresult);
		Thread.sleep(1000);
		
		/*try{
			driver=browser.driver;
			
			txtTxnID = driver.findElement(By.id("transaction_id")).getText();
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']", 1, 3);
			browser.excel.storeCellData("VTSale_Dashboard", "text::"+txtTxnID, 6, 3);
			browser.excel.storeCellData("VTSale_CardReports", txtTxnID, 5, 5);
			browser.excel.storeCellData("VTSale_CardReports", "text::"+txtTxnID, 8, 8);
			
			txtCardNumber = driver.findElement(By.xpath("//td[contains(text(),'Card Number')]/../td[2]")).getText();
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']/../td[1]", 1, 2);
			browser.excel.storeCellData("VTSale_Dashboard", "text::"+txtCardNumber, 6, 2);
			browser.excel.storeCellData("VTSale_CardReports", "text::"+txtCardNumber, 8, 10);
			
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']/../td[3]", 1, 4);
			
			txtPaymentType = driver.findElement(By.xpath("//td[contains(text(),'Payment Type')]/../td[2]")).getText();
			browser.excel.storeCellData("VTSale_CardReports", "text::"+txtPaymentType, 8, 11);
			
			txtDate = driver.findElement(By.xpath("//td[contains(text(),'Date')]/../td[2]")).getText();
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']/../td[4]", 1, 5);
			browser.excel.storeCellData("VTSale_Dashboard", "text::"+txtDate, 6, 5);
			
			txtTime = driver.findElement(By.xpath("//td[contains(text(),'Time')]/../td[2]")).getText();
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']/../td[5]", 1, 6);
			browser.excel.storeCellData("VTSale_Dashboard", "text::"+txtTime, 6, 6);
			
			browser.excel.storeCellData("VTSale_Dashboard", "xpath:://table[@class='rtable']/tbody/tr[*]/td[2][text()='"+txtTxnID+"']/../td[6]", 1, 7);
			
			txtDateTime = txtDate+" "+txtTime;
			browser.excel.storeCellData("VTSale_CardReports", "text::"+txtDateTime, 8, 7);
			
			Thread.sleep(1000);
			}
		catch(Exception e){
			logresult.logTest("Test Execution", "Status", "INFO", "Exception occurred!!!", e.getMessage(), "");
		}*/
		
		vt.vtCard(12, browser, logresult);
		Thread.sleep(1000);
		
		/*vt.vtDashboard(4, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtDashboard(6, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCardReports(4, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCardReports(6, browser, logresult);
		Thread.sleep(1000);
		
		vt.vtCardReports(8, browser, logresult);
		Thread.sleep(1000);*/
		
		logout.run(4, browser, logresult);
		Thread.sleep(1000);

		logout.run(6, browser, logresult);
		Thread.sleep(1000);
		
		logout.run(8, browser, logresult);CredentialManager.getInstance().releaseCredentials(credentials);
		Thread.sleep(1000);
	
		browser.Close();
		
		}


}

