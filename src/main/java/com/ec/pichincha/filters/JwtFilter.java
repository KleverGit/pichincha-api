package com.ec.pichincha.filters;

import com.ec.pichincha.auth.services.IJwtService;
import com.ec.pichincha.auth.services.JwtServiceImpl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtFilter extends BasicAuthenticationFilter {

	private IJwtService jwtService;

	public JwtFilter(AuthenticationManager authenticationManager, IJwtService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	/**
	 * Gets token from header Token has Bearer standard
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String tokenHeader = request.getHeader(JwtServiceImpl.HEADER_PREFIX);
		// Verify if header has token
		if (!requiresAuthentication(tokenHeader)) {
			chain.doFilter(request, response);
			// return if header not
			return;
		}
		UsernamePasswordAuthenticationToken auth = null;
		if (this.jwtService.validateToken(tokenHeader)) {
			auth = new UsernamePasswordAuthenticationToken(this.jwtService.getUsername(tokenHeader), null,
					this.jwtService.getRoles(tokenHeader));
		}
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}

	/**
	 * Validate if header has token
	 * @param request request
	 * @return boolean has or not token
	 */
	private boolean requiresAuthentication(String headerRequest) {
		if (headerRequest == null || !headerRequest.startsWith(JwtServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

}
