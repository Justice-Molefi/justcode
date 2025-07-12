package com.just.codeexecution.execution.language;

import com.just.codeexecution.execution.processor.CompiledExecutionProcessor;
import com.just.codeexecution.websocket.WebsocketService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class JavaCodeExecutor extends CompiledExecutionProcessor {

    public JavaCodeExecutor(WebsocketService websocketService) {
        super(websocketService);
    }

    @Override
    protected void generateFile() throws FileNotFoundException {
        String fileName = "Main.java";
        File codeFile = new File(baseDir, fileName);

        try(PrintWriter writer = new PrintWriter(codeFile)){
            writer.write(code);
        }
    }

    @Override
    protected int compile() throws IOException, InterruptedException {
        List<String> command = Arrays.asList(
                "docker", "run", "--rm",
                "-v", baseDir.getAbsolutePath() + ":/app",
                "--memory", "256m",
                "--cpus", "0.5",
                "--network", "none",
                "java-secure-exec",
                "javac", "/app/Main.java"
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        readStream(process.getInputStream(), uuid);
        return process.waitFor();
    }

    @Override
    protected void run() throws IOException, InterruptedException {
        List<String> command = Arrays.asList(
                "docker", "run", "--rm",
                "-v", baseDir.getAbsolutePath() + ":/app",
                "--memory", "256m",
                "--cpus", "0.5",
                "--network", "none",
                "java-secure-exec",
                "java", "-cp", "/app", "Main"
        );


        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Capture output and error streams
        readStream(process.getInputStream(), uuid);
        process.waitFor();
    }
}
