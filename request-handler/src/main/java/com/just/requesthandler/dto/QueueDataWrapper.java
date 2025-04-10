package com.just.requesthandler.dto;

import java.util.UUID;

public class QueueDataWrapper {
    private final String code;
    private final String language;
    private final UUID uuid;
    private final int timeout;

    public QueueDataWrapper(String code, String language, UUID uuid, int timeout){
        this.code = code;
        this.language = language;
        this.uuid = uuid;
        this.timeout = timeout;
    }

    public String getCode(){ return this.code; }
    public String getLanguage(){ return this.language; }
    public UUID getUuid(){ return this.uuid; }
    public int getTimeout(){ return this.timeout; }
}
