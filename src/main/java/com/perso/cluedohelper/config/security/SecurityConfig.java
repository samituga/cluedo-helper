package com.perso.cluedohelper.config.security;

import com.perso.cluedohelper.filter.APIKeyAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configuration class for the security of the API
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ApiAuthenticationFailureHandler apiAuthenticationFailureHandler;
	@Value("${cluedohelper.http.auth-token-header-name}")
	private String principalRequestHeader;
	@Value("${cluedohelper.http.auth-token}")
	private String principalRequestValue;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		final APIKeyAuthFilter apiKeyAuthFilter = apiKeyAuthFilter();
		httpSecurity
			.cors().and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(apiKeyAuthFilter).authorizeRequests().antMatchers("/**").authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(apiAuthenticationFailureHandler);
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**");
	}


	/**
	 * Builds the APIKeyAuthFilter
	 *
	 * @return The APIKeyAuthFilter
	 */
	private APIKeyAuthFilter apiKeyAuthFilter() {
		APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
		filter.setAuthenticationManager(authentication -> {
			String principal = (String) authentication.getPrincipal();
			if (!principalRequestValue.equals(principal)) {
				throw new BadCredentialsException("The API key was not found or not the expected value.");
			}
			authentication.setAuthenticated(true);
			return authentication;
		});
		return filter;
	}
}
