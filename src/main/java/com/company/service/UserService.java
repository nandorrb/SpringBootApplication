package com.company.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.company.entity.User;

public interface UserService extends UserDetailsService {

	public User getAccountByUsername(String username);
}
