package com.tonigdev.api.auth.nikelao.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tonigdev.api.auth.nikelao.model.NikelaoUsers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);

	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean res = true;

		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			res = false;
		}

		return res;
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return header.split(" ")[1].trim();
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) {
		NikelaoUsers userDetails = getUserDetails(token);

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);

		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private NikelaoUsers getUserDetails(String token) {
		NikelaoUsers userDetails = new NikelaoUsers();
		String[] jwtSubject = jwtUtil.getSubject(token).split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setUsername(jwtSubject[1]);

		return userDetails;
	}

}
