package com.example.mvc.domain;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class Board {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
	private Timestamp regDate;
}
