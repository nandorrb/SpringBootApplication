package com.company.controller;

import com.company.dto.LoginInfoDto;

import com.company.form.LoginForm;

import com.company.use_case.AuthUseCase;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/auth")
@Validated
public class AuthController {

	@Autowired
	private AuthUseCase authUseCase;

	@PostMapping("/login")
	public LoginInfoDto login(@RequestBody @Valid LoginForm loginForm) {
		return authUseCase.login(loginForm);
	}


}
