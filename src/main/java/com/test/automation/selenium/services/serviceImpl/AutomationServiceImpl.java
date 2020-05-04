package com.test.automation.selenium.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.automation.selenium.AutomationDTO.AutomationStatus;
import com.test.automation.selenium.services.AutomationService;

@Service
public class AutomationServiceImpl implements AutomationService {
	@Override
	public List<String> getStatus() {
		List<String> results = new ArrayList<>();
		AutomationStatus status = new AutomationStatus();
		
		results.add(0, status.suiteName);
		results.add(1, Integer.toString(status.totalTest));
		results.add(1, Integer.toString(status.totalTestPass));
		results.add(1, Integer.toString(status.totalTestFail));
		return results;
	}

}
