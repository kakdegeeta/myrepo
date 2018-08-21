package com.example.jwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jwt.service.EventNotificationService;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 31 Jul 2018
 *
 */
@Controller
public class FormController {

    @Autowired
    EventNotificationService evtService;
    
	@GetMapping("/ssologin")
    public String loginSuccessful(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		evtService.raiseEvent("LOGIN");
		//throw new RuntimeException();
		return "ui/index";
		
    }

	@GetMapping("/user/getAllUsers")
    public String getAllUsers(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("In getAllUsers");
		return "ui/users";
		
    }
	
	
}
