package com.example.project2.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project2.Repository.workersRepository;
import com.example.project2.entity.workers;
import com.example.project2.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final AuthenticationManager authmanager;
	private final workersRepository workrepo;
	private final JwtService jwtserv;
    private final PasswordEncoder passencode;
	
    public authenticationResponse register (registerreq reg) {
		var user = workers.builder()
				.ismi(reg.getFirstname())
		        .familiyasi(reg.getLastname())
		        .username(reg.getUsername())
		        .password(passencode.encode(reg.getPassword()))
		        .role(reg.getRole())
		        .build();
		var savedworker = workrepo.save(user);
		var jwttoken = jwtserv.generateToken(user);
		return authenticationResponse.builder()
				.token(jwttoken)
				.build();
	}
	
	
	
	public authenticationResponse auth (authenticateRequest authrequest) {
		authmanager.authenticate( new UsernamePasswordAuthenticationToken(
				authrequest.getUsername(),
				authrequest.getPassword())
				);
		var user = workrepo.findByUsername(authrequest.getUsername()).orElseThrow();

		var jwtToken= jwtserv.generateToken(user);
		return authenticationResponse
				.builder()
				
				.token(jwtToken)
				.build();
	}
}
