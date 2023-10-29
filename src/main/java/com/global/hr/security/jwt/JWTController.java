package com.global.hr.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/authentificate")
public class JWTController {
	@Autowired
	private JWTService jwtService;
	@Autowired
	private  AuthenticationManager authenticationManager;

	@PostMapping()
public String getTokenForAuthentificatedUser(@RequestBody JWTAuthentificationRequest authRequest) {
		//dakhelna user w aamalna token mais yelzemna manaamlou token ken manthabtu l user hedheka mawjud f database mch ay user
		Authentication authentication = authenticationManager
				.authenticate (new UsernamePasswordAuthenticationToken (authRequest.getUserName()
						, authRequest.getPassword()));	
		if (authentication.isAuthenticated()){
			return jwtService.getGeneratedToken(authRequest.getUserName());	
		}else {
			throw new UsernameNotFoundException("Invalid User Credentials");
		}


}

}
