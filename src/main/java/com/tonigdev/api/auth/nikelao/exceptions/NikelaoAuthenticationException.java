package com.tonigdev.api.auth.nikelao.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NikelaoAuthenticationException extends AuthenticationException{
	private static final long serialVersionUID = -5887290656516148255L;
	
	public NikelaoAuthenticationException() {
		super(null);
	}
	
	public NikelaoAuthenticationException(String msg) {
		super(msg);
	}
	
	public NikelaoAuthenticationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
