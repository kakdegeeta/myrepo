package com.example.jwt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719 30 Jul 2018
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	public JwtAuthenticationFilter(RequestMatcher matcher) {
		super(matcher);

	}

	public JwtAuthenticationFilter() {
		
		super(SSOSecurityConfig.TOKEN_BASED_AUTH_ENTRY_POINT);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		Authentication auth = null;

		System.out.println("In JwtAuthenticationFilter::attemptAuthentication");

		auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			System.out.println("Authenticated Session. Bypass token verification");
			return auth;
		} else {
			String tokenPayload = request.getParameter("token");

			// Validate the token. Extract traderId from token

			if (tokenPayload == null || isValid(tokenPayload) == false) {
				throw new InsufficientAuthenticationException("Invalid Token");
			}

			// Create our Authentication and let Spring know about it

			final List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			auth = new UsernamePasswordAuthenticationToken("traderId", null, authorities);
			return auth;
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("In JwtAuthenticationFilter::successfulAuthentication");
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		getFailureHandler().onAuthenticationFailure(request, response, failed);

	}

	/**
	 * @param tokenPayload
	 * @return
	 */
	private boolean isValid(String tokenPayload) {
		return jwtTokenUtil.verifyToken(tokenPayload);
	}

}
