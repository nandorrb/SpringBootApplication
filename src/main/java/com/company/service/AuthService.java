package com.company.service;

import com.company.dto.LoginInfoDto;

public interface AuthService {

	LoginInfoDto login(String username);
}
