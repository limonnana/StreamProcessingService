package com.limonnana.streamProcessingService;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    @GetMapping(value = "/eventType")
    public Map<String,Long> eventType(){

       return Counter.counterEventType;
    }

    @GetMapping(value = "/words")
    public Map<String,Long> words(){

        return Counter.counterWords;
    }


    @GetMapping(value = "/counters")
    public String counters(){

        String json = "";
         Gson gson = new Gson();
         StringBuilder sb = new StringBuilder();
         json = gson.toJson(Counter.counterEventType)  ;
         sb.append(json);
         json = gson.toJson(Counter.counterWords)  ;
         sb.append(json);
         System.out.println("id: " + Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());
        return sb.toString();
    }
}
