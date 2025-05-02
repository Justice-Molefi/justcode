package com.just.codeexecution.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOutput(UUID id, String output){
        String destination = "queue/response/" + id.toString();
        messagingTemplate.convertAndSend(destination, output);
    }
}
