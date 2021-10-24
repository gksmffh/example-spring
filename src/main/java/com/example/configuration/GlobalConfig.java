package com.example.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class GlobalConfig {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	private String uploadFilePath;
	private String uploadResourcePath;
	private String SchedulerCronExample1;
	
	private boolean local;
	private boolean dev;
	private boolean prod;
	
	@PostConstruct //클래스가 인스턴스와될떄 바로 실행시키는 메서드로지정
	public void init() {
		logger.info("init");
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		String activeProfile = "local";
		if(ObjectUtils.isNotEmpty(activeProfiles)) {
			activeProfile = activeProfiles[0];
		}
		String resourcePath = String.format("classpath:globals/global-%s.properties",activeProfile);
		try {
			Resource resource = resourceLoader.getResource(resourcePath);
			Properties properties =PropertiesLoaderUtils.loadProperties(resource);
			uploadFilePath = properties.getProperty("uploadFile.path");
			uploadResourcePath = properties.getProperty("uploadFile.resourcePath");
			SchedulerCronExample1 = properties.getProperty("scheduler.cron.example");
			
			this.local = activeProfile.equals("local");
			this.local = activeProfile.equals("dev");
			this.local = activeProfile.equals("prod");
			
		} catch(Exception e) {
			logger.error("e", e);
		}
	}
	
	public String getUploadFilePath() {
		return uploadFilePath;
	}
	public String getUploadResourcePath() {
		return uploadResourcePath;
	}
	public String getSchedulerCronExample1() {
		return SchedulerCronExample1;
	}
	public boolean isLocal() {//로컬 프로필인경우 사용해서 로직구현
		return local;
	}
	public boolean isdDev() {//개발 프로필인경우 사용해서 로직구현
		return dev;
	}
	public boolean isProv() {//운영 프로필인경우 사용해서 로직구현
		return prod;
	}
}
