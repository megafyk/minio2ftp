package com.example.minio2ftp;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
@Slf4j
class MinioOptTest {

    @Autowired
    MinioProps minioProps;

    MinioClient minioClient;

    @Test
    void test_listObjects() {
        minioClient = MinioClient.builder()
                .credentials(minioProps.accessKey, minioProps.secretKey)
                .endpoint(minioProps.host)
                .build();

        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket("test")
                .recursive(true)
                .maxKeys(100)
                .build();
        Iterable<Result<Item>> results = minioClient.listObjects(args);
        int count = 0;
        Iterator<Result<Item>> itr = results.iterator();
        while (itr.hasNext()) {
            Result<Item> item = itr.next();
            try {
                //log.info(item.get().objectName());
                count++;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }


        }
        log.info(String.valueOf(count));
    }
}