package com.just.requesthandler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CodeRequest {

    @NotBlank(message = "Code cannot be empty")
    @Size(max = 10000, message = "This is too much code yo!, keep it less than 10 MB")
    private String code;

    @NotBlank(message = "Language cannot be empty")
    private String language;

    private int timeout = 15;
    private String uuid;

    public CodeRequest(){}

    public CodeRequest(String code, String language, int timeout, String uuid){
        this.code = code;
        this.language = language;
        this.timeout = timeout;
        this.uuid = uuid;
    }

    public String getCode(){ return this.code; }
    public String getLanguage(){ return  this.language; }
    public int getTimeout(){ return this.timeout; }
    public String getUuid(){ return this.uuid; }
}
