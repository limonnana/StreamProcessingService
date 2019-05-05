package com.limonnana.streamProcessingService;

import com.google.gson.Gson;
import net.bull.javamelody.internal.common.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;


@RestController
public class DeferredResultController {

    @Autowired
    private AsyncService service;

    @GetMapping("/deferredresult")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {

        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            String json = "";
            Gson gson = new Gson();
            StringBuilder sb = new StringBuilder();
            json = gson.toJson(Counter.counterEventType)  ;
            sb.append(json);
            json = gson.toJson(Counter.counterWords)  ;
            sb.append(json);
            output.setResult(ResponseEntity.ok(sb.toString()));
        });
        LOG.info("servlet thread freed");
        return output;
    }

}
