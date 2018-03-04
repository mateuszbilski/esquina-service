package com.squeezedlemon.esquina.service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String USERS_BY_USERNAME_QUERY = "SELECT AC_NAME AS username, AC_ENC_PASS AS password, AC_ENABLED AS enabled FROM"
			+ " ACCOUNTS WHERE AC_DELETED IS NULL AND AC_NAME = ?";
	
	private static final String AUTHORITIES_BY_USERNAME_QUERY = "SELECT AC_NAME AS username, AC_ROLE AS authority FROM ACCOUNTS"
			+ " WHERE AC_DELETED IS NULL AND AC_NAME = ? ";
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
			.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY).passwordEncoder(passwordEncoder());
	}

	@Bean 
	@Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.httpBasic().and()
			.csrf().disable();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
