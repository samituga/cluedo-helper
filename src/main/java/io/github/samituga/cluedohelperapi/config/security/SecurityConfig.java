package io.github.samituga.cluedohelperapi.config.security;

import io.github.samituga.cluedohelperapi.filter.ApiKeyAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configuration class for the security of the API.
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
    final ApiKeyAuthFilter apiKeyAuthFilter = apiKeyAuthFilter();
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


  /**
   * Builds the APIKeyAuthFilter.
   *
   * @return The APIKeyAuthFilter
   */
  private ApiKeyAuthFilter apiKeyAuthFilter() {
    ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);
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
