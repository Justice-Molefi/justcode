package com.just.requesthandler.controller;


import com.just.requesthandler.dto.CodeRequest;
import com.just.requesthandler.dto.QueueDataWrapper;
import com.just.requesthandler.service.CodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/run")
public class RequestHandlerController {
    private final CodeService service;

    public RequestHandlerController(CodeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> run(@Valid @RequestBody CodeRequest codeRequest){
//        System.out.println("Code: " + codeRequest.getCode());
//        System.out.println("Language: " + codeRequest.getLanguage());
//        System.out.println("Timeout: " + codeRequest.getTimeout());

        UUID uuid = UUID.randomUUID();
        QueueDataWrapper queueDataWrapper = new QueueDataWrapper(codeRequest.getCode(), codeRequest.getLanguage(), uuid, codeRequest.getTimeout());
        service.forward(queueDataWrapper);
        return ResponseEntity.status(HttpStatus.OK).body(uuid.toString());
    }
}
