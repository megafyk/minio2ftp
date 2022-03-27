package com.example.minio2ftp;

import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class OptController {
    private FtpOpt ftpOpt;
    private MinioOpt minioOpt;

    public OptController(FtpOpt ftpOpt, MinioOpt minioOpt) {
        this.ftpOpt = ftpOpt;
        this.minioOpt = minioOpt;
    }

    @GetMapping("/listBuckets")
    public void listBuckets() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //minioOpt.listBuckets();
        //minioOpt.getPresignedObjectUrl();
        minioOpt.listObjects();
    }

    @PostMapping("/putObject")
    public void putObject() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioOpt.putObject();
    }
}
