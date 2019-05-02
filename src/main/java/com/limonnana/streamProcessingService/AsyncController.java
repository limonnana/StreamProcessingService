package com.limonnana.streamProcessingService;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService service;

    @RequestMapping(value = "/counterAsynch")
    public String counterAsynch() throws InterruptedException, ExecutionException
    {
        CompletableFuture<Map<String,Long>> counterEventType = service.findCounter("eventType");

        CompletableFuture<Map<String,Long>> counterWords = service.findCounter("words");

        CompletableFuture.allOf(counterEventType, counterWords).join();

        String json = "";
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        json = gson.toJson(counterEventType.get());
        sb.append(json);
        json = gson.toJson(counterWords.get());
        sb.append(json);

        return sb.toString();
    }


}
