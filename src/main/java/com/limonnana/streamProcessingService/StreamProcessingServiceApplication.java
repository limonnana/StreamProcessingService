package com.limonnana.streamProcessingService;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableAsync
public class StreamProcessingServiceApplication {


	@Autowired
	private Environment env;

	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(800);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}


	public static void main(String[] args) throws IOException{

		SpringApplication.run(StreamProcessingServiceApplication.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	private void runBlackBox()throws IOException{

		Runtime rt = Runtime.getRuntime();

		Process pr = rt.exec(env.getProperty("pathToBlackBox"));

		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

		String line=null;

		while((line=input.readLine()) != null) {

			Gson gson = new Gson();

			try {
				Line l = gson.fromJson(line, Line.class);

				Counter.addEvent(l.getEvent_type(), l.getData());

			}catch (Exception e){

			}
		}


	}
	}




