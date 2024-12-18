package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.company.entity.User;
import com.company.entity.Token;
import com.company.entity.Token.Type;

public interface TokenRepository extends JpaRepository<Token, Integer> {

	@Modifying
	void deleteByAccount(User account);

	Token findBykeyAndType(String key, Type type);
}
