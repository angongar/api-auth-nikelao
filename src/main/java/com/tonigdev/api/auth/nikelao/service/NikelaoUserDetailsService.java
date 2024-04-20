package com.tonigdev.api.auth.nikelao.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tonigdev.api.auth.nikelao.model.NikelaoRoles;
import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;
import com.tonigdev.api.auth.nikelao.repositories.INikelaoRolesRepository;
import com.tonigdev.api.auth.nikelao.repositories.INikelaoUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NikelaoUserDetailsService implements UserDetailsService {

	private final INikelaoUserRepository repository;
	private final INikelaoRolesRepository rolRepository;

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
	
	public UserDetails createUser(String username, String pass) throws Exception {
		NikelaoUsers user = null;
		NikelaoUsers userRegister = null;
		Optional<NikelaoUsers> userBD = null;
		NikelaoRoles rol = null;
		
		userBD = repository.findByUsername(username);
		
		if(!userBD.isPresent()) {
			user = new NikelaoUsers();
			
			rol = getRolUser();
			
			user.createUser(username, pass);
			
			user.getRoles().add(rol);

			userRegister = repository.save(user);
			
		}else {
			throw new Exception("El usuario con el nombre de usuario indicado ya existe.");
		}
		
		return userRegister;
	}
	

	private NikelaoRoles getRolUser() {
		Optional<NikelaoRoles> rolBD = null;
		NikelaoRoles rol = null;
		
		rolBD = rolRepository.findById(Long.valueOf(2));
		
		if(rolBD.isPresent()) {
			rol = rolBD.get();
		}
		
		return rol;
	}

}
