package com.wu.framework.easy.mysql.listener.config;

import lombok.Data;

@Data
public class GeneralLog {

    private String eventTime;
    private String userHost;
    private String threadId;
    private String serverId;
    private String commandType;
    private byte[] argument;

}