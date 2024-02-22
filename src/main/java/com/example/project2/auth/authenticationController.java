package com.example.project2.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class authenticationController {
   
	private final AuthenticationService authServie;
	
	@PostMapping("/register")
	public ResponseEntity reg (@RequestBody registerreq reg) {
		return ResponseEntity.ok(authServie.register(reg));
	}

	@PostMapping("/authenticate")
	public ResponseEntity auth (@RequestBody authenticateRequest authrequest) {
		authenticationResponse auth = authServie.auth(authrequest);
		return ResponseEntity.ok(auth);
	}
}
