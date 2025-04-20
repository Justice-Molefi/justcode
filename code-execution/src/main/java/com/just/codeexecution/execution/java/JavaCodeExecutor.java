package com.just.codeexecution.execution.java;

import com.just.codeexecution.dto.ProcessResult;
import com.just.codeexecution.websocket.WebsocketService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class JavaCodeExecutor {
    private final WebsocketService websocketService;
    private final String destination = "queue/response/";

    public JavaCodeExecutor(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    private Map<String, File> writeToTempFile(String code) throws FileNotFoundException {
        Map<String, File> files = new HashMap<>();
        File tempDir = new File("temp");

        if(!tempDir.mkdirs())
            System.out.println("Directory Already Exists: " + tempDir.getAbsolutePath());

        File codeFile = new File(tempDir, "TempCode.java");
        try(PrintWriter writer = new PrintWriter(codeFile)){
            writer.write(code);
        }

        files.put("tempDir", tempDir);
        files.put("codeFile", codeFile);

        return files;
    }

    private int compileCode(File tempDir, UUID id) throws IOException, InterruptedException {
        List<String> command = Arrays.asList(
                "docker", "run", "--rm",
                "-v", tempDir.getAbsolutePath() + ":/app",
                "--memory", "256m",
                "--cpus", "0.5",
                "--network", "none",
                "java-secure-exec",
                "javac", "/app/TempCode.java"
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        readStream(process.getInputStream(), id);

        return process.waitFor();
    }
    // Execute compiled Java code in Docker
    private void run(File tempDir, UUID id) throws IOException, InterruptedException {
        List<String> command = Arrays.asList(
                "docker", "run", "--rm",
                "-v", tempDir.getAbsolutePath() + ":/app",
                "--memory", "256m",
                "--cpus", "0.5",
                "--network", "none",
                "java-secure-exec",
                "java", "-cp", "/app", "TempCode"
        );


        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Capture output and error streams
        readStream(process.getInputStream(), id);
        process.waitFor();
    }

    public void executeCode(String code, UUID id) throws IOException, InterruptedException {
        Map<String, File> files = writeToTempFile(code);
        File tempDir = files.get("tempDir");
        File codeFile = files.get("codeFile");

        try{
            // Step 1: Compile the code
            int exitCode = compileCode(tempDir,id);

            // Step 2: Execute if compilation succeeded
            if (exitCode == 0) {
                websocketService.sendOutput(id,"Compilation successful");
                websocketService.sendOutput(id,"Executing code...");
                run(tempDir, id);
            } else {
                System.out.println("Execution skipped due to compilation failure.");
            }
        }finally {
            cleanUp(codeFile, tempDir);
        }
    }

    private void cleanUp(File codeFile, File tempDir){
        if(!codeFile.delete())
            throw new RuntimeException("failed to delete file: " + codeFile.getName());

        if(!new File(tempDir, "TempCode.class").delete())
            throw new RuntimeException("failed to delete file: TempCode.class");

        if(!tempDir.delete())
            throw new RuntimeException("failed to delete file: " + tempDir.getName());
    }

    // Utility to read stream into string
    private void readStream(InputStream stream, UUID id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;

            System.out.println("Code Output");
            System.out.println("==================");

            while ((line = reader.readLine()) != null) {
                if(websocketService != null)
                    websocketService.sendOutput(id, line);
                System.out.println(line);
            }
        }
    }
}
