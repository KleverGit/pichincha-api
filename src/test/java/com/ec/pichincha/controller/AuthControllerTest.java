package com.ec.pichincha.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ec.pichincha.vos.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@RunWith(SpringRunner.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mock;

	@Test
	public void login() {
		try {
			UserVo user = new UserVo();
			user.setUsername("admin");
			user.setPassword("123");
			this.mock.perform(post("/auth/login").contentType("application/json")
					.content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
