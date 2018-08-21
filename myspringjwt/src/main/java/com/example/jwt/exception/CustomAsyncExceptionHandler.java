package com.example.jwt.exception;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719 16 Aug 2018
 *
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler#
	 * handleUncaughtException(java.lang.Throwable, java.lang.reflect.Method,
	 * java.lang.Object[])
	 */
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		System.out.println("Exception message:" + ex.getMessage());
		System.out.println("Method name:" + method.getName());

		for (Object param : params) {
			System.out.println("Parameter value:" + param);
		}

	}

}
