package com.example.project2.DTOclasses;

import lombok.Data;

@Data

public class errorresponse {

	private String message;

	public errorresponse(String message) {
		this.message = message;

	}
}
