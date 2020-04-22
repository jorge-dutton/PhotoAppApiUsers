package com.jdutton.photoapp.api.users.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jdutton.photoapp.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final UsersService usersService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;

	public WebSecurity(final UsersService usersService,
			final BCryptPasswordEncoder bCryptPasswordEncoder,
			final Environment env) {
		super();
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.env = env;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // CSRF (Cross Site Request Forgery) disabled
		// The following regarding h2-console should be as is for working with
		// H2 console only for dev environments
		http.authorizeRequests().antMatchers("/users/**").permitAll().and()
				.addFilter(getAuthenticationFilter());
		// This is for correct show of h2-console page made of iframes
		http.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(usersService,
				env, authenticationManager());
		authFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(usersService)
				.passwordEncoder(bCryptPasswordEncoder);
	}
}
