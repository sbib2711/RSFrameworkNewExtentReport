package com.test.automation.selenium.framework;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.test.automation.selenium.businesscomponents.CredentialManager;
import com.test.automation.selenium.businesscomponents.BCEnvironment;

public class ExecutionInitiator{
	
	public static ExcelUtil excel = new ExcelUtil();
	
	public static void main(String args[]) {
		try {
			run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void run() throws Exception {
		@SuppressWarnings("rawtypes")
        logResult logresult;
		String FileName;
		FileName = System.getProperty("user.dir") + "//Driver.xlsx";
		//FileName=ExecutionInitiator.class.getClassLoader().getResource("Driver.xlsx").getPath().substring(1);
		Environment.Initialise(FileName);
		BCEnvironment.Initialise(FileName);
		CredentialManager.getInstance().initialise();
		int numOfParallelRun =	(int) Float.parseFloat(Environment.maxParallelrun.trim());
		System.out.println("Threadpool size: " + numOfParallelRun);
		ExecutorService executor = Executors.newFixedThreadPool(numOfParallelRun);
			
		int noOfmodules =  excel.getRowNumber(FileName, "Test Suites");
		logresult = new logResult();
		for (int kk = 1; kk <= noOfmodules; kk++) {
			String strTestSuiteName = excel.getCellData(FileName, "Test Suites", kk, 1);
			
			String strTestSuiteExecutionPerm = excel.getCellData(FileName, "Test Suites", kk, 2);
			if (!strTestSuiteName.trim().isEmpty() && strTestSuiteExecutionPerm.trim().equalsIgnoreCase("Y")) {
				//logresult = new logResult(strTestSuiteName);
				//logresult = new logResult();
				logresult.init(strTestSuiteName);
				int noOfTestScriptCount =  excel.getRowNumber(FileName, strTestSuiteName);
				for (int ii = 1; ii <= 5; ii++) {
					String BrowserType = "";
					switch (ii) {
						case 	1 : BrowserType = "IE";	break;
						case 	2 : BrowserType = "FIREFOX"; break;
						case 	3 : BrowserType = "CHROME"; break;
						case    4 : BrowserType = "CHROMEHEADLESS"; break;
						case    5 : BrowserType = "HTMLUNIT"; break;
					}

					
					for (int jj = 1; jj <= noOfTestScriptCount; jj++) {
						String strTestCaseName = excel.getCellData(FileName, strTestSuiteName, jj, 0);
						if (!strTestCaseName.trim().isEmpty()) {
							String strTestClassName = excel.getCellData(FileName,strTestSuiteName, jj, 1);
							String strTestExecutionApplicability = excel.getCellData(FileName, strTestSuiteName, jj, ii+1);
							if (strTestExecutionApplicability.trim().equalsIgnoreCase("Y")) {
								
								try {
									
									////logresult = new logResult(strTestCaseName);
							        	//logresult = new logResult(strTestSuiteName);
									logresult.startTest(strTestSuiteName,strTestCaseName);
									Class[] paramString = new Class[4];	
									paramString[0] = String.class;
									paramString[1] = String.class;
									paramString[2] = String.class;	
									paramString[3] = logResult.class;
									
									System.out.println("Starting Thread for Test " + strTestClassName);
									
									Class<?> cls;
									try {
										cls = Class.forName(strTestClassName.trim());
									
										// TODO Auto-generated catch block

									
									
									Object obj = cls.newInstance();
									
									Method method = cls.getDeclaredMethod("ExecuteTest", paramString);
									method.invoke(obj, BrowserType, "", "",logresult);
									logresult.endTest();
									
									}catch(Exception e){
									e.printStackTrace();
									}

									   // Runnable worker = new WorkerThread(strTestClassName,strTestCaseName,BrowserType,logresult);  
							           // executor.execute(worker);//calling execute method of ExecutorService  
							        //logresult.endTest();
								} catch (Exception ex){
									ex.printStackTrace();
									
								} finally {
									
								}
							}
						}
					}
										 
				}
				logresult.closeReport();
				
			}
		}
		
		executor.shutdown();
	}
}