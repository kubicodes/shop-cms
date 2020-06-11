package com.kubicodes.shopcms.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//allow everyone to access homepage and allow just "user" to access the categories
		
		http.authorizeRequests().antMatchers("/kategorien/**").hasAnyRole("USER").antMatchers("/").permitAll();
	}
}
