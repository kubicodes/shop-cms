package com.kubicodes.shopcms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kubicodes.shopcms.models.AdminRepository;
import com.kubicodes.shopcms.models.UserRepository;
import com.kubicodes.shopcms.models.data.Admin;
import com.kubicodes.shopcms.models.data.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//use the implemented method findByUsername in user and admin repo
		User user = userRepository.findByUsername(username);
		Admin admin = adminRepository.findByUsername(username);
		
		//return user or admin if not null
		if(user != null) {
			return user;
		}
		
		if(admin != null) {
			return admin;
		}
		
		//if no user or admin found throw exception with message
		throw new UsernameNotFoundException("Benutzer nicht gefunden!");
	}

}
