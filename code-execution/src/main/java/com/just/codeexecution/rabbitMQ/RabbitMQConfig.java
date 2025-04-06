package com.just.codeexecution.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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
        return new Jackson2JsonMessageConverter();
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

    @Bean
    MessageListenerAdapter messageListenerAdapter(CodeReceiver codeReceiver){
        return new MessageListenerAdapter(codeReceiver, jsonMessageConverter());
    }

    @Bean
    SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    public String getQueueName(){ return queueName; }
    public String getTopicExchangeName(){ return topicExchangeName; }
    public String getRoutingKey(){ return routingKey; }
}
