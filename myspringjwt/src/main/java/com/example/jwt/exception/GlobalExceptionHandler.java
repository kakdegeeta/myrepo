package com.example.jwt.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	

	
	/**
     * Handle Exception
     *
     * @param ex   	  Exception
     * @param request WebRequest
     * @return the ApplicationError object
     */

	@ExceptionHandler(value = { Exception.class,RuntimeException.class })
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		System.out.println("In GlobalExceptionHandler:handleException");
		ApplicationError appException = new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR);
		appException.setMessage(ex.getMessage());
		appException.setDebugMessage(ex.getLocalizedMessage());
		return handleExceptionInternal(ex, appException, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	
	}
	
	
	/**
     * Handle IllegalArgumentException and IllegalStateException
     *
     * @param ex      RuntimeException
     * @param request WebRequest
     * @return the ResponseEntity object
     */

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		System.out.println("In GlobalExceptionHandler: handleConflict");
		return handleExceptionInternal(ex,  ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	

}
