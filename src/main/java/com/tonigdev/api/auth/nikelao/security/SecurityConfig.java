package com.tonigdev.api.auth.nikelao.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tonigdev.api.auth.nikelao.security.auth.NikelaoAuthenticationManager;
import com.tonigdev.api.auth.nikelao.service.NikelaoUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final NikelaoUserDetailsService userDetailsService;

	@Bean
	DaoAuthenticationProvider usernameAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}


	@Bean
	DaoAuthenticationProvider emailAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setHideUserNotFoundExceptions(false);
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		return new NikelaoAuthenticationManager(List.of(usernameAuthenticationProvider(), emailAuthenticationProvider()));
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
