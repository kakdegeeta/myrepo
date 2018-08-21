package com.example.jwt;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Copyright (c) 2018 NTT DATA Corporation
 *
 * @author 159719
 * 8 Aug 2018
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {TestContext.class,WebApplicationContext.class})
@WebAppConfiguration
public class SSOControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
 
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void createTokenTest() throws Exception {
		
		mockMvc.perform(get("/api/sso/createtoken"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$", isA(String.class)));
		
		
	}
}
