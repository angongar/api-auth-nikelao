package com.tonigdev.api.auth.nikelao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;

@Repository
public interface INikelaoUserRepository extends JpaRepository<NikelaoUsers, Long>{
	
	public Optional<NikelaoUsers> findByUsername(String username) throws UsernameNotFoundException;
	
}
