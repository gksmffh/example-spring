package com.example.mvc.domain;

public enum MenuType { //게시판 종류
	community(BoardType.COMMUNITY),
	notice(BoardType.NOTICE),
	faq(BoardType.FAQ),
	inquiry(BoardType.INQUIRY),
	;
	
	private BoardType boardType;
	
	MenuType(BoardType boardType) {
		this.boardType = boardType;
	}
	
	public BoardType getboardType () {
		return boardType;
	}
}