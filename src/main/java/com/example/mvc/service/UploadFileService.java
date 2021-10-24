package com.example.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvc.domain.UploadFile;
import com.example.mvc.parameter.UploadFileParameter;
import com.example.mvc.repository.UploadFileRepository;

@Service
public class UploadFileService {
	@Autowired
	UploadFileRepository repository;
	
	public void save(UploadFileParameter parameter) {
		repository.save(parameter);
	}

	public UploadFile get(int uploadFileSeq) {
		return repository.get(uploadFileSeq);
		
	}
}
