package com.ec.pichincha.auth.services;

import java.util.Date;
import java.util.Arrays;
import java.io.IOException;
import java.util.Collection;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import com.ec.pichincha.filters.SimpleGrantedAuthorityMixin;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtServiceImpl implements IJwtService {

	// It must be in constants properties
	public static final String HEADER_PREFIX = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String SECRET = "__@seed_pichincha_api123456789@__";

	/**
	 * Create token from authentication
	 */
	@Override
	public String create(Authentication auth) throws IOException {
		String username = ((User) auth.getPrincipal()).getUsername();
		/*
		 * another way to set seed SecretKey key =
		 * Keys.secretKeyFor(SignatureAlgorithm.HS256); String secretString =
		 * Encoders.BASE64.encode(key.getEncoded()); logger.info("secret string --> " +
		 * secretString);
		 */

		// One way to set seed
		SecretKey key = Keys.hmacShaKeyFor("__@seed_pichincha_api123456789@__".getBytes());
		// Set roles
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put("roles", new ObjectMapper().writeValueAsString(roles));
		// set expiration time 1 hour
		// set roles in claims
		String token = Jwts.builder().setClaims(claims).setSubject(username).signWith(key).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)).compact();
		return token;
	}

	/**
	 * Validate token
	 */
	@Override
	public boolean validateToken(String token) {
		boolean tokenValid;
		try {
			getClaims(token);
			tokenValid = true;
		} catch (JwtException e) {
			tokenValid = false;
		}
		return tokenValid;
	}

	/**
	 * Get claims, claims have all info about user
	 */
	@Override
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(this.resolveToken(token))
				.getBody();
	}

	/**
	 * Get username from token
	 */
	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Get roles from token
	 */
	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("roles");
		Collection<? extends GrantedAuthority> rolesAuthorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString(), SimpleGrantedAuthority[].class));
		return rolesAuthorities;
	}

	/**
	 * Build token standard
	 */
	@Override
	public String resolveToken(String token) {
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token = token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}
}
