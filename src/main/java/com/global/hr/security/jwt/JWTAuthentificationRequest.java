package com.global.hr.security.jwt;

import lombok.Data;

@Data
public class JWTAuthentificationRequest {
	private String userName;
	private String password;
	

}
