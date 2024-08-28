package com.company.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.company.dto.LoginInfoDto;
import com.company.entity.User;
import com.company.entity.Token;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IJWTTokenService jwtTokenService;

	@Autowired
	private UserService accountService;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
	}

	@Override
	public LoginInfoDto login(String username) {
		// get entity
		User entity = accountService.getAccountByUsername(username);

		// convert entity to dto
		LoginInfoDto dto = modelMapper.map(entity, LoginInfoDto.class);

		// add jwt token to dto
		dto.setToken(jwtTokenService.generateJWTToken(entity.getUsername()));

		// add refresh token to dto
		Token token = jwtTokenService.generateRefreshToken(entity);
		dto.setRefreshToken(token.getKey());

		return dto;
	}
}
