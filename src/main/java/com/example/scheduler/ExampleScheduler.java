package com.example.scheduler;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExampleScheduler {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron = "#{@schedulerCronExample1}") //beam으로 등록한 globalconfig의 cromexample값을 값을 사용
	public void schedule1() {
		logger.info("schedule1동작하고 있음: {}", Calendar.getInstance().getTime());
	}
}
