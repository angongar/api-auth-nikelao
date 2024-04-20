package com.tonigdev.api.auth.nikelao.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tonigdev.api.auth.nikelao.jwt.JwtTokenUtil;
import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;
import com.tonigdev.api.auth.nikelao.response.APINikelaoResponseRest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtUtil;
	private final NikelaoUserDetailsService userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<APINikelaoResponseRest> login(String username, String pass){
		log.info("AuthService.login(username, pass) - Se inició el método");
		
		NikelaoUsers user = null;
		ResponseEntity<APINikelaoResponseRest> response = null;
		APINikelaoResponseRest apiResponse = new APINikelaoResponseRest();
		String token = null;
		
		try {
			
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, pass));
			
			user = (NikelaoUsers) userService.loadUserByUsername(username);
			
			token = jwtUtil.generateToken(user);
			
			apiResponse.setMessage("Se ha logeado el usuario correctamente.");
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setToken(token);
			
			response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
			
		}catch(BadCredentialsException e) {
			log.error("AuthService.login(username, pass) - Credenciales erroneas." + e.getMessage());
			apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
			apiResponse.setMessage("Credenciales erroneas.");
			response = new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<APINikelaoResponseRest> register(String username, String pass) {
		log.info("AuthService.register(username, pass) - Se inició el método");
		
		NikelaoUsers user = null;
		ResponseEntity<APINikelaoResponseRest> response = null;
		APINikelaoResponseRest apiResponse = new APINikelaoResponseRest();	
		String token = null;
		
		try {
			
			user = (NikelaoUsers) userService.createUser(username, passwordEncoder.encode(pass));
			
			token = jwtUtil.generateToken(user);
			
			apiResponse.setMessage("Se ha registrado el usuario correctamente.");
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setToken(token);
			
			response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
	
		}catch (Exception e) {
			log.error("AuthService.register(username, pass) - Error al registrar el usuario." + e.getMessage());
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setMessage("Se ha producido un error al registrar el usuario.");
			
			response = new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
		return response;
	}


}
