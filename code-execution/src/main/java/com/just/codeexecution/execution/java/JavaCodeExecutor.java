package com.just.codeexecution.execution.java;

import com.just.codeexecution.dto.ProcessResult;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JavaCodeExecutor {
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

    private ProcessResult compileCode(File tempDir, File codeFile) throws IOException, InterruptedException {
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
        Process process = pb.start();

        // Capture output and error streams
        String output = readStream(process.getInputStream());
        String error = readStream(process.getErrorStream());
        int exitCode = process.waitFor();

        return new ProcessResult(output, error, exitCode);
    }
    // Execute compiled Java code in Docker
    private static ProcessResult run(File tempDir) throws IOException, InterruptedException {
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
        Process process = pb.start();

        // Capture output and error streams
        String output = readStream(process.getInputStream());
        String error = readStream(process.getErrorStream());
        int exitCode = process.waitFor();

        return new ProcessResult(output, error, exitCode);
    }

    public void executeCode(String code) throws IOException, InterruptedException {
        Map<String, File> files = writeToTempFile(code);
        File tempDir = files.get("tempDir");
        File codeFile = files.get("codeFile");

        try{
            // Step 1: Compile the code
            ProcessResult compileResult = compileCode(tempDir, codeFile);
            System.out.println("Compilation Exit Code: " + compileResult.getExitCode());
            System.out.println("Compilation Output: " + compileResult.getOutput());
            System.out.println("Compilation Error: " + compileResult.getError());

            // Step 2: Execute if compilation succeeded
            if (compileResult.getExitCode() == 0) {
                ProcessResult execResult = run(tempDir);
                System.out.println("Execution Exit Code: " + execResult.getExitCode());
                System.out.println("Execution Output: " + execResult.getOutput());
                System.out.println("Execution Error: " + execResult.getError());
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
    private static String readStream(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }
}
