package org.migus.security.cors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CorsInterceptor extends HandlerInterceptorAdapter {
	private boolean allowCredentials;
	private String allowOrigins;

	public CorsInterceptor(boolean allowCredentials, String allowOrigins) {
		super();
		this.allowCredentials = allowCredentials;
		this.allowOrigins = allowOrigins;
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Credentials",
				Boolean.toString(allowCredentials));
		response.addHeader("Access-Control-Allow-Origin", allowOrigins);
		return super.preHandle(request, response, handler);
	}
}