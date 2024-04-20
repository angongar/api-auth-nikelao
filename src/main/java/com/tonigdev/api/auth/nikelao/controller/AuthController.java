package com.tonigdev.api.auth.nikelao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonigdev.api.auth.nikelao.request.AuthRequest;
import com.tonigdev.api.auth.nikelao.response.APINikelaoResponseRest;
import com.tonigdev.api.auth.nikelao.service.IAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final IAuthService service;
	
	
	@PostMapping("/login")
	public ResponseEntity<APINikelaoResponseRest> login(@RequestBody AuthRequest request){
		return service.login(request.getUsername(), request.getPass());
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<APINikelaoResponseRest> register(@RequestBody AuthRequest request){
		return service.register(request.getUsername(), request.getPass());
		
	}
	
}
