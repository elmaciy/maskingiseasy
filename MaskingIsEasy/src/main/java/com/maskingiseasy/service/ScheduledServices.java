package com.maskingiseasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.annotation.SpringComponent;

@Service
public class ScheduledServices {
	
	@Autowired
	GeneralService generalService;

	//-------------------------------------------------------------------------------------

	@Scheduled(fixedRate=5000, initialDelay=10000)
	public void checkProcesses() {
		System.out.println("Checking processes...");
	}
	
	


	
}
