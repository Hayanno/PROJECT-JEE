package com.univamu.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class SSecurityService implements SecurityService {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void autologin(String email, String password, HttpServletRequest request) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		Authentication token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(token);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	}

}
