package com.example.project2.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Clients implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String ism;
	private String familiya;
	private String passport_raqam;
	private long jshshir;
	private String yashash_manzili;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;
	private String createdTime;
	private boolean active;
	@ManyToOne
    @JsonIgnore
	@JoinColumn(name = "workerId")
	private workers worker;

}
