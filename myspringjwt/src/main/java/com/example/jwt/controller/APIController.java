package com.example.jwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.security.JwtTokenUtil;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 31 Jul 2018
 *
 */
@RestController
@RequestMapping("/api/")
public class APIController {
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
    
	@GetMapping("sso/createtoken")
    public String createToken(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("In createToken");

		return jwtTokenUtil.createToken("traderId");
		
    }
	

	@GetMapping("sso/verifytoken")
    public boolean verifyToken(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("In verifyToken");
		String token=request.getParameter("token");
		
		if (token != null) {
			return jwtTokenUtil.verifyToken(token);
        }
		System.out.println("Token Not Found In Request");
        return false;
    }
	
	
	@GetMapping("cfd/updatebalance")
    public boolean cfdUpdateBalance(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("cfd/updatebalance");
		return true;
    }
	
	@GetMapping("wallet/updatebalance")
    public boolean walletUpdateBalance(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("wallet/updatebalance");
		throw new RuntimeException();
		
		//return true;
    }
}
