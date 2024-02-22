package com.example.project2.DTOclasses;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class expenseDto {
	public Long id;
	public String adType;
	public Long adCost;
	public Long adTerm;
	public boolean active;
	private Long workerId;
	
}
