package com.example.minio2ftp;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinioOpt {
    private final MinioClient minioClient;

    public MinioOpt(MinioProps minioProps) {
        minioClient = MinioClient.builder()
                .credentials(minioProps.accessKey, minioProps.secretKey)
                .endpoint(minioProps.host)
                .build();
    }

    public void listBuckets() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<Bucket> bucketList = minioClient.listBuckets();
        for (Bucket bucket : bucketList) {
            log.info(bucket.creationDate() + ", " + bucket.name());
        }
    }

    public void listObjects() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                .bucket("test")
                .recursive(true)
                .startAfter("HELP.md")
                .includeUserMetadata(true)
                .maxKeys(1000)
                .build();
        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);
        int count = 0;
        Iterator<Result<Item>> itr = results.iterator();
        while (itr.hasNext()) {
            Result<Item> item = itr.next();
            log.info(item.get().objectName());
            count++;
        }
        log.info(String.valueOf(count));
    }

    public void putObject() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File file = new File("HELP.md");
        InputStream inputStream = new FileInputStream(file);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket("customer")
                .object(file.getName())
                .stream(inputStream, -1, 10485760)
                .build();
        minioClient.putObject(putObjectArgs);
    }

    public void getPresignedObjectUrl() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket("customer")
                .object("HELP.md")
                .expiry(1, TimeUnit.MINUTES)
                .build();
        log.info(minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs));
    }
}
