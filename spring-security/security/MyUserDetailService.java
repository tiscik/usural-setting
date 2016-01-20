package com.joiniot.lock.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService{

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		/*
		 * 
		 * 
		 * 
		 * 
		 * 实现逻辑信息  返回User对象
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		return null;
	}

}
