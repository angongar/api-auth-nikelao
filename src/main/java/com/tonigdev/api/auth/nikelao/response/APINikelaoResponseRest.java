package com.tonigdev.api.auth.nikelao.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class APINikelaoResponseRest {
	
	private HttpStatus status;
	private String message;
	private String token;
}
