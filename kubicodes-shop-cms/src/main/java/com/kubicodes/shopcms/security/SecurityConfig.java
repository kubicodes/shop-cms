package com.kubicodes.shopcms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean // implementation of the bean in the registrationcontroller
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//allow everyone to access the different page but allow just "user" to access the categories
		
		http.authorizeRequests()
		.antMatchers("/kategorien/**").hasAnyRole("USER", "ADMIN") //just logged in users can see category
		.antMatchers("/admin/**").hasAnyRole("ADMIN") //just admins can access admin backend
		.antMatchers("/").permitAll()
		.and()//justlike a seperator
		.formLogin().loginPage("/login")
		.and()
		.logout().logoutSuccessUrl("/") //Go to Homepage after logout
		.and()
		.exceptionHandling().accessDeniedPage("/"); //Go to Homepage when access denied
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//defines how user gonna be looked up to see if e.g. the user exists and so on
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
