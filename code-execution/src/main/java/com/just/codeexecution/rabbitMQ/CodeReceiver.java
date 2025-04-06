package com.just.codeexecution.rabbitMQ;

import com.just.codeexecution.dto.CodeRequest;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class CodeReceiver {
    public void handleMessage(CodeRequest codeRequest){
        System.out.println("Code Received...");
        System.out.println("Code: " + codeRequest.getUuid());
        System.out.println("Code: " + codeRequest.getLanguage());
        System.out.println("Code: " + codeRequest.getCode());
    }
}
