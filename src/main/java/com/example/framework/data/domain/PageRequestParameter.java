package com.example.framework.data.domain;

import lombok.Data;

@Data
public class PageRequestParameter<T> { //페이지 요청정보와 파라메터 정보 포함
	private MySQLPageRequest pageRequest;
	private T parameter;
	
	public PageRequestParameter(MySQLPageRequest pageRequest, T parameter) {
		this.pageRequest = pageRequest;
		this.parameter = parameter;
	}
}