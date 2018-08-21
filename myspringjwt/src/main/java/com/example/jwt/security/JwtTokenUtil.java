package com.example.jwt.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719 31 Jul 2018
 *
 */
@Component
public class JwtTokenUtil {

	@Value("${app.jwt.expirationtime}")
	private long EXPIRATIONTIME ;
	@Value("${app.jwt.secret}")
	private String SECRET;	

	
	/**
	 * @param eXPIRATIONTIME the eXPIRATIONTIME to set
	 */
	public void setEXPIRATIONTIME(long eXPIRATIONTIME) {
		EXPIRATIONTIME = eXPIRATIONTIME;
	}

	/**
	 * @param sECRET the sECRET to set
	 */
	public void setSECRET(String sECRET) {
		SECRET = sECRET;
	}
	
	/**
	 * 
	 * @param subject
	 * @return
	 */
	public String createToken(String subject) {

		String token = JWT.create().withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.sign(Algorithm.HMAC512(SECRET.getBytes()));
		return token;

	}

	/**
	 * 
	 * @param subject
	 * @return
	 */
	public boolean verifyToken(String token) {
		try {
			// parse the token.
			String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token).getSubject();

			if (user != null) {
				System.out.println("Token Verified Successfully for :" + user);
				return true;
			}
		} catch (TokenExpiredException exp) {
			System.out.println("The Token has expired...");
			System.out.println(exp.getMessage());
		}

		return false;
	}
}
