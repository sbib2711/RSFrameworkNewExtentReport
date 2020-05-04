package com.test.automation.selenium.APIControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.automation.selenium.AutomationDTO.AutomationParam;
import com.test.automation.selenium.framework.ExcelUtil;
import com.test.automation.selenium.framework.ExecutionInitiator;
import com.test.automation.selenium.services.AutomationService;

@Controller
@RequestMapping("/automation/ui")
public class StartAutomation {

	@Autowired
	private AutomationService automationService;

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAutomationStatus() {
		return new ResponseEntity<List<String>>(automationService.getStatus(), HttpStatus.OK);
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView getHTMLReport() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
		// return new
		// ResponseEntity<List<String>>(automationService.getStatus(),HttpStatus.OK);
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public ResponseEntity<?> triggerAutomation() {
		{

			Runnable run1 = new Runnable()

			{

				public void run()

				{

					ExecutionInitiator driver = new ExecutionInitiator();
					try {
						driver.run();
						for (int i = 0; i <= 10000000; i++) {
							Thread.sleep(100);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Error Executing Automation");
						e.printStackTrace();
					}

				}

			};

			Thread th1 = new Thread(run1);

			th1.start();
			// runDriver();
			return new ResponseEntity<String>("Response....", HttpStatus.OK);

		}

	}

	@Async
	public void runDriver() {
		ExecutionInitiator driver = new ExecutionInitiator();
		try {
			driver.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Executing Automation");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public ResponseEntity<?> triggerCustomAutomation(@RequestBody AutomationParam params) {

		Runnable run1 = new Runnable() {

			public void run() {
				ExecutionInitiator driver = new ExecutionInitiator();
				String environment = "";
				String url = "";
				String browserType = "";
				String testDataFolder = "";
				String testResultFolder = "";
				String browserDriverFolder = "";
				String projectName = "";
				//browserDriverFolder = params.getBrowserDriverFolder();
				//browserType = params.getBrowserType();
				//environment = params.getEnvironment();
				//projectName = params.getProjectName();
				//testDataFolder = params.getTestDataFolder();
				//testResultFolder = params.getTestResultFolder();
				//url = params.getUrl();

				try {
					if (browserDriverFolder != "") {
						new ExcelUtil().setCellData("Driver.xlsx", "Automation Framework Config", browserDriverFolder, 2, 1);
					}

					// ExcelUtil.setCellData("Driver.xlsx", "Automation Framework Config",
					// browserType,2 ,1 );
					// ExcelUtil.setCellData("Driver.xlsx", "Automation Framework Config",
					// environment,2 ,1 );
					if (projectName != "" && projectName != null) {
						new ExcelUtil().setCellData("Driver.xlsx", "Application Initialization", projectName, 1, 1);
					}
					if (testDataFolder != "" & testDataFolder != null) {
						new ExcelUtil().setCellData("Driver.xlsx", "Automation Framework Config", testDataFolder, 10, 1);
					}

					if (testResultFolder != "" && testResultFolder != null) {
						new ExcelUtil().setCellData("Driver.xlsx", "Automation Framework Config", testResultFolder, 11, 1);
					}
					if (url != "" && url != null) {
						new ExcelUtil().setCellData("Driver.xlsx", "Application Initialization", url, 3, 1);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					driver.run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error Executing Automation");
					e.printStackTrace();
				}

			}
		};

		Thread t1 = new Thread(run1);
		t1.start();
		return new ResponseEntity<String>("Started....", HttpStatus.OK);
	}
}
