package com.example.project2.dclass;

import java.util.List;

import com.example.project2.entity.workers;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class pgresponse {


	public List<workers> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean ohirgi;

//	public List<resp> getContent() {
//		return content;
//	}


}

