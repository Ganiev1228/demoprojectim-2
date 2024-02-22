package com.example.project2.dclass;

import java.util.List;

import com.example.project2.entity.expense;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class pgforexpense {

	public List<expense> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean ohirgi;

}
