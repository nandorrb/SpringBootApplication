package com.company.dto;

import lombok.Data;

@Data
public class LoginInfoDto {

	private Integer id;

	private String fullname;

	private String role;

	private String token;
	
	private String refreshToken;

	private String departmentName;

}
