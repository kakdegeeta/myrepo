package com.example.jwt.exception;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 10 Aug 2018
 *
 */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {
	
	protected Logger logger;
	
	public GlobalExceptionHandlingControllerAdvice() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* . . . . . . . . . . . . . EXCEPTION HANDLERS . . . . . . . . . . . . . . */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	// 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		logger.error("Request raised a DataIntegrityViolationException");
		// Nothing to do
	}

	/**
	 * Convert a predefined exception to an HTTP Status code and specify the
	 * name of a specific view that will be used to display the error.
	 * 
	 * @return Exception view.
	 */
	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Exception exception) {
		// Nothing to do. Return value 'databaseError' used as logical view name
		// of an error page, passed to view-resolver(s) in usual way.
		logger.error("Request raised " + exception.getClass().getSimpleName());
		return "databaseError";
	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name. This method explicitly
	 * creates and returns
	 * 
	 * @param req
	 *            Current HTTP request.
	 * @param exception
	 *            The exception thrown - always {@link SupportInfoException}.
	 * @return The model and view used by the DispatcherServlet to generate
	 *         output.
	 * @throws Exception
	 */
	@ExceptionHandler({Exception.class,RuntimeException.class,AuthenticationException.class})
	public ModelAndView handleError(HttpServletRequest req, Exception exception)
			throws Exception {
		System.out.println("In GlobalExceptionHandlingControllerAdvice:handleError");
		
		// Rethrow annotated exceptions or they will be processed here instead.
		if (AnnotationUtils.findAnnotation(exception.getClass(),
				ResponseStatus.class) != null)
			throw exception;

		logger.error("Request: " + req.getRequestURI() + " raised " + exception);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		
		Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				mav.setViewName("error-404");
				mav.addObject("status", 404);
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				mav.setViewName("systemerror");
				mav.addObject("status", 500);
			}
			else
			{

				mav.addObject("status",statusCode);
				mav.setViewName("systemerror");
			}
		}
		else 
			{
				mav.addObject("status", 500);
				mav.setViewName("systemerror");
			}
		return mav;
	}
	
}
