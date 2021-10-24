package com.example.mvc.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.example.mvc.domain.Board;
import com.example.mvc.domain.BoardType;
import lombok.Data;

@Data //getter,setter 추가
public class Board {
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
	private int viewCount;
	private Timestamp regDate;
	private boolean delYn;
	
}
