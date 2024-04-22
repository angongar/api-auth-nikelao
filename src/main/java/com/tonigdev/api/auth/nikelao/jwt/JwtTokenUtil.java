package com.tonigdev.api.auth.nikelao.jwt;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtil {
	
	@Value("${auth.secret}")
	private String tokenSecret;
	
	@Value("${auth.expires}")
	private Long tokenExpiration;
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(tokenSecret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	
	public String generateToken(NikelaoUsers user) {
		Claims claims = Jwts.claims();
        // Añade los campos como claims
        claims.put("username", user.getUsername());
        claims.put("id", user.getId());
        
        List<String> authorities = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();
        claims.put("authorities", authorities);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, tokenSecret)
				.compact();
	}
	
	public boolean validateAccessToken(String token) {
			Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
			return true;
	}

}
