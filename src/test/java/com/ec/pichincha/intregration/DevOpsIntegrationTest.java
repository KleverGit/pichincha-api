package com.ec.pichincha.intregration;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ec.pichincha.vos.AuthorizationVo;
import com.ec.pichincha.vos.MessageVo;
import com.ec.pichincha.vos.UserVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Integration test with WebEnvironment.RANDOM_PORT Set up all spring context in
 * random port, excecute all layers of endpoint
 * 
 * @author kleverhidalgo
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevOpsIntegrationTest {

	@LocalServerPort
	private int port;

	public String token;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testAuthorization() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		UserVo user = new UserVo();
		user.setUsername("admin");
		user.setPassword("123");
		String inputJson = mapToJson(user);
		HttpEntity<String> entity = new HttpEntity<String>(inputJson, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/auth/login"), HttpMethod.POST,
				entity, String.class);
		log.info("/auth/login ---> Integration ---> " + response.getBody() + " statusCode ---> "
				+ response.getStatusCodeValue());
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void testDevOps() throws Exception {
		String token = "";
		HttpHeaders headers2 = new HttpHeaders();
		UserVo user = new UserVo();
		user.setUsername("admin");
		user.setPassword("123");
		String inputJson = this.mapToJson(user);
		HttpEntity<String> entity2 = new HttpEntity<String>(inputJson, headers2);
		ResponseEntity<String> response2 = restTemplate.exchange(createURLWithPort("/auth/login"), HttpMethod.POST,
				entity2, String.class);
		AuthorizationVo auth = this.mapFromJson(response2.getBody(), AuthorizationVo.class);
		token = auth.getToken();

		HttpHeaders headers = new HttpHeaders();
		MessageVo mes = new MessageVo();
		mes.setFrom("Klever");
		mes.setMessage("Hello !! ");
		mes.setTo("Pichincha");
		mes.setTimeToLifeSec(7);
		String mesJson = mapToJson(mes);
		log.info("/DevOps ---> Integration ---> " + mesJson);
		log.info("/DevOps ---> token ---> " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token.trim());
		HttpEntity<String> entity = new HttpEntity<String>(mesJson, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/DevOps"), HttpMethod.POST, entity,
				String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
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
