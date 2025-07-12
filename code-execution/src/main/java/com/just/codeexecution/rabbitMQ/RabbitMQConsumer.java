package com.just.codeexecution.rabbitMQ;

import com.just.codeexecution.dto.CodeRequest;
import com.just.codeexecution.execution.processor.CodeExecutor;
import com.just.codeexecution.execution.factory.CodeExecutorFactory;
import com.just.codeexecution.websocket.WebsocketService;
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
    private final WebsocketService websocketService;

    public RabbitMQConsumer(WebsocketService service) {
        this.websocketService = service;
    }

    @RabbitListener(queues = queueName)
    public void handleMessage(CodeRequest codeRequest){
        log.info("Received payload {} from Queue {}", codeRequest, queueName);

        try{
            CodeExecutor codeExecutor = CodeExecutorFactory.getExecutor(codeRequest.getLanguage(), websocketService);
            codeExecutor.process(codeRequest.getCode(), codeRequest.getUuid());
        }catch (Exception e){
            log.error(e.getMessage());
            websocketService.sendOutput(codeRequest.getUuid(), ">> Something went wrong, Please try again later");
        }
    }
}
