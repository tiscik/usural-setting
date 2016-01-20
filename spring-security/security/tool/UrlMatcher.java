package com.joiniot.lock.security.tool;

public interface UrlMatcher {
	Object compile(String paramString);
	boolean pathMatchesUrl(Object paramObject,String paramString);
	String getUninversalMatchPattern();
	boolean requiresLowerCaseUrl();
}
