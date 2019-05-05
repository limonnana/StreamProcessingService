package com.limonnana.streamProcessingService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Async("threadPoolTaskExecutor")
    public CompletableFuture <Map<String,Long>> findCounter(String mapCounter) throws InterruptedException {

        logger.info("Looking up " + mapCounter);
        String url = String.format("http://localhost:8080/%s", mapCounter);
        Map<String,Long> results = restTemplate.getForObject(url, Map.class);

        return CompletableFuture.completedFuture(results);
    }

}
