package com.jdutton.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdutton.photoapp.api.users.ui.model.UserLoginRequestModel;

//@EnableWebSecurity
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
	    throws AuthenticationException {
	try {
	    UserLoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(),
		    UserLoginRequestModel.class);

	    return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
		    credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
	    final FilterChain chain, final Authentication auth) throws IOException, ServletException {

    }
}
