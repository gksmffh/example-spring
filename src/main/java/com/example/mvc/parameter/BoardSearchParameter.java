package com.example.mvc.parameter;

import com.example.mvc.domain.BoardType;
import lombok.Data;

@Data
public class BoardSearchParameter {
	private String keyword;
	private BoardType[] boardTypes;
	
	public BoardSearchParameter() {
		
	}
}
