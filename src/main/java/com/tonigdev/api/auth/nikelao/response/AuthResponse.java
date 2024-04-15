package com.tonigdev.api.auth.nikelao.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponse {
	
	private String accessToken;
	
	public AuthResponse() {
		super();
	}
	
	public AuthResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

}
