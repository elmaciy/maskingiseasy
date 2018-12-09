package com.maskingiseasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledServices {
	
	@Autowired
	GeneralService generalService;

	//-------------------------------------------------------------------------------------

	@Scheduled(fixedRate=5000, initialDelay=3000)
	public void checkProcesses() {
		generalService.makeSureWorkingDirectoriesExists();
		System.out.println("Checking processes...");
	}
	
	


	
}
