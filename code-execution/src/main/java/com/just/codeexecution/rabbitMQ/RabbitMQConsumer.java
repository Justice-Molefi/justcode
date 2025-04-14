package com.just.codeexecution.rabbitMQ;

import com.just.codeexecution.dto.CodeRequest;
import com.just.codeexecution.execution.java.JavaCodeExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final String queueName = "code.request";
    private final JavaCodeExecutor javaCodeExecutor;

    public RabbitMQConsumer(JavaCodeExecutor javaCodeExecutor) {
        this.javaCodeExecutor = javaCodeExecutor;
    }

    @RabbitListener(queues = queueName)
    public void handleMessage(CodeRequest codeRequest){
        log.info("Received payload {} from Queue {}", codeRequest, queueName);

        try{
            javaCodeExecutor.executeCode(codeRequest.getCode());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
