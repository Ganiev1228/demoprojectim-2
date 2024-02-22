package com.example.project2.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// BU KLASSDI ORNIGA WORKERS DI OZINI QOYIB ISHLATIB KORISH;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class authenticateRequest {

	private String username;
	private String password;
}
