package com.example.project2.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class expense implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String adType;
	private Long adCost;
	private Long adTerm;
	@JsonIgnore
	@Column(updatable = false)
	@JsonFormat(pattern = "HH:mm:ss  dd:MM:yyyy")
	private LocalDateTime starttime;
	@JsonIgnore
	private LocalDateTime endtime;
	private boolean active;
	@ManyToOne
	@JoinColumn(name = "worker_id")
	@JsonIgnore
	private workers worker;

}
