package com.ec.pichincha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ec.pichincha.auth.services.IJwtService;
import com.ec.pichincha.filters.AuthFilter;
import com.ec.pichincha.filters.JwtFilter;
import com.ec.pichincha.services.UserDetailServiceImpl;

/**
 * Spring security config with token library
 * @author kleverhidalgo
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Autowired
	private IJwtService jwtService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Validate http requests
	 * It has filter before requests 
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and().httpBasic().and()
				.addFilter(new AuthFilter(authenticationManager(), this.jwtService))
				.addFilter(new JwtFilter(authenticationManager(), this.jwtService)).csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * Set encoder service (bcrypt) in login
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// always do comparison with encripted password 
		auth.userDetailsService(this.userDetailsService).passwordEncoder(bcrypt);
	}

}
