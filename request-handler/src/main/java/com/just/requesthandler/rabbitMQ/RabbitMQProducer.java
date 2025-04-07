package com.just.requesthandler.rabbitMQ;

import com.just.requesthandler.dto.QueueDataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQProducer {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitMQConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
        this.rabbitMQConfig = rabbitMQConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(QueueDataWrapper payload){
        log.info("Publishing to {} using routingKey {}. Payload: {}", rabbitMQConfig.getTopicExchangeName(), rabbitMQConfig.getRoutingKey(), payload);
        rabbitTemplate.convertAndSend(rabbitMQConfig.getTopicExchangeName(), rabbitMQConfig.getRoutingKey(), payload);
        log.info("Published to {} using routingKey {}. Payload: {}", rabbitMQConfig.getTopicExchangeName(), rabbitMQConfig.getRoutingKey(), payload);
    }
}
