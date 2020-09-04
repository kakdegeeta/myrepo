package com.example.jwt.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.jwt.exception.CustomAsyncExceptionHandler;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 16 Aug 2018
 *
 */
@Configuration
@EnableAsync
public class CustomAsyncConfig implements AsyncConfigurer{

	@Override
	@Bean(destroyMethod="shutdown")
    public Executor getAsyncExecutor() {

	/**
		Configure ThreadPoolTaskExecutor
	**/
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
		return executor;
    }
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		
		return new CustomAsyncExceptionHandler();
	}
	
	
}
