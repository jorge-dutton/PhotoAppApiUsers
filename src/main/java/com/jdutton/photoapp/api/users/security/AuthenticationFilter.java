package com.jdutton.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdutton.photoapp.api.users.service.UsersService;
import com.jdutton.photoapp.api.users.shared.UserDto;
import com.jdutton.photoapp.api.users.ui.model.UserLoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final UsersService usersService;
	private final Environment env; // Used to read the secret token for auth

	public AuthenticationFilter(UsersService usersService, Environment env,
			AuthenticationManager authenticationManager) {
		super();
		this.usersService = usersService;
		this.env = env;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(
			final HttpServletRequest request,
			final HttpServletResponse response) throws AuthenticationException {
		try {
			UserLoginRequestModel credentials = new ObjectMapper().readValue(
					request.getInputStream(), UserLoginRequestModel.class);

			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(
							credentials.getEmail(), credentials.getPassword(),
							new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void successfulAuthentication(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain,
			final Authentication auth) throws IOException, ServletException {

		final String username = ((User) auth.getPrincipal()).getUsername();
		final UserDto userDetails = usersService
				.getUserDetailsByEmail(username);

		// Generation of JWT (Jason Web Token)
		final String token = Jwts.builder().setSubject(userDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long
						.parseLong(env.getProperty("token.expiration.time"))))
				.signWith(SignatureAlgorithm.HS256,
						TextCodec.BASE64
								.decode(env.getProperty("token.secret")))
				.compact();
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}
}
