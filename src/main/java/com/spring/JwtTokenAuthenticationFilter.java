package com.spring;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mkl.mkltest.utility.AuthorityCryptor;


@Component
@Order(1)
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	public static String TOKEN_NAME = "AV-ACCESS-KEY";
	public static String TOKEN_SECRET = "avissomethingveryseret";
	public static String TOKEN_ISSUER = "anvui";
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = this.getToken(request);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUER).build();
			DecodedJWT jwt = verifier.verify(token);

			String id = jwt.getId();
			String creditical = "none";
			List<String> permissions = new ArrayList<String>();
			
			try {
				creditical = jwt.getAudience().get(0);
				permissions = AuthorityCryptor.decodeFromHex(jwt.getAudience().get(1));
			} catch (Exception e) {
				// no need anything here
			}
					
			//TODO decode token to gen Authorities 
			List<SimpleGrantedAuthority> authorities = new LinkedList<SimpleGrantedAuthority>();
			
	        for (String permission : permissions) {
	        	authorities.add(new SimpleGrantedAuthority(permission));
	        }
			
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(id, creditical, authorities));
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println("ex: " + e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}

	}

	private String getToken(HttpServletRequest request) {

		String token = null;

		if (request.getParameter(TOKEN_NAME) != null) {
			token = request.getParameter(TOKEN_NAME);
		}

		if (request.getHeader(TOKEN_NAME) != null) {
			token = request.getHeader(TOKEN_NAME);
		}

		if (request.getHeader("Authorization") != null) {
			token = request.getHeader("Authorization").replaceAll("Bearer", "").trim();
		}

		if (WebUtils.getCookie(request, TOKEN_NAME) != null) {
			token = WebUtils.getCookie(request, TOKEN_NAME).getValue();
		}

		return token;
	}

}