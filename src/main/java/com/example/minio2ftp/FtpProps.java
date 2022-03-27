package com.example.minio2ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpProps {
    @Value("${storage.ftp.host}")
    String host;
    @Value("${storage.ftp.port}")
    int port;
    @Value("${storage.ftp.user}")
    String user;
    @Value("${storage.ftp.pass}")
    String pass;
    @Value("${storage.ftp.clientMode}")
    int clientMode;
    @Value("${storage.ftp.connectionTimeout}")
    int connectionTimeout;
    @Value("${storage.ftp.commandTimeout}")
    int commandTimeout;
    @Value("${storage.ftp.dataTimeout}")
    int dataTimeout;
}
