package com.ec.pichincha.services;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	/**
	 * Return UserDetails from springSecurity
	 * It can build User 
	 * Here we can develop and get user from BDD
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> roles = new ArrayList<>();
		String passwordBCrypt = passwordEncode.encode("123");
		UserDetails userDet = new User("admin", passwordBCrypt, roles);
		return userDet;
	}

}
