package com.just.requesthandler.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String queueName = "code.request";
    private static final String topicExchangeName = "code.exchange";
    private static final String routingKey = "code.request";

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.deactivateDefaultTyping();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(mapper);
        jackson2JsonMessageConverter.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.INFERRED);
        return jackson2JsonMessageConverter;
    }

    @Bean
    Queue codeRequestQueue(){
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }

    public String getQueueName(){ return queueName; }
    public String getTopicExchangeName(){ return topicExchangeName; }
    public String getRoutingKey(){ return routingKey; }
}
