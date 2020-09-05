package com.ec.pichincha.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ec.pichincha.vos.AuthorizationVo;
import com.ec.pichincha.vos.MessageVo;
import com.ec.pichincha.vos.UserVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class DevOpsControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void existentUserCanGetTokenAndAuthentication() throws Exception {
		UserVo user = new UserVo();
		user.setUsername("admin");
		user.setPassword("123");

		String inputJson = mapToJson(user);

		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post("/auth/login").content(inputJson))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		AuthorizationVo authvo = new AuthorizationVo();
		authvo = this.mapFromJson(response, AuthorizationVo.class);

		MessageVo mes = new MessageVo();
		mes.setFrom("Klever");
		mes.setMessage("Hello !! ");
		;
		mes.setTo("Pichincha");
		mes.setTimeToLifeSec(7);
		String mesJson = mapToJson(mes);

		MvcResult result2 = this.mvc
				.perform(MockMvcRequestBuilders.post("/DevOps").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mesJson).header("Authorization", "Bearer " + authvo.getToken()))
				.andExpect(status().isOk()).andReturn();
		String response2 = result2.getResponse().getContentAsString();
		log.info("Response /DevOps ---> " + response2);
	}

	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	public <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
