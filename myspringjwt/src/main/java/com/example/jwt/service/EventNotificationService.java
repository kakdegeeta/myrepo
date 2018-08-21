package com.example.jwt.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 16 Aug 2018
 *
 */
@Component
public class EventNotificationService {
	
	/**
	 * @param string
	 */
	@Async
	public void raiseEvent(String eventCode) throws InterruptedException {
		System.out.println("Execute method asynchronously. "
			      + Thread.currentThread().getName());
		
		try {
	        Thread.sleep(5000);
	        System.out.println(String.format("'%s' Event Execution Completed",eventCode));
	      
	    } catch (InterruptedException e) {
	        
	    	System.out.println("Thread Interrupted.. raise exception");
	       	throw e;
	    }
		
	}
	

}
