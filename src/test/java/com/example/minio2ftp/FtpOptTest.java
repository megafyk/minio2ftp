package com.example.minio2ftp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FtpOptTest {

    @Autowired
    FtpOpt ftpOpt;

    @Test
    void test_download() throws IOException {
        ftpOpt.download();
    }

    @Test
    void test_upload() {
        ftpOpt.upload();
    }

    @Test
    void test_createDirectory() throws IOException {
        ftpOpt.createDirectory("/test1/test2/test.jpg");
    }
}