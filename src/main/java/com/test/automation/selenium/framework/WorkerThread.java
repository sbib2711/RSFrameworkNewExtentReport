package com.test.automation.selenium.framework;

import java.lang.reflect.Method;
import com.test.automation.selenium.testScripts.*;
public class WorkerThread implements Runnable {

	String strTestClassName;
	String BrowserType;
	String strTestCaseName;
	logResult logresult;
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		
		
	}

	WorkerThread(String className, String strTestCaseName, String browserType,logResult resobj) {
		this.strTestClassName = className;
		this.BrowserType = browserType;
		this.strTestCaseName = strTestCaseName;
		this.logresult = resobj;
	}

}
