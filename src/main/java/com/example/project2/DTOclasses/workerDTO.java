package com.example.project2.DTOclasses;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.project2.Enums.Role;
import com.example.project2.entity.Korxona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class workerDTO {
    private Long id;
	private String ismi;
	private String password;
	private String username;
	private String familiyasi;
	private int yoshi;
	private long maosh;
	private String yashash_manzili;
	private String passportNumber;
	private int jshshir;
	private String millati;
	private Korxona korxona;
	private Role role;
}
