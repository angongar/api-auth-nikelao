package com.tonigdev.api.auth.nikelao.security.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.tonigdev.api.auth.nikelao.exceptions.NikelaoAuthenticationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NikelaoAuthenticationManager implements AuthenticationManager {

	private final List<AuthenticationProvider> authenticationProviders;

	public NikelaoAuthenticationManager(List<AuthenticationProvider> authenticationProviders) {
		this.authenticationProviders = authenticationProviders;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication res = null;

		List<AuthenticationException> excepctions = new ArrayList<>();

		for (AuthenticationProvider provider : authenticationProviders) {
			try {
				res = provider.authenticate(authentication);
				if (res != null && res.isAuthenticated()) {
					return res;
				}

			} catch (AuthenticationException e) {
				excepctions.add(e);
			}
		}
		
		if(!excepctions.isEmpty()) {
			excepctions.forEach(e ->{
				log.warn(e.getMessage());
			});

			throw new NikelaoAuthenticationException();
		}

		return res;
	}

}
