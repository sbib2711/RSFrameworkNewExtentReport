package com.test.automation.selenium.businesscomponents;

import com.test.automation.selenium.framework.ExcelUtil;

public class BCEnvironment {
	public static String projectName ="";
	public static String projectVer ="";
	public static String appURL ="";
	public static String loginUserName ="";
	public static String loginPassword ="";
	
	public static void Initialise(String FileName) throws Exception  {
		ExcelUtil excel = new ExcelUtil();
		projectName 	= excel.getCellData(FileName, "Application Initialization", 1, 1);
		projectVer		= excel.getCellData(FileName, "Application Initialization", 2, 1);
		appURL 			= excel.getCellData(FileName, "Application Initialization", 3, 1);
		loginUserName 	= excel.getCellData(FileName, "Application Initialization", 4, 1);
		loginPassword 	= excel.getCellData(FileName, "Application Initialization", 5, 1);
	}
}
