package com.example.project2.auth;


import com.example.project2.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class registerreq {
 
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private Role role;
}
