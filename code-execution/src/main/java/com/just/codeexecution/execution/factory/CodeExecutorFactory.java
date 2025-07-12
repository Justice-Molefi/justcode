package com.just.codeexecution.execution.factory;

import com.just.codeexecution.execution.processor.CodeExecutor;
import com.just.codeexecution.execution.language.JavaCodeExecutor;
import com.just.codeexecution.websocket.WebsocketService;

public class CodeExecutorFactory {

    public static CodeExecutor getExecutor(String name, WebsocketService service){
        String processedName = name.trim().toLowerCase();
        if(processedName.equals("java")){
            return new JavaCodeExecutor(service);
        }

        throw new IllegalArgumentException();
    }
}
