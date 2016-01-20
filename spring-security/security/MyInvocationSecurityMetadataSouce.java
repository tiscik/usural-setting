package com.joiniot.lock.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.joiniot.lock.security.tool.AntUrlPathMatcher;
import com.joiniot.lock.security.tool.UrlMatcher;

public class MyInvocationSecurityMetadataSouce implements FilterInvocationSecurityMetadataSource{
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap=null;
	
	//tomcat启动时实例化一次
	public MyInvocationSecurityMetadataSouce(){
		loadResourceDefine();
	}
	//加载url和权限的对应关系
	public void loadResourceDefine(){
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_USER");
		atts.add(ca);
		resourceMap.put("/index", atts);
		Collection<ConfigAttribute> attsno = new ArrayList<ConfigAttribute>();
		ConfigAttribute cano = new SecurityConfig("ROLE_NO");
		attsno.add(cano);
		resourceMap.put("/other", attsno);
	}
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String url = ((FilterInvocation)object).getRequestUrl();
		Iterator<String> iterator = resourceMap.keySet().iterator();
		while (iterator.hasNext()) {
			String resUrl = iterator.next();
			if (urlMatcher.pathMatchesUrl(resUrl, url)) {
				return resourceMap.get(resUrl);
			}
			
		}
		return null;
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
