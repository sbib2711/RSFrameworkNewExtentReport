package com.test.automation.selenium.framework;

public class Environment {
	public static String CurrentScreenName = "";
	public static String PrevioustScreenName = "";
	public static String browserDriverDir = "";
	public static String browserDriverName = "";
	public static String tempDir = "";
	public static String dataDir = "";
	public static String resultDir = "";
	public static int implicitTimeOut = 20;
	public static int explicitTimeOut = 20;
	public static Boolean CaptureScreenshotSuccessPage = false;
	public static int TestDataRowIndex;
	public static String browserName;

	public static String iebrowserDriverName = "";
	public static String chromebrowserDriverName = "";
	public static String executionMode = "";
	public static String maxParallelrun = "";
	public static String usingDocker = "";
	public static int currentLoggedinCount = 0;
	public static String gridServer;
	public static String gridServerPort;
	public static ExcelUtil excel = new ExcelUtil();

	public static void Initialise(String FileName) throws Exception {

		String osName;
		browserDriverDir = excel.getCellData(FileName, "Automation Framework Config", 2, 1);
		iebrowserDriverName = excel.getCellData(FileName, "Automation Framework Config", 3, 1);
		chromebrowserDriverName = excel.getCellData(FileName, "Automation Framework Config", 4, 1);
		browserDriverName = iebrowserDriverName;

		dataDir = excel.getCellData(FileName, "Automation Framework Config", 10, 1);
		resultDir = excel.getCellData(FileName, "Automation Framework Config", 11, 1);
		executionMode = excel.getCellData(FileName, "Automation Framework Config", 14, 1);
		maxParallelrun = excel.getCellData(FileName, "Automation Framework Config", 15, 1);
		usingDocker = excel.getCellData(FileName, "Automation Framework Config", 16, 1);
		gridServer = excel.getCellData(FileName, "Automation Framework Config", 17, 1);
		gridServerPort = excel.getCellData(FileName, "Automation Framework Config", 18, 1);		
		System.out.println(System.getProperty("os.name"));
		osName = new String(System.getProperty("os.name"));
		if (osName.equalsIgnoreCase("Linux")) {
			browserDriverName = browserDriverName.replaceAll(".exe", "");
			chromebrowserDriverName = chromebrowserDriverName.replaceAll(".exe", "");
			System.out.println(browserDriverName);
			System.out.println(chromebrowserDriverName);
		}

		// String strTimeOut = ExcelUtil.getCellData(FileName, "Automation Framework
		// Config", 12, 1);
		// implicitTimeOut = Integer.parseInt(strTimeOut);
		// strTimeOut = ExcelUtil.getCellData(FileName, "Automation Framework Config",
		// 13, 1);
		// explicitTimeOut = Integer.parseInt(strTimeOut);
	}
}