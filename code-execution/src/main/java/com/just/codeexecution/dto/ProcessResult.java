package com.just.codeexecution.dto;

public class ProcessResult {
    private final String output;
    private final String error;
    private final int exitCode;

    public ProcessResult(String output, String error, int exitCode){
        this.output = output;
        this.error = error;
        this.exitCode = exitCode;
    }

    public String getOutput(){ return this.output; }
    public String getError(){ return this.error; }
    public int getExitCode(){ return this.exitCode; }
}
