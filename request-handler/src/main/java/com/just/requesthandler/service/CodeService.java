package com.just.requesthandler.service;


import com.just.requesthandler.dto.CodeRequest;
import com.just.requesthandler.dto.QueueCodeWrapper;
import com.just.requesthandler.rabbitMQ.RabbitMQConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig config;

    public CodeService(RabbitTemplate rabbitTemplate, TopicExchange topicExchange, RabbitMQConfig config) {
        this.rabbitTemplate = rabbitTemplate;
        this.config = config;
    }

    public void forward(QueueCodeWrapper queueCodeWrapper){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(config.getTopicExchangeName(), config.getRoutingKey(), queueCodeWrapper);
    }
}
