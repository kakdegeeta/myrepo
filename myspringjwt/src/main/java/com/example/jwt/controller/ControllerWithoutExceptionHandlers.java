package com.example.jwt.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jwt.exception.GlobalExceptionHandlingControllerAdvice;

/**
 * A controller whose request-handler methods deliberately throw exceptions to
 * demonstrate the points discussed in the Blog.
 * <p>
 * Expects a <tt>@ControllerAdvice</tt> to handle its exceptions - see
 * {@link GlobalExceptionHandlingControllerAdvice}.
 * 
 * @author Paul Chapman
 */
@Controller
@RequestMapping("/global")
public class ControllerWithoutExceptionHandlers {

	protected Logger logger;

	public ControllerWithoutExceptionHandlers() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* . . . . . . . . . . . . . . REQUEST HANDLERS . . . . . . . . . . . . . . */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * Controller to demonstrate exception handling.
	 * 
	 * @return The view name (an HTML page with Thymeleaf markup).
	 */
	@RequestMapping("")
	String home1() {
		logger.info("Global home page 1");
		return "global";
	}

	/**
	 * Controller to demonstrate exception handling..
	 * 
	 * @return The view name (an HTML page with Thymeleaf markup).
	 */
	@RequestMapping("/")
	String home2() {
		logger.info("Global home page 2");
		return "global";
	}

	
	/**
	 * Throws an unannotated <tt>DataIntegrityViolationException</tt>. Must be
	 * caught by an exception handler.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws DataIntegrityViolationException
	 *             Always thrown.
	 */
	@RequestMapping("/dataIntegrityViolation")
	String throwDataIntegrityViolationException() throws SQLException {
		logger.info("Throw DataIntegrityViolationException");
		throw new DataIntegrityViolationException("Duplicate id");
	}

	/**
	 * Simulates a database exception by always throwing <tt>SQLException</tt>.
	 * Must be caught by an exception handler.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws SQLException
	 *             Always thrown.
	 */
	@RequestMapping("/databaseError1")
	String throwDatabaseException1() throws SQLException {
		logger.info("Throw SQLException");
		throw new SQLException();
	}

	

}
