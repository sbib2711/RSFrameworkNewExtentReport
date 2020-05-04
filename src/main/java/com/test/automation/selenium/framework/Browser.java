package com.test.automation.selenium.framework;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.log4testng.Logger;

public class Browser {

	public  WebDriver driver = null;
	public  WebElement element;
	public  WebElement eleTopMenu;
	public  List<WebElement> elements;
	public  String MainWindowHandle;
	public  Boolean isObjectPresent = true;
	public  File tmpDir = null;
	public ExcelUtil excel = new ExcelUtil();
	 String BwrType = null;
	ChromeOptions options;
	public logResult  logresult;
	public Browser(logResult resultObject)
	{
		this.logresult = resultObject;
	}

	public  WebDriver Open(String BrowserType) throws Exception {

		BwrType = BrowserType;
		DesiredCapabilities capabilities;

		Proxy proxy = new Proxy();
		if (!System.getProperty("os.name").equalsIgnoreCase("Linux")) {
			//proxy.setHttpProxy("172.25.8.17:8080");
			//proxy.setSslProxy("172.25.8.17:8080");
		}
		TemporaryFilesystem tmpFs = TemporaryFilesystem
				.getTmpFsBasedOn(new File(System.getProperty("user.dir") + "/" + Environment.tempDir));
		tmpDir = tmpFs.createTempDir(BwrType + "-", "browser");

		try {
			System.out.println(BrowserType.toUpperCase());
			switch (BrowserType.toUpperCase()) {

			case "IE":
				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				// capabilities.setCapability(InternetExplorerDriver.ACCEPT_SSL_CERTS, true);
				// capabilities.setCapability("ie.ensureCleanSession", true);

				if ((Environment.iebrowserDriverName == null) || (Environment.iebrowserDriverName == ""))
					Environment.browserDriverName = "IEDriverServer.exe";
				else
					Environment.browserDriverName = Environment.iebrowserDriverName;

				File file = new File(System.getProperty("user.dir") + "//" + Environment.browserDriverDir + "//"
						+ Environment.browserDriverName);
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver(capabilities);
				//driver.manage().window().maximize();
				logresult.logTest("Browser", "Open Browser", "PASS", "IE", "The IE browser was opened.", "");
				break;

			case "CHROMEHEADLESS":

				if ((Environment.chromebrowserDriverName == null) || (Environment.chromebrowserDriverName == ""))
					Environment.browserDriverName = "chromedriver.exe";
				else
					Environment.browserDriverName = Environment.chromebrowserDriverName;
				capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				// options.addArguments("disable-infobars");
				// options.addArguments("test-type");
				// capabilities.setCapability("chrome.binary", System.getProperty("user.dir") +
				// "//"+Environment.browserDriverDir+"//"+Environment.browserDriverName);
				// capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				// //driver = new ChromeDriver(capabilities);
				// ChromeDriverService service = new ChromeDriverService.Builder()
				// .usingDriverExecutable(new File(System.getProperty("user.dir") +
				// "//"+Environment.browserDriverDir+"//"+Environment.browserDriverName))
				// .usingAnyFreePort()
				// .build();

				// System.out.println("\nI am in Linux \n");
				// options.setProxy(proxy);
				options.addArguments("window-size=1920,1080");
				options.addArguments("headless");
				options.addArguments("ignore-certificate-errors");
				options.addArguments("disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				// options.addArguments("--screenshot");
				// options.addArguments("--remote-debugging-port=9222");

				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//"
						+ Environment.browserDriverDir + "//" + Environment.browserDriverName);
				if (Environment.executionMode.equalsIgnoreCase("PARALLEL")) {
				// driver = new ChromeDriver(options);
					driver = new RemoteWebDriver(new URL(("http://" + Environment.gridServer + ":" +  Environment.gridServerPort + "/wd/hub")), capabilities);
				}
				else
				{
					driver = new ChromeDriver(options);
				}
				// driver.manage().window().maximize();
				if (driver != null)
					logresult.logTest("Browser", "Open Browser", "PASS", "CHROME",
							"The CHROME browser was opened in " + System.getProperty("os.name"), "");
				else
					logresult.logTest("Browser", "Open Browser", "PASS", "CHROME", "Drive is null.", "");
				break;

			case "CHROME":

				if ((Environment.chromebrowserDriverName == null) || (Environment.chromebrowserDriverName == ""))
					Environment.browserDriverName = "chromedriver.exe";
				else
					Environment.browserDriverName = Environment.chromebrowserDriverName;
				capabilities = DesiredCapabilities.chrome();
				options = new ChromeOptions();
				options.addArguments("disable-infobars");
				// options.addArguments("test-type");
				// capabilities.setCapability("chrome.binary", System.getProperty("user.dir") +
				// "//"+Environment.browserDriverDir+"//"+Environment.browserDriverName);
				// capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				// //driver = new ChromeDriver(capabilities);
				// ChromeDriverService service = new ChromeDriverService.Builder()
				// .usingDriverExecutable(new File(System.getProperty("user.dir") +
				// "//"+Environment.browserDriverDir+"//"+Environment.browserDriverName))
				// .usingAnyFreePort()
				// .build();

				System.out.println("\nI am in Linux \n");
				// options.setProxy(proxy);
				options.addArguments("window-size=1920,1080");
				// options.addArguments("headless");
				options.addArguments("ignore-certificate-errors");
				options.addArguments("disable-gpu");
				
				// options.addArguments("--screenshot");
				// options.addArguments("--remote-debugging-port=9222");

				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//"
						+ Environment.browserDriverDir + "//" + Environment.browserDriverName);

				if (Environment.executionMode.equalsIgnoreCase("PARALLEL")) {
				// driver = new ChromeDriver(options);
				driver = new RemoteWebDriver(new URL(("http://" + Environment.gridServer + ":4446" +   "/wd/hub")), capabilities);
				}
				else
				{
					driver = new ChromeDriver(options);
				}
				

				// driver.manage().window().maximize();
				if (driver != null)
					logresult.logTest("Browser", "Open Browser", "PASS", "CHROME",
							"The CHROME browser was opened in " + System.getProperty("os.name"), "");
				else
					logresult.logTest("Browser", "Open Browser", "PASS", "CHROME", "Drive is null.", "");
				break;

			case "FIREFOX":
				FirefoxProfile ffProfile = new FirefoxProfile();
				File firebug = new File(
						System.getProperty("user.dir") + File.separator + "DriverExe" + File.separator + "firebug.xpi");
				File firePath = new File(System.getProperty("user.dir") + File.separator + "DriverExe" + File.separator
						+ "FireXPath.xpi");
				if (firebug.exists()) {
					ffProfile.addExtension(firebug);
					ffProfile.setPreference("extensions.firebug.showFirstRunPage", false);
					ffProfile.setPreference("extensions.firebug.allPagesActivation", "on");
					ffProfile.setPreference("extensions.firebug.console.enableSites", true);
				}
				if (firePath.exists()) {
					ffProfile.addExtension(firePath);
				}
				FirefoxOptions foption = new FirefoxOptions();
				foption.setProfile(ffProfile);
				driver = new FirefoxDriver(foption);
				driver.manage().window().maximize();
				logresult.logTest("Browser", "Open Browser", "PASS", "FIREFOX", "The FIREFOX browser was opened.", "");
				break;

			case "HTMLUNIT":
				DesiredCapabilities htmlunitcapability = DesiredCapabilities.htmlUnit();
				// htmlunitcapability.setJavascriptEnabled(true);
				// htmlunitcapability.acceptInsecureCerts();
				htmlunitcapability.setAcceptInsecureCerts(true);
				htmlunitcapability.setJavascriptEnabled(true);
				java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.WARNING);
				java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(Level.WARNING);
				// htmlunitcapability.setVersion(org.openqa.selenium.remote.BrowserType.HTMLUNIT);
				// htmlunitcapability.setBrowserName("htmlunit");
				// driver = new HtmlUnitDriver(htmlunitcapability);

				if (!System.getProperty("os.name").equalsIgnoreCase("Linux")) {
					htmlunitcapability.setCapability(CapabilityType.PROXY, proxy);
				}
				driver = new HtmlUnitDriver(htmlunitcapability);

				if (driver != null) {
					logresult.logTest("Browser", "Open Browser", "PASS", "HTMLUNIT", "The HTMLUNIT driver was opened.",
							"");
				} else
					logresult.logTest("Browser", "Open Browser", "PASS", "HTMLUNIT",
							"The HTMLUNIT browser was not opened.", "");
				break;

			default:
				logresult.logTest("Browser", "Open Browser", "PASS", "FIREFOX", "Unsupported Browser Type", "");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			ex.printStackTrace();
			exitTestifBrowserNotOpened(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			exitTestifBrowserNotOpened(ex.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(Environment.implicitTimeOut, TimeUnit.SECONDS);
		return driver;
	}

	public  void exitTestifBrowserNotOpened(String message) throws Exception {
		logresult.logTest("Browser", "Open Browser", "FAIL", "FIREFOX", "Exception occurred :: " + message, "");
		logresult.logTest("Test Execution", "Status", "INFO", "FIREFOX",
				"Test execution was stopped due to exception occurred in previous steps", "");
		this.Close();

		// Assert.fail("Total Pass count : " +ReporterUtil.testPassCount + "Total Fail
		// count : "+ ReporterUtil.testFailCount + ", Exception occurred : " + message);

	}

	public  WebDriver Open(String BrowserType, String Url) throws Exception {
		Open(BrowserType);
		driver.get(Url);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		logresult.logTest("UI", "Browse To", "PASS", "", "Browse to " + Url, "");
		driver.manage().timeouts().implicitlyWait(Environment.implicitTimeOut, TimeUnit.SECONDS);
		/*
		 * if (BrowserType.equalsIgnoreCase("IE")){ try { if
		 * (driver.getTitle().contains("Certificate Error")) { driver.navigate().to(
		 * "javascript:document.getElementById('overridelink').click();"); } } catch
		 * (Exception e){ logresult.logTest("Browser", "Close Browser", "FAIL", BwrType,
		 * "The browser or webdriver session could not be closed successfully . ExcePtion occurred :"
		 * +e.getMessage(), ""); } }
		 */
		return driver;
	}

	public  void Close() throws Exception {
		// Reporter.log("Closing Browser");
		try {
			if (driver != null) {
				// driver.close();
				System.out.println("	INFO : Browser close [Successfull]");
				logresult.logTest("Browser", "Close Browser", "PASS", BwrType, "The browser was closed successfully.",
						"");
				driver.quit();
				System.out.println("	INFO : Quit from webdriver session [Successfull]");
				logresult.logTest("Browser", "Close Session", "PASS", BwrType,
						"The webdriver session was closed successfully.", "");
				TemporaryFilesystem tmpFs = TemporaryFilesystem.getDefaultTmpFS();
				tmpFs.deleteTemporaryFiles();
			}
		} catch (Exception e) {
			logresult.logTest("Browser", "Close Browser", "FAIL", BwrType,
					"The browser or webdriver session could not be closed successfully . Exception occurred :"
							+ e.getMessage(),
					"");
			e.printStackTrace();
		}
	}

	public  void CheckIfElmentIsPresentByXpath(String xpath) {
		WebDriverWait waiter = new WebDriverWait(driver, Environment.explicitTimeOut);
		waiter.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		driver.findElement(By.xpath(xpath));
	}

	public  void switchToModalWindow() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainWindowHandle = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public  void switchToNewWindow() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainWindowHandle = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public  void pressEscapeButton() throws Exception {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).build().perform();
		//// Reporter.log("Clicked on Escape button ");
		logresult.logTest("UI", "Press Escape Button", "PASS", " ", "Clicked on escape button.", "");
	}

	public  void switchToMainWindow() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().window(MainWindowHandle);
	}

	public  void InputDataEx(String SheetName, String objName, String objDesc, String VerifyData, String Data)
			throws Exception {
		Boolean DoReportIfObjectNotSeen = true;
		Thread.sleep(10);
		isObjectPresent = true;
		if (!objDesc.isEmpty() && (!VerifyData.isEmpty() || !Data.isEmpty())) {
			if (Data.toUpperCase().startsWith("<IF_EXIST>")) {
				String[] TempDataList = Data.split("::");
				Data = Data.substring(TempDataList[0].length() + 2);
				DoReportIfObjectNotSeen = false;
			}
			element = getWebElement(objDesc);
			if (!Data.isEmpty()) {
				if (isObjectPresent == true) {
					try {
						String[] DataList = Data.split("::");
						switch (DataList[0].toLowerCase()) {
						case "clickon":
							if (element.isEnabled()) {
								String src = element.getAttribute("src");
								if (src != null && src.endsWith("png"))
									element.click();
								else {
									switch (this.BwrType) {
									case "IE":
										element.sendKeys(Keys.ENTER);
										break;
									case "FIREFOX":
										element.sendKeys(Keys.ENTER);
										break;
									case "CHROME":
										element.click();
										break;
									default:
										element.click();
										break;
									}
								}
								// Reporter.log("Clicked on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"The object was clicked on.", "");
							} else {
								// Reporter.log("Failed to click on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The object could not be clicked on, as the objectwas disabled.",
										captureScreenshot());
							}
							break;
						case "simpleclickon":
							if (element.isEnabled()) {
								switch (this.BwrType) {
								case "IE":
									element.sendKeys(Keys.RETURN);
									break;
								default:
									element.click();
									break;
								}
								// Reporter.log("Clicked on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"The object was clicked on.", "");
							} else {
								// Reporter.log("Failed to click on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The object could not be clicked on, as the objectwas disabled.",
										captureScreenshot());
							}
							break;

						case "clickonlink":
							if (element.isEnabled()) {
								element.click();
								// Reporter.log("Clicked on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"The object was clicked on.", "");
							} else {
								// Reporter.log("Failed to click on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The object could not be clicked on, as the objectwas disabled.",
										captureScreenshot());
							}
							break;

						case "clickonaftermouseover":
							boolean isClickedOn = false;
							if (element.isDisplayed()) {
								if (BwrType.equals("IE") == true) {
									element.sendKeys(Keys.RETURN);
								} else {
									element.click();
								}
								isClickedOn = true;
							} else {
								if (element.isDisplayed()) {
									Thread.sleep(1000);
									if (BwrType.equals("IE") == true) {
										element.sendKeys(Keys.RETURN);
									} else {
										element.click();
									}
									isClickedOn = true;
								}
							}
							Thread.sleep(1000);
							try {
								if (element.isDisplayed()) {
									ReleaseMouseFromTopMenu(eleTopMenu);
									Thread.sleep(2000);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (isClickedOn) {
								// Reporter.log("Clicked on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"The object was clicked on.", "");
							} else {
								// Reporter.log("Failed to click on [" + objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The object could not be clicked on, as the object was not displayed.",
										captureScreenshot());
							}

							break;
						case "set":
							if (element.isEnabled()) {
								switch (DataList[1].toLowerCase()) {
								case "on":
									if (!element.isSelected()) {
										JavascriptExecutor js = null;
										js = (JavascriptExecutor) driver;
										js.executeScript("arguments[0].click();", element);
										//// Utility.SleepForWhileInSecs(1);
									}
									// Reporter.log("Set checkbox [" + objDesc + "] as ON .");
									logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
											"The checkbox was set as ON.", "");
									break;
								case "off":
									if (element.isSelected()) {
										JavascriptExecutor js = null;
										js = (JavascriptExecutor) driver;
										js.executeScript("arguments[0].click();", element);
										// Utility.SleepForWhileInSecs(1);
									}
									// Reporter.log("Set checkbox [" + objDesc + "] as OFF.");
									logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
											"The checkbox  was set as OFF.", "");
									break;
								}
							} else {
								// Reporter.log("Set checkbox [" + objDesc + "] as "+ DataList[1] +" ::
								// Status[FAIL] :: The checkbox was disabled.");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The checkbox could not set to " + DataList[1]
												+ " , the check box was disabled.",
										captureScreenshot());
								captureScreenshot();
							}
							break;

						case "check":
							if (element.isEnabled()) {
								if (!element.isSelected() && DataList[1].equalsIgnoreCase("on")) {
									JavascriptExecutor js = null;
									js = (JavascriptExecutor) driver;
									js.executeScript("arguments[0].click();", element);
									// Utility.SleepForWhileInSecs(1);
								}
								// Reporter.log("Selected the check box [" + objDesc + "]");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"The check box  was selected", "");
							} else {
								// Reporter.log("Set checkbox [" + objDesc + "] as "+ DataList[1] +" ::
								// Status[FAIL] :: The checkbox was disabled.");
								logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
										"The checkbox could not set to " + DataList[1]
												+ " , the check box was disabled.",
										captureScreenshot());
							}
							break;

						case "mouseover":
							eleTopMenu = null;
							eleTopMenu = element;
							switch (BwrType.toUpperCase()) {
							case "FIREFOX":
								element.click();
								break;
							case "IE":
								element.sendKeys(Keys.ENTER);
								break;
							case "CHROME":
								element.click();
								break;
							}
							// Reporter.log("Moved mouse over object having description as [" + objDesc + "]
							// ");
							logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
									"mouse over was performed.", "");

							break;
						case "selectitem":
							Select droplist = new Select(element);
							droplist.selectByVisibleText(DataList[1]);
							// Utility.SleepForWhile(5000);
							// Reporter.log("Selected "+ DataList[1] +" from drop downlist [" + objDesc + "]
							// ");
							logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
									"The item " + DataList[1] + " was selected from drop downlist.", "");
							break;

						case "selectitembyvisibletext":
							Select droplist1 = new Select(element);
							droplist1.selectByVisibleText(DataList[1]);
							// Utility.SleepForWhile(5000);
							// Reporter.log("Selected "+ DataList[1] +" from drop downlist [" + objDesc + "]
							// ");
							logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
									"The item " + DataList[1] + " was selected from drop downlist.", "");
							break;

						case "selectitembyvalue":
							Select droplist2 = new Select(element);
							droplist2.selectByValue(DataList[1]);
							// Utility.SleepForWhile(5000);
							// Reporter.log("Selected "+ droplist2.getFirstSelectedOption().getText() +"
							// from drop downlist [" + objDesc + "] ");
							logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
									"The item " + droplist2.getFirstSelectedOption().getText()
											+ " was selected from drop downlist.",
									"");
							break;

						case "selectitembyindex":
							Select droplist3 = new Select(element);
							droplist3.selectByIndex(Integer.valueOf(DataList[1]));
							// Utility.SleepForWhile(2000);
							// Reporter.log("Selected "+ droplist3.getFirstSelectedOption().getText() +"
							// from drop downlist [" + objDesc + "] ");
							logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
									"The item " + droplist3.getFirstSelectedOption().getText()
											+ " was selected from drop downlist.",
									"");
							break;
						default:
							element.clear();
							if (Data.toUpperCase().startsWith("<PAN>")) {
								String[] TempDataList = Data.split("::");
								Data = Data.substring(TempDataList[0].length() + 2);
								Data = Data.replace("X", "0");
							}
							if (Data.trim().equalsIgnoreCase("<blank>")) {
								// Reporter.log("Entered Value <blank> for object ["+ objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"Blank value was entered for object.", "");
								break;

							} else {
								element.sendKeys(Data);
								// Reporter.log("Entered Value " + Data + " for object ["+ objDesc + "] ");
								logresult.logTest(SheetName, "InputData", "PASS", objName + "[" + objDesc + "]",
										"Entered [" + Data + "] to edit box.", "");
								break;
							}
						}
					} catch (ElementNotVisibleException e) {
						DoReportIfObjectNotSeen = true;
						e.printStackTrace();
						logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
								"Object not found in page. Input operation [" + Data + "] was skipped. ",
								captureScreenshot());
					}
				} else {
					if (DoReportIfObjectNotSeen == true) {
						// Reporter.log("Object [" + objDesc + "] not found in page : Status [FAIL] :
						// Input operation [" + Data + "] was skipped. ");
						logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
								"Object not found in page. Input operation [" + Data + "] was skipped. ",
								captureScreenshot());
					}
				}
			}

			DoReportIfObjectNotSeen = true;

			if (!VerifyData.isEmpty()) {
				if (VerifyData.toUpperCase().startsWith("<IF_EXIST>")) {
					String[] TempDataList = VerifyData.split("::");
					VerifyData = VerifyData.substring(TempDataList[0].length() + 2);
					DoReportIfObjectNotSeen = false;
				}

				String[] VerifyDataList = VerifyData.split("::");
				String AttribType = VerifyDataList[0];
				String ExpectedValue = VerifyData.substring(VerifyDataList[0].length() + 2);
				String ActualValue;
				Boolean bActualValue = false;
				Boolean bExpectedValue = false;
				if (isObjectPresent == true) {

					if (ExpectedValue.trim().equalsIgnoreCase("<blank>")) {
						ExpectedValue = "";
					}
					switch (AttribType.toLowerCase()) {
					case "valueattribute":
						ActualValue = element.getAttribute("value");
						if (ActualValue.equals(ExpectedValue)) {
							// Reporter.log("Verified value of object having description as [" + objDesc +"]
							// : Status [PASS] , Expected Value ->["+ ExpectedValue + "] , Actual Value ->
							// [" + ActualValue + "]");
							logresult.logTest(SheetName, "VerifyValue", "PASS", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified value of object having description as [" + objDesc +"]
							// : Status [FAIL] , Expected Value ->["+ ExpectedValue + "] , Actual Value ->
							// [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyValue", "FAIL", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
						}
						break;

					case "readonly":
						ActualValue = element.getAttribute("readonly");
						if (ActualValue.matches(ExpectedValue)) {
							// Reporter.log("Verified value of object having description as [" + objDesc +"]
							// : Status [PASS] , Expected Value ->["+ ExpectedValue + "] , Actual Value ->
							// [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectReadOnly", "PASS", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified value of object having description as [" + objDesc +"]
							// : Status [FAIL] , Expected Value ->["+ ExpectedValue + "] , Actual Value ->
							// [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectReadOnly", "FAIL", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
						}
						break;
					case "text":
						ActualValue = element.getText();
						if (ActualValue.equals(ExpectedValue)) {
							// Reporter.log("Verified text value of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyText", "PASS", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified text value of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyText", "FAIL", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
						}
						break;

					case "matchestext":
						ActualValue = element.getText();
						if (ActualValue.matches(ExpectedValue)) {
							// Reporter.log("Verified text value of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyText", "PASS", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified text value of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyText", "FAIL", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
						}
						break;

					case "asserttext":
						ActualValue = element.getText();
						if (ActualValue.equals(ExpectedValue)) {
							// Reporter.log("Verified text value of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "AssertText", "PASS", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							logresult.logTest(SheetName, "AssertText", "FAIL", objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
							AssertJUnit.assertEquals(ExpectedValue, ActualValue);
						}
						break;

					case "assertdisplayed":
						bActualValue = element.isDisplayed();
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Display Status ->["+ bExpectedValue +
							// "] , Actual Display Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "AssertIsObjectDisplayed", "PASS",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									"");
						} else {
							logresult.logTest(SheetName, "AssertIsObjectDisplayed", "FAIL",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									captureScreenshot());
							AssertJUnit.assertEquals(bExpectedValue, bActualValue);
						}
						break;

					case "displayed":
						bActualValue = element.isDisplayed();
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Display Status ->["+ ExpectedValue +
							// "] , Actual Display Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisplayed", "PASS",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + ExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									"");
							//// Reporter.log("PASS : <font color=\"green\"><b>"+ "Test case has
							//// passed"+"</b></font><br/>");
						} else {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Display Status ->["+ ExpectedValue +
							// "] , Actual Display Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisplayed", "FAIL",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + ExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									captureScreenshot());
						}
						break;

					case "enabled":
						bActualValue = element.isEnabled();
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified enabled status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected enabled Status ->["+ bExpectedValue +
							// "] , Actual enabled Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsEnabled", "PASS", objName + "[" + objDesc + "]",
									"Expected enabled Status ->[" + bExpectedValue + "] , Actual enabled Status -> ["
											+ bActualValue + "] ",
									"");
						} else {
							// Reporter.log("Verified enabled status of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected enabled Status ->["+ bExpectedValue +
							// "] , Actual enabled Status -> [" + bActualValue + "] ");
							logresult
									.logTest(SheetName, "VerifyObjectIsEnabled", "FAIL", objName + "[" + objDesc + "]",
											"Expected enabled Status ->[" + bExpectedValue
													+ "] , Actual enabled Status -> [" + bActualValue + "] ",
											captureScreenshot());
						}
						break;

					case "disabled":
						if (element.isEnabled())
							bActualValue = false;
						else
							bActualValue = true;
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified disabled status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected disabled Status ->["+ bExpectedValue +
							// "] , Actual disabled Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisabled", "PASS",
									objName + "[" + objDesc + "]", "Expected disabled Status ->[" + bExpectedValue
											+ "] , Actual disabled Status -> [" + bActualValue + "] ",
									"");
						} else {
							// Reporter.log("Verified disabled status of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected disabled Status ->["+ bExpectedValue +
							// "] , Actual disabled Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisabled", "FAIL",
									objName + "[" + objDesc + "]", "Expected disabled Status ->[" + bExpectedValue
											+ "] , Actual disabled Status -> [" + bActualValue + "] ",
									captureScreenshot());
						}
						break;

					case "typeattribute":
						ActualValue = element.getAttribute("type");
						if (ActualValue.equalsIgnoreCase(ExpectedValue)) {
							// Reporter.log("Verified Value of type attribute of object having description
							// as [" + objDesc +"] : Status [PASS] , Expected Value ->["+ ExpectedValue + "]
							// , Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyAttributeOfObject", "PASS",
									objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified Value of type attribute of object having description
							// as [" + objDesc +"] : Status [FAIL] , Expected Value ->["+ ExpectedValue + "]
							// , Actual Value -> [" + ActualValue + "] ");
							logresult.logTest(SheetName, "VerifyAttributeOfObject", "FAIL",
									objName + "[" + objDesc + "]",
									"Expected Value ->[" + ExpectedValue + "] , Actual Value -> [" + ActualValue + "]",
									captureScreenshot());
						}
						break;

					case "containstext":
						for (int jj = 1; jj < VerifyDataList.length; jj++) {
							ExpectedValue = VerifyDataList[jj];
							ActualValue = element.getText();
							if (ActualValue.contains(ExpectedValue)) {
								// Reporter.log("Verified text value of object having description as [" +
								// objDesc +"] : Status [PASS], The object contains text ["+ ExpectedValue + "]
								// , The Actual Value ->[" +ActualValue +"]");
								logresult.logTest(SheetName, "VerifySubStringText", "PASS",
										objName + "[" + objDesc + "]", "The object contains text [" + ExpectedValue
												+ "] , The Actual Value ->[" + ActualValue + "]",
										"");
							} else {
								// Reporter.log("Verified text value of object having description as [" +
								// objDesc +"] : Status [FAIL], The object does not contain text ["+
								// ExpectedValue + "], The Actual Value ->[" +ActualValue +"]");
								logresult.logTest(SheetName, "VerifySubStringText", "FAIL",
										objName + "[" + objDesc + "]", "The object does not contain text ["
												+ ExpectedValue + "], The Actual Value ->[" + ActualValue + "]",
										captureScreenshot());
							}
						}
						break;

					case "containsregularexpressiontext":
						for (int jj = 1; jj < VerifyDataList.length; jj++) {
							ExpectedValue = VerifyDataList[jj];
							ActualValue = element.getText();
							if (ActualValue.matches(ExpectedValue)) {
								// Reporter.log("Verified text value of object having description as [" +
								// objDesc +"] : Status [PASS], The object contains text ["+ ExpectedValue + "]
								// , The Actual Value ->[" +ActualValue +"]");
								logresult.logTest(SheetName, "VerifySubStringText", "PASS",
										objName + "[" + objDesc + "]", "The object contains text [" + ExpectedValue
												+ "] , The Actual Value ->[" + ActualValue + "]",
										"");
							} else {
								// Reporter.log("Verified text value of object having description as [" +
								// objDesc +"] : Status [FAIL], The object does not contain text ["+
								// ExpectedValue + "], The Actual Value ->[" +ActualValue +"]");
								logresult.logTest(SheetName, "VerifySubStringText", "FAIL",
										objName + "[" + objDesc + "]", "The object does not contain text ["
												+ ExpectedValue + "], The Actual Value ->[" + ActualValue + "]",
										captureScreenshot());
							}
						}
						break;

					case "selecteditem":
						ActualValue = new Select(element).getFirstSelectedOption().getText();
						if (ActualValue.contains(ExpectedValue)) {
							// Reporter.log("Verified selected item in drop down list of object having
							// description as [" + objDesc +"] : Status [PASS], Expected selected item ["+
							// ExpectedValue + "] , The Actual selected item ->[" +ActualValue +"]");
							logresult.logTest(SheetName, "VerifyItemSelected", "PASS", objName + "[" + objDesc + "]",
									"The expected item to be selected [" + ExpectedValue
											+ "] , The actual item was selected [" + ActualValue + "]",
									"");
						} else {
							logresult.logTest(SheetName, "VerifyItemSelected", "FAIL", objName + "[" + objDesc + "]",
									"The expected item to be selected [" + ExpectedValue
											+ "] , The actual item was selected [" + ActualValue + "]",
									captureScreenshot());
						}
						break;

					case "itemslistbyvisibletext":
						int itemCount = new Select(element).getOptions().size();
						// Reporter.log("item count "+itemCount);
						ActualValue = "";
						for (int s = 0; s < itemCount; s++) {
							if (ActualValue.isEmpty())
								ActualValue = new Select(element).getOptions().get(s).getText();
							else
								ActualValue = ActualValue + "::" + new Select(element).getOptions().get(s).getText();
						}
						String presentLists = "";
						String absentLists = "";
						for (int jj = 1; jj < VerifyDataList.length; jj++) {
							String ExpectedItem = VerifyDataList[jj];
							if (ActualValue.contains(ExpectedItem)) {
								if (presentLists.isEmpty())
									presentLists = ExpectedItem;
								else
									presentLists = presentLists + "::" + ExpectedItem;
							} else {
								if (absentLists.isEmpty())
									absentLists = ExpectedItem;
								else
									absentLists = absentLists + "::" + ExpectedItem;
							}
						}
						if (absentLists.isEmpty()) {
							// Reporter.log("Verified item in drop down list of object having description as
							// [" + objDesc +"] : Status [PASS], The dropdown list contains all expected
							// items ["+ presentLists + "] , All actual items are ->[" +ActualValue +"]");
							logresult.logTest(SheetName, "VerifyItemsInDropDownList", "PASS",
									objName + "[" + objDesc + "]", "The dropdown list contains all expected items ["
											+ presentLists + "] , All actual items are  ->[" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified item in drop down list of object having description as
							// [" + objDesc +"] : Status [FAIL], Some of expected items list ["+
							// presentLists + "] not present in actual items [" +ActualValue +"]. The items
							// were not present in the dropdown list : " +absentLists);
							logresult.logTest(SheetName, "VerifyItemsInDropDownList", "FAIL",
									objName + "[" + objDesc + "]",
									"Some of expected items list [" + presentLists + "] not present in actual items ["
											+ ActualValue + "]. The items were not present in the dropdown list : "
											+ absentLists,
									captureScreenshot());
						}

						break;
					case "checked": // it should not be used
						bActualValue = element.isSelected();
						switch (ExpectedValue.toLowerCase()) {
						case "yes":
							bExpectedValue = true;
							break;
						case "true":
							bExpectedValue = true;
							break;
						case "no":
							bExpectedValue = false;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [PASS] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bExpectedValue + "] ");
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "PASS",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									"");
						} else {
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "FAIL",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									captureScreenshot());
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bActualValue + "]");
						}
						break;

					case "selected":
						bActualValue = element.isSelected();
						switch (ExpectedValue.toLowerCase()) {
						case "yes":
							bExpectedValue = true;
							break;
						case "true":
							bExpectedValue = true;
							break;
						case "no":
							bExpectedValue = false;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [PASS] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bExpectedValue + "] ");
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "PASS",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									"");
							//// Reporter.log("PASS : <font color=\"green\"><b>"+ "Test case has
							//// passed"+"</b></font><br/>");
						} else {
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "FAIL",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									captureScreenshot());
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bActualValue + "]");
						}
						break;

					case "containscolumnnames":
						ActualValue = getTableColumnNamesByTableElement(element);
						if (ActualValue.contains(ExpectedValue)) {
							// Reporter.log("Verified Column Names in table [" + objDesc +"] : Status
							// [PASS], The expected column names ["+ExpectedValue+ "] are present. The
							// actual column names are ["+ ActualValue + "]");
							logresult.logTest(SheetName, "VerifyColumnNames", "PASS", objName + "[" + objDesc + "]",
									"The expected column names  [" + ExpectedValue
											+ "] are present. The actual column names are [" + ActualValue + "]",
									"");
						} else {
							// Reporter.log("Verified Column Names in table [" + objDesc +"] : Status
							// [FAIL], The expected column names ["+ExpectedValue+ "] are present. The
							// actual column names are ["+ ActualValue + "]");
							logresult.logTest(SheetName, "VerifyColumnNames", "FAIL", objName + "[" + objDesc + "]",
									"The expected column names  [" + ExpectedValue
											+ "] are present. The actual column names are [" + ActualValue + "]",
									"");
						}
						break;
					case "datarowcount":
						List<WebElement> allRows = element.findElements(By.tagName("tr"));
						int dataRowsCount = allRows.size();
						if (dataRowsCount == Integer.valueOf(ExpectedValue)) {
							// Reporter.log("Verified Row Count in table [" + objDesc +"] : Status [PASS],
							// Expected row count ["+ExpectedValue+ "]. The actual row count ["+
							// dataRowsCount + "]");
							logresult.logTest(SheetName, "VerifyRowCount", "PASS", objName + "[" + objDesc + "]",
									" Expected row count [" + ExpectedValue + "]. The actual row count ["
											+ dataRowsCount + "]",
									"");
						} else {
							// Reporter.log("Verified Row Count in table [" + objDesc +"] : Status [FAIL],
							// Expected row count ["+ExpectedValue+ "]. The actual row count ["+
							// dataRowsCount + "]");
							logresult.logTest(SheetName, "VerifyRowCount", "FAIL", objName + "[" + objDesc + "]",
									" Expected row count [" + ExpectedValue + "] are present. The actual row count["
											+ dataRowsCount + "]",
									"");
						}
						break;
					default:
						ActualValue = element.getText();
						break;
					}
				} else {
					switch (AttribType.toLowerCase()) {
					case "assertdisplayed":
						bActualValue = false;
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Value ->["+ ExpectedValue + "] ,
							// Actual Value -> [" + ExpectedValue + "] ");
							logresult.logTest(SheetName, "VerifyIsObjectDisplayed", "PASS",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									"");
						} else {
							logresult.logTest(SheetName, "VerifyIsObjectDisplayed", "FAIL",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									captureScreenshot());
							AssertJUnit.assertEquals(bExpectedValue, bActualValue);
						}
						break;

					case "displayed":
						bActualValue = false;
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected Display Status ->["+ ExpectedValue +
							// "] , Actual Display Status -> [" + ExpectedValue + "] ");
							logresult.logTest(SheetName, "VerifyIsObjectDisplayed", "PASS",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									"");
						} else {
							// Reporter.log("Verified display status of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Display Status ->["+ ExpectedValue +
							// "] , Actual Display Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyIsObjectDisplayed", "FAIL",
									objName + "[" + objDesc + "]", "Expected Display Status ->[" + bExpectedValue
											+ "] , Actual Display Status -> [" + bActualValue + "] ",
									captureScreenshot());
						}
						break;

					case "disabled":
						bActualValue = true;
						switch (ExpectedValue.toLowerCase()) {
						case "true":
							bExpectedValue = true;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified disabled status of object having description as [" +
							// objDesc +"] : Status [PASS] , Expected disabled Status ->["+ bExpectedValue +
							// "] , Actual disabled Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisabled", "PASS",
									objName + "[" + objDesc + "]", "Expected disabled Status ->[" + bExpectedValue
											+ "] , Actual disabled Status -> [" + bActualValue + "] ",
									"");
						} else {
							// Reporter.log("Verified disabled status of object having description as [" +
							// objDesc +"] : Status [FAIL] , Expected disabled Status ->["+ bExpectedValue +
							// "] , Actual disabled Status -> [" + bActualValue + "] ");
							logresult.logTest(SheetName, "VerifyObjectIsDisabled", "FAIL",
									objName + "[" + objDesc + "]", "Expected disabled Status ->[" + bExpectedValue
											+ "] , Actual disabled Status -> [" + bActualValue + "] ",
									captureScreenshot());
						}
						break;

					case "selected":
						bActualValue = element.isSelected();
						switch (ExpectedValue.toLowerCase()) {
						case "yes":
							bExpectedValue = true;
							break;
						case "true":
							bExpectedValue = true;
							break;

						case "no":
							bExpectedValue = false;
							break;
						case "false":
							bExpectedValue = false;
							break;
						}
						if (bActualValue == bExpectedValue) {
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [PASS] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bExpectedValue + "] ");
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "PASS",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									"");
						} else {
							logresult.logTest(SheetName, "VerifyCheckBoxSelection", "FAIL",
									objName + "[" + objDesc + "]", "The expected selection [" + bExpectedValue
											+ "] , The actual item was selected [" + bActualValue + "]",
									captureScreenshot());
							// Reporter.log("Verified checked status of check box having description as [" +
							// objDesc +"] : Status [FAIL] , Expected Status ->["+ bExpectedValue + "] ,
							// Actual Status -> [" + bActualValue + "]");
						}
						break;
					default:
						if (DoReportIfObjectNotSeen == true) {
							// Reporter.log("Object [" + objDesc + "] not found in page : Status [FAIL] :
							// Verification operation ["+ VerifyData +"] was skipped. ");
							logresult.logTest(SheetName, "InputData", "FAIL", objName + "[" + objDesc + "]",
									"Object [" + objDesc + "] not found in page", captureScreenshot());
						}
						break;
					}

				}

			}
		}
	}

	public  void InputData(String SheetName, int Row) throws Exception {
		int noOfCOl = 0;
		String[] NameLists = SheetName.split("_");
		Environment.CurrentScreenName = SheetName;
		if (!Environment.CurrentScreenName.equalsIgnoreCase(Environment.PrevioustScreenName)) {
			// captureScreenshotForSuccessPage();
			Environment.PrevioustScreenName = Environment.CurrentScreenName;
		}
		logresult.logTest(SheetName, "Begin InputData", "Info", "RowNumber[" + Row + "]",
				"Input data for business component [" + SheetName + "] was started", "");
		String xlsFileName = System.getProperty("user.dir") + "\\" + Environment.dataDir + "\\" + NameLists[0]
				+ ".xlsx";
		if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
			xlsFileName.replace("\\", "/");
		}
		System.out.println("XL filename: " + xlsFileName);
		try {
			noOfCOl = excel.GetNumberOfColumn(xlsFileName, SheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String objDesc = null, Data = null, VerifyData = null, objName = null;

		for (int i = 1; i < noOfCOl; i++) {
			try {
				objName = excel.getCellData(xlsFileName, SheetName, 0, i);
				objDesc = excel.getCellData(xlsFileName, SheetName, 1, i);
				Data = excel.getCellData(xlsFileName, SheetName, Row - 1, i);
				VerifyData = excel.getCellData(xlsFileName, SheetName, Row, i);
			} catch (Exception e) {
				// Reporter.log("Exception Triggered "+e);
				e.printStackTrace();
			}
			isObjectPresent = true;
			InputDataEx(SheetName, objName, objDesc, VerifyData, Data);
		}
	}

	List<WebElement> getChildWebElementsList(String objDesc) throws Exception {
		if (!objDesc.isEmpty()) {
			String[] objDescList = objDesc.split("::");
			try {
				switch (objDescList[0].trim().toLowerCase()) {
				case "id":
					elements = driver.findElements(By.id(objDescList[1]));
					break;

				case "name":
					elements = driver.findElements(By.name(objDescList[1]));
					break;

				case "linktext":
					elements = driver.findElements(By.linkText(objDescList[1]));
					break;

				case "partiallinktext":
					elements = driver.findElements(By.partialLinkText(objDescList[1]));
					break;

				case "xpath":
					elements = driver.findElements(By.xpath(objDescList[1]));
					break;

				case "tagname":
					elements = driver.findElements(By.tagName(objDescList[1]));
					break;

				case "cssselector":
					elements = driver.findElements(By.cssSelector(objDescList[1]));
					break;

				case "classname":
					elements = driver.findElements(By.className(objDescList[1]));
					break;

				default:
					logresult.logTest("UI", "object identifier", "FAIL", objDesc,
							"The syntax for object identifier as follows [id|name|linktext|partiallinktext|xpath|classname|tagname|css selector]::[description]",
							"Not applicable");
				}
			} catch (NoSuchElementException e) {
				isObjectPresent = false;
				element = null;
				e.printStackTrace();
			}
		}
		return elements;
	}

	public  WebElement getWebElementWithoutWait(String objDesc) {
		if (!objDesc.isEmpty()) {
			String[] arrObjDesc = objDesc.split("::");
			String strObjDesc = objDesc.substring(arrObjDesc[0].length() + 2);
			try {
				switch (arrObjDesc[0].toLowerCase()) {
				case "id":
					element = driver.findElement(By.id(strObjDesc));
					isObjectPresent = true;
					break;

				case "name":
					element = driver.findElement(By.name(strObjDesc));
					isObjectPresent = true;
					break;

				case "linktext":
					element = driver.findElement(By.linkText(strObjDesc));
					isObjectPresent = true;
					break;

				case "partiallinktext":
					element = driver.findElement(By.partialLinkText(strObjDesc));
					isObjectPresent = true;
					break;

				case "xpath":
					element = driver.findElement(By.xpath(strObjDesc));
					isObjectPresent = true;
					break;

				case "tagname":
					element = driver.findElement(By.tagName(strObjDesc));
					isObjectPresent = true;
					break;

				case "cssselector":
					element = driver.findElement(By.cssSelector(strObjDesc));
					isObjectPresent = true;
					break;

				case "classname":
					element = driver.findElement(By.className(strObjDesc));
					isObjectPresent = true;
					break;

				}
			} catch (NoSuchElementException e) {
				isObjectPresent = false;
				element = null;
				e.printStackTrace();
			}
		}
		return element;
	}

	public  WebElement getWebElementWithTimeOut(String objDesc, int actTimeOut) throws Exception {
		int tempTimeOut = Environment.explicitTimeOut;
		Environment.explicitTimeOut = actTimeOut;
		WebElement ele = getWebElement(objDesc);
		Environment.explicitTimeOut = tempTimeOut;
		return ele;
	}

	public  WebElement getWebElement(String objDesc) throws Exception {
		if (!objDesc.isEmpty()) {
			String[] arrObjDesc = objDesc.split("::");
			String strObjDesc = objDesc.substring(arrObjDesc[0].length() + 2);
			try {

				WebDriverWait wait = new WebDriverWait(driver, Environment.explicitTimeOut);
				switch (arrObjDesc[0].toLowerCase()) {
				case "id":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strObjDesc)));
					isObjectPresent = true;
					break;

				case "name":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strObjDesc)));
					isObjectPresent = true;

					break;

				case "linktext":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strObjDesc)));
					isObjectPresent = true;
					break;

				case "partiallinktext":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(strObjDesc)));
					isObjectPresent = true;
					break;

				case "xpath":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strObjDesc)));
					isObjectPresent = true;
					break;

				case "tagname":
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(strObjDesc)));
					isObjectPresent = true;
					break;

				case "cssselector":
					element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(strObjDesc)));
					isObjectPresent = true;
					break;

				case "classname":
					element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(strObjDesc)));
					isObjectPresent = true;
					break;

				default:
					logresult.logTest("UI", "object identifier", "FAIL", objDesc,
							"The syntax for object identifier as follows [id|name|linktext|partiallinktext|xpath||classname|tagname|css selector]::[description]",
							"Not applicable");
				}
			} catch (NoSuchElementException e) {
				isObjectPresent = false;
				element = null;
				e.printStackTrace();
				System.out.println(driver.getPageSource());

			} catch (ElementNotVisibleException e) {
				isObjectPresent = false;
				element = null;
				e.printStackTrace();
				System.out.println(driver.getPageSource());
			} catch (TimeoutException e) {
				isObjectPresent = false;
				element = null;
				e.printStackTrace();
				System.out.println(driver.getPageSource());
			}
		}
		/*
		 * try { scrollTo(driver, element); } catch (Exception e){
		 * 
		 * }
		 */

		return element;
	}

	/*
	 * public  void scrollTo(WebDriver driver, WebElement element) {
	 * ((JavascriptExecutor) driver).executeScript(
	 * "arguments[0].scrollIntoView();", element); }
	 */

	public  List<WebElement> getChildWebElementsList(WebElement parentElement, String objDesc) throws Exception {

		if (!objDesc.isEmpty()) {
			String[] arrObjDesc = objDesc.split("::");
			String strObjDesc = objDesc.substring(arrObjDesc[0].length() + 2);
			try {
				switch (arrObjDesc[0].toLowerCase()) {
				case "id":
					elements = parentElement.findElements(By.id(strObjDesc));
					break;

				case "name":
					elements = parentElement.findElements(By.name(strObjDesc));
					break;

				case "linktext":
					elements = parentElement.findElements(By.linkText(strObjDesc));
					break;

				case "partiallinktext":
					elements = parentElement.findElements(By.partialLinkText(strObjDesc));
					break;

				case "xpath":
					elements = parentElement.findElements(By.xpath(strObjDesc));
					break;

				case "tagname":
					elements = parentElement.findElements(By.tagName(strObjDesc));
					break;

				case "cssselector":
					elements = parentElement.findElements(By.cssSelector(strObjDesc));
					break;

				case "classname":
					elements = parentElement.findElements(By.className(strObjDesc));
					break;

				default:
					logresult.logTest("UI", "object identifier", "FAIL", objDesc,
							"The syntax for object identifier as follows [id|name|linktext|partiallinktext|xpath|classname|tagname|css selector]::[description]",
							"Not applicable");
				}
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				isObjectPresent = false;
				element = null;
			} catch (TimeoutException e) {
				e.printStackTrace();
				isObjectPresent = false;
				element = null;
			}
		}
		return elements;
	}

	public  String captureScreenshot() {
		if (driver instanceof TakesScreenshot) {
			String PageName = "";
			File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				PageName = driver.getTitle();
			} catch (NoSuchElementException e) {
				PageName = Environment.CurrentScreenName;
				e.printStackTrace();
			}
			try {
				// String shortFileName = Environment.TestScriptNameWithoutPackage + "-"+
				// Utility.GetTimeStampForScreenShot()+ ".png";
				String shortFileName = Utility.GetTimeStampForScreenShot() + ".png";
				String FileName = System.getProperty("user.dir") + "//" + Environment.resultDir + "//Screenshots//"
						+ shortFileName;
				FileUtils.moveFile(tempFile, new File(FileName));
				return shortFileName;
			} catch (IOException e) {
				e.printStackTrace();
				// TODO handle exception
			}
			return "";
		}
		return "";
	}

	public  int getTableRowCount(String objDesc) throws Exception {
		WebElement tableElement = getWebElement(objDesc);
		List<WebElement> allRows = tableElement.findElements(By.tagName("tr"));
		return allRows.size();
	}

	public  String getTableColumnNames(String objDesc) throws Exception {
		WebElement tableElement = getWebElement(objDesc);
		return getTableColumnNamesByTableElement(tableElement);
	}

	public  String getTableColumnNamesByTableElement(WebElement tableElement) {
		List<WebElement> tableHeaderRow = tableElement.findElements(By.tagName("th"));
		String strHeader = "";
		for (WebElement headerElement : tableHeaderRow) {
			strHeader = strHeader + "::" + headerElement.getText();
		}
		return strHeader;
	}

	public  int getTableColumnCount(String objDesc, int rowNumber) throws Exception {
		WebElement tableElement = getWebElement(objDesc);
		List<WebElement> colRow = tableElement.findElements(By.xpath("//tbody//tr"));
		return colRow.size();
	}

	public  String GetRowDataWithTbody(String objDesc, int row) throws Exception {
		int rowiIterator = 0;
		if (!objDesc.isEmpty()) {
			String[] arrObjDesc = objDesc.split("::");
			String strObjDesc = objDesc.substring(arrObjDesc[0].length() + 2);
			WebElement tableElement = getWebElement(objDesc);
			String strRowData = "";
			List<WebElement> allRows = tableElement.findElements(By.xpath(strObjDesc + "/tr"));
			for (WebElement rowElement : allRows) {
				rowiIterator++;
				if (rowiIterator > row)
					return "";
				strRowData = "";
				if (rowiIterator == row) {
					int colIterator = 0;
					List<WebElement> cells = rowElement.findElements(By.tagName("td"));
					for (WebElement cell : cells) {
						colIterator++;
						if (colIterator == 0)
							strRowData = cell.getText();
						else
							strRowData = strRowData + "::" + cell.getText();
					}
					return strRowData;
				}
			}
		}
		return "";
	}

	public  void clickOn(String ComponentName, String objName, String objDesc) throws Exception {
		WebElement element = this.getWebElement(objDesc);
		if (element.isEnabled()) {
			switch (this.BwrType) {
			case "IE":
				element.click();
				break;
			case "FIREFOX":
				element.click();
				break;
			case "CHROME":
				element.click();
				break;
			default:
				element.click();
				break;
			}
			// Reporter.log("Clicked on "+ objName +"[" + objDesc + "] ");
			logresult.logTest(ComponentName, "InputData", "PASS", objName + "[" + objDesc + "]",
					"The object was clicked on.", "");
		} else {
			// Reporter.log("Failed to click on "+ objName +"[" + objDesc + "] ");
			logresult.logTest(ComponentName, "InputData", "FAIL", objName + "[" + objDesc + "]",
					"The object could not be clicked on, as the objectwas disabled.", "");
		}
	}

	public  void clickOnUsingEnterKey(String ComponentName, String objName, String objDesc) throws Exception {
		WebElement element = this.getWebElement(objDesc);
		if (element.isEnabled()) {
			element.sendKeys(Keys.ENTER);
			// Reporter.log("Clicked on "+ objName +"[" + objDesc + "] ");
			logresult.logTest(ComponentName, "InputData", "PASS", objName + "[" + objDesc + "]",
					"The object was clicked on.", "");
		} else {
			// Reporter.log("Failed to click on "+ objName +"[" + objDesc + "] ");
			logresult.logTest(ComponentName, "InputData", "FAIL", objName + "[" + objDesc + "]",
					"The object could not be clicked on, as the objectwas disabled.", "");
		}
	}

	public  String GetCellData(String objDesc, int row, int col) throws Exception {
		int rowiIterator = 0;
		WebElement tableElement = getWebElement(objDesc);
		List<WebElement> allRows = tableElement.findElements(By.xpath("//tbody//tr"));
		for (WebElement rowElement : allRows) {
			rowiIterator++;
			if (rowiIterator > row)
				return "";
			int colIterator = 0;
			List<WebElement> cells = rowElement.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				colIterator++;
				if ((rowiIterator == row) && (colIterator == col)) {
					return cell.getText();
				}
				if (colIterator > col)
					return "";
			}
		}
		return "";
	}

	public  void ReleaseMouseFromTopMenu(WebElement el) throws InterruptedException {
		Thread.sleep(1000);
		Actions ClickHoldForTopMenu = new Actions(driver);
		try {
			ClickHoldForTopMenu.moveToElement(el).release(el).build().perform();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			// nothing
		}
	}

}
