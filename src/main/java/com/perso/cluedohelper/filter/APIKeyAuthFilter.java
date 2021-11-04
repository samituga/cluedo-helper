package com.perso.cluedohelper.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Filter for the API key authentication
 */
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

	private final String principalRequestHeader;

	public APIKeyAuthFilter(String principalRequestHeader) {
		this.principalRequestHeader = principalRequestHeader;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getHeader(principalRequestHeader);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}

}