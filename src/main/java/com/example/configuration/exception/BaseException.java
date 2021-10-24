package com.example.configuration.exception;

import com.example.configuration.http.BaseResponseCode;

public class BaseException extends AbstractBaseException { //프로젝트에서 기본예외 클래스를 사용하기위한 클래스

	  private static final long serialVersionUID = 8342235231880246631L;
	  
	  public BaseException() {
	  }
	  
	  public BaseException(BaseResponseCode responseCode) {
	  	this.responseCode = responseCode;
	  }
	  
	  public BaseException(BaseResponseCode responseCode, String[] args) {
		  this.responseCode = responseCode;
		  this.args = args;
	  }
	  
	}