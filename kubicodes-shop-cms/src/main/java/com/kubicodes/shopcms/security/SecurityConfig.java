package com.kubicodes.shopcms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean // implementation of the bean in the registrationcontroller
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//allow everyone to access the different page but allow just "user" to access the categories
		
		http.authorizeRequests().antMatchers("/kategorien/**").hasAnyRole("USER").antMatchers("/").permitAll();
	}
}
