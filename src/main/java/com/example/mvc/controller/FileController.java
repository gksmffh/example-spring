package com.example.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.example.configuration.GlobalConfig;
import com.example.configuration.exception.BaseException;
import com.example.configuration.http.BaseResponse;
import com.example.configuration.http.BaseResponseCode;
import com.example.mvc.parameter.UploadFileParameter;
import com.example.mvc.service.UploadFileService;

@RestController
@RequestMapping("/file")
@Api(tags = "업로드") //api 이름설정
public class FileController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GlobalConfig config;
	
	@Autowired
	UploadFileService uploadFileService;
	
	
	@PostMapping("/save")
	@ApiOperation(value = "업로드", notes = "")
	public BaseResponse<Boolean> save(@RequestParam("uploadFile") MultipartFile multipartFile) {
		logger.debug("config : {}", config);
		logger.debug("multipartFile : {}", multipartFile);
		if(multipartFile == null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String uploadFilePath = config.getUploadFilePath() + currentDate + "/"; 
		logger.debug("uploadFilePath : {}", uploadFilePath);
		
		
		String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") +1, multipartFile.getOriginalFilename().length());
		String filename = UUID.randomUUID().toString() + "." + prefix;   
		logger.info("filename : {}", filename);
		String pathname = uploadFilePath + filename; 
		
		File folder = new File(uploadFilePath);
		if(!folder.isDirectory()) {//폴더가 없다면 생성
			folder.mkdirs();
		}
		
		String resourcePathname = config.getUploadResourcePath() + currentDate + "/" + filename;
		File dest = new File(pathname);
		try {
			multipartFile.transferTo(dest);
			
			//파일 업로드 된 후 DB에 저장
			
			UploadFileParameter parameter = new UploadFileParameter();
			parameter.setContentType(multipartFile.getContentType());
			parameter.setOriginalFilename(multipartFile.getOriginalFilename());
			parameter.setFilename(filename);
			parameter.setPathname(pathname);
			parameter.setSize((int) multipartFile.getSize());
			parameter.setResourcePathname(resourcePathname);
			uploadFileService.save(parameter);
			
		} catch (IllegalStateException | IOException e) {
			logger.error("e",e);
		}
		
		return new BaseResponse<Boolean>(true);
	}
}