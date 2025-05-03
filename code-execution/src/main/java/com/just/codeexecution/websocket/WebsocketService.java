package com.just.codeexecution.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOutput(String id, String output){
        String destination = "queue/response/" + id;
        messagingTemplate.convertAndSend(destination, output);
    }
}
