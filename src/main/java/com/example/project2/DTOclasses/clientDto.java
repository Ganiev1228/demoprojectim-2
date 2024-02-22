package com.example.project2.DTOclasses;

import com.example.project2.entity.workers;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class clientDto {
    private Long id;
	private String ism;
	private String familiya;
	private String passport_raqam;
	private long jshshir;
	private String yashash_manzili;
	private boolean active;
	private Long workerId;
}
