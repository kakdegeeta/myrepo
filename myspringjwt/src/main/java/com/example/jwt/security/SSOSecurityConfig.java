package com.example.jwt.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719 30 Jul 2018
 *
 */
@Configuration
@EnableWebSecurity
public class SSOSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String SSO_ENTRY_POINT = "/api/sso/**";
    public static final String CFD_ENTRY_POINT = "/api/cfd/**";
    public static final String WALLET_ENTRY_POINT = "/api/wallet/**";
    public static final String GATEWAY_ENTRY_POINT = "/api/gateway/**";
    
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/ssologin";
	
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	

	@Override
	public void configure(WebSecurity  web) throws Exception {
		 
		 web.ignoring().antMatchers(SSO_ENTRY_POINT);
		 web.ignoring().antMatchers(CFD_ENTRY_POINT);
		 web.ignoring().antMatchers(WALLET_ENTRY_POINT);
		 web.ignoring().antMatchers(GATEWAY_ENTRY_POINT);
	 }
		
		 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
		.authorizeRequests()
		.antMatchers("/js/**", "/css/**", "/img/**").permitAll()
		.antMatchers("/user/**")
		.hasAuthority("USER")					
	 .and()
	 	.logout()
	 	.invalidateHttpSession(true)
	 	.clearAuthentication(true)
	 	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	 	.logoutSuccessUrl("/logoutsuccessful")
	 	.permitAll()
	 .and()	
	 	.exceptionHandling()
	 	.accessDeniedHandler(accessDeniedHandler)
	 	.defaultAuthenticationEntryPointFor(new RestAuthenticationEntryPoint(), new AntPathRequestMatcher("/api/**"))
	 	.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
	  .and()
	 	.headers()
	 		.frameOptions().disable()
	 		.xssProtection().block(false);
	
		
	   http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	}
	
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
    }
	
	@Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		
        JwtAuthenticationFilter filter 
            = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new ForwardAuthenticationFailureHandler("/authfailure"));
        return filter;        
       
    }
	
	class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authenticationException) throws IOException, ServletException {
			System.out.println("In RestAuthenticationEntryPoint:: commence");
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().println("{ \"error\": \"" + authenticationException.getMessage() + "\" }");

		}

	}
	
	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() throws Exception {
		final FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<JwtAuthenticationFilter>();
		registrationBean.setFilter(authenticationTokenFilterBean());
		registrationBean.addUrlPatterns("/*");
		// set as false to avoid multiple filter calls
		registrationBean.setEnabled(false);
		return registrationBean;
	}

}
