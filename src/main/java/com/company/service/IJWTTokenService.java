package com.company.service;

import org.springframework.security.core.Authentication;

import com.company.dto.TokenDTO;
import com.company.entity.User;
import com.company.entity.Token;

import jakarta.servlet.http.HttpServletRequest;

public interface IJWTTokenService {

	String generateJWTToken(String username);
	
	Authentication parseTokenToUserInformation(HttpServletRequest request);
	
	Token generateRefreshToken(User account);
	
	boolean isRefreshTokenValid(String refreshToken);
	
	TokenDTO getNewToken(String refreshToken);
	
}
