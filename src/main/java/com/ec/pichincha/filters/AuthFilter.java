package com.ec.pichincha.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ec.pichincha.auth.services.IJwtService;
import com.ec.pichincha.vos.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kleverhidalgo
 * Filter to get Info a more properties in Authentication
 */
@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	private IJwtService jwtService;

	/**
	 * Constructor AuthFilter
	 * @param authManager
	 * @param jwtService
	 */
	public AuthFilter(AuthenticationManager authManager, IJwtService jwtService) {
		this.authManager = authManager;
		// this line changes the default path of authentication 
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
		this.jwtService = jwtService;
	}
	
	/**
	 * Attempt Authentication
	 * Override UsernamePassword, it intercepts the attempt authentication
	 * It validates only and not configure
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// get through form-data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			// get through form-data
			username = request.getParameter("username");
			password = request.getParameter("password");
			
			if (username != null && password != null) {
				log.info("(username desde request --> " + username + " )");
				log.info("(password desde request --> " + password + " )");
			} else {
				// It works when 
				UserVo u = new ObjectMapper().readValue(request.getInputStream(), UserVo.class);
				username = u.getUsername();
				password = u.getPassword();
				log.info("(username desde request raw --> " + username + " )");
				log.info("(password desde request raw --> " + password + " )");
			}
			if (username == null) {
				username = "";
			}
			if (password == null) {
				password = "";
			}
			username = username.trim();

		} catch (Exception e) {
			log.error("Error in authenticacion"+ " username ---> "+ username + " password ---> "+  password);
		}
		// token from spring security
		// it means that ut is in spring context
		UsernamePasswordAuthenticationToken ut = new UsernamePasswordAuthenticationToken(username, password);
		return authManager.authenticate(ut);

	}

	/**
	 * Attempt success
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// gets token 
		String token = this.jwtService.create(authResult);
		// Bearer format
		response.addHeader("Authorization", "Bearer " + token);
		Map<String, Object> bodyResp = new HashMap<String, Object>();
		bodyResp.put("token", token);
		bodyResp.put("user", (User) authResult.getPrincipal());
		bodyResp.put("message", "login jwt success !!");
		response.getWriter().write(new ObjectMapper().writeValueAsString(bodyResp));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	/**
	 * Attempt unsuccess
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> bodyResp = new HashMap<String, Object>();
		bodyResp.put("message", "Credentials error");
		response.getWriter().write(new ObjectMapper().writeValueAsString(bodyResp));
		response.setStatus(401);
		response.setContentType("application/json");
	}
	
}
