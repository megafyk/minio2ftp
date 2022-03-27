package com.example.minio2ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioProps {
    @Value("${storage.minio.secretKey}")
    public String secretKey;
    @Value("${storage.minio.accessKey}")
    public String accessKey;
    @Value("${storage.minio.host}")
    public String host;

}
