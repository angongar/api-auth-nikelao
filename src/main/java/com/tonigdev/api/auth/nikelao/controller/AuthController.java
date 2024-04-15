package com.tonigdev.api.auth.nikelao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonigdev.api.auth.nikelao.jwt.EmailPasswordAuthenticationToken;
import com.tonigdev.api.auth.nikelao.jwt.JwtTokenUtil;
import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;
import com.tonigdev.api.auth.nikelao.request.AuthRequest;
import com.tonigdev.api.auth.nikelao.response.AuthResponse;
import com.tonigdev.api.auth.nikelao.service.NikelaoUserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtUtil;
	private final NikelaoUserDetailsService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request){
		
		try {
			String username = request.getUsername();
			String pass = request.getPass();
			
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, pass));
			
			NikelaoUsers user = (NikelaoUsers) userService.loadUserByUsername(username);
			
			String token = jwtUtil.generateToken(user);
			
			AuthResponse response = new AuthResponse(token);
			
			return ResponseEntity.ok(response);
			
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
}
