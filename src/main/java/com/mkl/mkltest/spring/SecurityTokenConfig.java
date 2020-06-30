package com.mkl.mkltest.spring;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	public static String TOKEN_NAME = "AV-ACCESS-KEY";
	public static String TOKEN_SECRET = "avissomethingveryseret";
	public static String TOKEN_ISSUER = "mkl";
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String bypass[] = { "/status/firestore", "/", "/welcome", "/user/login", "/user/register" };
		String ignore[] = { "/v2/api-docs","/csrf", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
				"/swagger-ui.html", "/webjars/**" };
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable()
				// make sure we use stateless session; session won't be used to store user's
				// state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// handle an authorized attempts
				.exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
				// Add a filter to validate the tokens with every request
				.addFilterBefore(new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				// authorization requests config
				.authorizeRequests()
				// allow all who are accessing "auth" service
				.antMatchers(null, bypass).permitAll()
				.antMatchers(ignore).permitAll()
				// Any other request must be authenticated
				.anyRequest().authenticated();
		
//		http.authorizeRequests().antMatchers(HttpMethod.POST, "/seat-map").hasRole("");
//		http.authorizeRequests().antMatchers(HttpMethod.POST, "/seat-map").hasRole("CREATE_SEAT_MAP");
	}
}