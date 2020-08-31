package com.ec.pichincha.auth.services;

import java.io.IOException;
import java.util.Collection;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public interface IJwtService {

	public Claims getClaims(String token);
	public String getUsername(String token);
	public String resolveToken(String token);
	public boolean validateToken(String token);
	public String create(Authentication auth) throws IOException;
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
	
}
