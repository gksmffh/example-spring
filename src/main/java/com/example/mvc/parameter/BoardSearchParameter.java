package com.example.mvc.parameter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.example.mvc.domain.BoardType;

import lombok.Data;

@Data
public class BoardSearchParameter {

	private String Keyword;
	private List<BoardType> boardTypes;
	
	
	public BoardSearchParameter() {
		
	}
}
