package com.example.jwt.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler{

	
	private static Logger log = LoggerFactory.getLogger(LoggingAccessDeniedHandler.class);

	/**
     * Handle AccessDenied
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param ex   	  AccessDeniedException
     *
     */
	
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {

//      //  Authentication auth = applicationContext.getAuthentication();
//
//        if (ex instanceof MissingCsrfTokenException
//                || ex instanceof InvalidCsrfTokenException) {
//
//        	final HttpSession session = request.getSession(false);
//            if (session == null) {
//                throw new IllegalStateException("Cannot create CSRF token challenge when session is null.");
//            }
//        }        
//        if (auth != null) {
//            log.info(auth.getName()
//                    + " was trying to access protected resource: "
//                    + request.getRequestURI());
//        }

        response.sendRedirect(request.getContextPath() +"access-denied");

    }
	
}
