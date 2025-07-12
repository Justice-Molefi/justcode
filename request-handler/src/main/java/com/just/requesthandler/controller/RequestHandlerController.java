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
@CrossOrigin(origins = "http://localhost:4200/")
public class RequestHandlerController {
    private final CodeService service;

    public RequestHandlerController(CodeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> run(@Valid @RequestBody CodeRequest codeRequest){
        String uuid = codeRequest.getUuid() == null ? UUID.randomUUID().toString() : codeRequest.getUuid();

        QueueDataWrapper queueDataWrapper = new QueueDataWrapper(codeRequest.getCode(), codeRequest.getLanguage(), uuid, codeRequest.getTimeout());
        service.forward(queueDataWrapper);
        return ResponseEntity.status(HttpStatus.OK).body(uuid);
    }
}
