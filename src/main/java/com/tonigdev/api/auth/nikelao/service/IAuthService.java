package com.tonigdev.api.auth.nikelao.service;

import org.springframework.http.ResponseEntity;

import com.tonigdev.api.auth.nikelao.response.APINikelaoResponseRest;

public interface IAuthService{
	
	public ResponseEntity<APINikelaoResponseRest> login(String username, String pass);
	
	public ResponseEntity<APINikelaoResponseRest> register(String username, String pass);
	

}
