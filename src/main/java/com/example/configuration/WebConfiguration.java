package com.example.configuration;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.example.configuration.servlet.handler.BaseHandlerInterceptor;
import com.example.framework.data.web.MySQLPageRequestHandleMethodArgumentResolver;
import com.example.mvc.domain.BaseCodeLabelEnum;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private GlobalConfig config;
	
	private static final String WINDOWS_FILE = "file:///";
	private static final String LINUX_FILE = "file:";
	
	@Bean //다국어 프로퍼티를 사용하기위한 아래 메세지 소스를 빈으로 등록
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("classpath:/messages/message");
		source.setDefaultEncoding("UTF-8");
		source.setCacheSeconds(60);
		source.setDefaultLocale(Locale.KOREAN);
		source.setUseCodeAsDefaultMessage(true);
		return source;
			
	}
	
	@Bean 
	public BaseHandlerInterceptor baseHandlerInterceptor() { 
		return new BaseHandlerInterceptor(); 
		}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(BaseCodeLabelEnum.class, new BaseCodeLabelEnumJsonSerializer());
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}
	
	@Bean
	public MappingJackson2JsonView mappingJackson2JsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
		jsonView.setObjectMapper(objectMapper());
		return jsonView;
	}
	@Bean
	public GlobalConfig config() {
		return new GlobalConfig();
	}
	
	@Bean
	public FilterRegistrationBean<SitemeshConfiguration> sitemeshBean(){ //sitemash설정
		FilterRegistrationBean<SitemeshConfiguration> filter = new FilterRegistrationBean<SitemeshConfiguration>();
		filter.setFilter(new SitemeshConfiguration());
		return filter;
	}
	@Override 
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(baseHandlerInterceptor()); 
		}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new MySQLPageRequestHandleMethodArgumentResolver());
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { //이 함수를 구현하면 브라우저에서 접근이 가능하다(url로 업로드 파일 보기가능)
		//업로드 파일 static resource 접근 경로
		String resourcePattern = config.getUploadResourcePath() + "**";
		
		if (config.isLocal()) { // 로컬(윈도우환경)
			registry.addResourceHandler(resourcePattern).addResourceLocations("file:///" + config.getUploadFilePath());
		} else { //리눅스또는 유닉스 환경
			registry.addResourceHandler(resourcePattern).addResourceLocations("file:" + config.getUploadFilePath());
			
		}
	}
	
}
