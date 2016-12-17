package com.univamu.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
	
	void autologin(String email, String password, HttpServletRequest request);
}
