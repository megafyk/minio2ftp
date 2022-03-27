package com.example.minio2ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Slf4j
@Service
public class FtpOpt {
    FtpRemoteFileTemplate ftpTemplate;
    FtpSession ftpSession;

    public FtpOpt(FtpRemoteFileTemplate ftpTemplate, DefaultFtpSessionFactory defaultFtpSessionFactory) {
        this.ftpTemplate = ftpTemplate;
        this.ftpSession = defaultFtpSessionFactory.getSession();
    }

    public void download() throws IOException {
        File file = new File("test.jpg");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try {
                boolean success = ftpTemplate.get("test.jpg", stream -> FileCopyUtils.copy(stream, fileOutputStream));
                if (success) {
                    log.info("Download file success");
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    public void upload() {
        File file = new File("test.jpg");
        Message<File> msg = MessageBuilder.withPayload(file).build();
        ftpTemplate.send(msg, "/test", FileExistsMode.REPLACE);
    }

    public void createDirectory(String absolutePath) throws IOException {
        String[] subDirs = absolutePath.split("/");
        String rootDir = "/" ;
        for (int i = 0; i < subDirs.length - 1; i++) {
            rootDir = rootDir.concat(subDirs[i]).concat("/");
            ftpSession.getClientInstance().makeDirectory(rootDir);
        }
    }
}
