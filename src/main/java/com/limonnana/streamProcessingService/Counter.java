package com.limonnana.streamProcessingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Counter {

    public static Map<String, Long> counterEventType = new HashMap<String, Long>();
    public static Map<String, Long> counterWords = new HashMap<String, Long>();


    public static void addEvent(String key, String value){

        add(counterEventType, key);

        String[] words = value.split(" ");

        for(String w : words){
            add(counterWords, w);
        }

    }

    private static void add(Map<String, Long> m, String key){

        Long existKey = m.get(key);

        if(existKey != null){
            Long c = new Long(existKey.intValue() + 1);
            m.put(key, c);
        }else{
            m.put(key, 0L);
        }
    }



}
