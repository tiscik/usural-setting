package com.joiniot.lock.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager{
	
	//检查用户是否够权限访问资源  
    //参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息  
    //参数object是url  
    //参数configAttributes所需的权限
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute ca = iterator.next();
			String needRole = ((SecurityConfig)ca).getAttribute();
			for (GrantedAuthority ga:authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("no right");
	}

	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
