package com.tonigdev.api.auth.nikelao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.tonigdev.api.auth.nikelao.model.NikelaoUser;

@Repository
public interface INikelaoUserRepository extends JpaRepository<NikelaoUser, Long>{
	
	public Optional<NikelaoUser> loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public Optional<NikelaoUser> loadUserByEmail(String email) throws UsernameNotFoundException;

}
