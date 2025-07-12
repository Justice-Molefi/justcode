package com.just.codeexecution.websocket;

import com.just.codeexecution.rabbitMQ.RabbitMQConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger log = LoggerFactory.getLogger(WebsocketService.class);

    public WebsocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOutput(String id, String output){
        log.info("Sending message to client");
        String destination = "queue/response/" + id;
        messagingTemplate.convertAndSend(destination, output);
    }
}
