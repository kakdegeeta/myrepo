package com.example.jwt.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 14 Aug 2018
 *
 */

@Controller
public class TestController{


	
	@RequestMapping(CommonURLs.ERROR_URL)
	public String handleError(HttpServletRequest request) {

		System.out.println("START:: handleError");

		String view = "systemerror";
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		System.out.println("Status Code:: " + status);

		if (status != null) {
			HttpStatus statusCode = Arrays.stream(HttpStatus.values())
					.filter(httpstatus -> httpstatus.value() == Integer.valueOf(status.toString())).findAny()
					.orElse(HttpStatus.INTERNAL_SERVER_ERROR);

			switch (statusCode) {

			case FORBIDDEN:
				view ="403";
				break;
			case NOT_FOUND:
				view = "systemerror";
				break;
			default:
				view = "systemerror";
				break;

			}
		}
		return view;

	}
	@RequestMapping("/authfailure")
	public ModelAndView loginFailure(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		
		System.out.println("In LoginErrorController");
		Object exception=request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(exception!=null && exception instanceof AuthenticationException)
		{
			mav.addObject("exception", exception);
			mav.addObject("url", request.getRequestURL());
			mav.setViewName("403");
		}
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			
			HttpStatus statusCode = Arrays.stream(HttpStatus.values())
			        .filter(httpstatus -> httpstatus.value()== Integer.valueOf(status.toString()))
			        .findAny()
			        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

			switch (statusCode) {

			case FORBIDDEN:
				mav.setViewName("403");
				mav.addObject("status", 404);
				break;
			default:
				mav.setViewName("systemerror");
				mav.addObject("status", 500);
				break;

			}
		}
			return mav;
			
	}
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.servlet.error.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		
		return "/error";
	}

}
