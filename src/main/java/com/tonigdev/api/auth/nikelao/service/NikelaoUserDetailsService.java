package com.tonigdev.api.auth.nikelao.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;
import com.tonigdev.api.auth.nikelao.repositories.INikelaoUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NikelaoUserDetailsService implements UserDetailsService {

	private final INikelaoUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		NikelaoUsers user = null;
		
		Optional<NikelaoUsers> optional = repository.findByUsername(username);

		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}

		return user;
	}

}
