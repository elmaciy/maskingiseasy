package com.maskingiseasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledServices {
	
	@Autowired 
	GenService genService;


	//-------------------------------------------------------------------------------------

	@Scheduled(fixedRate=5000, initialDelay=3000)
	public void checkProcesses() {
		genService.mylog("Hi from manager");
	}
	
	


	
}
