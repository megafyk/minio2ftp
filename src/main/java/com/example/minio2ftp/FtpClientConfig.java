package com.example.minio2ftp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

@Configuration
public class FtpClientConfig {
    private FtpProps ftpProps;

    public FtpClientConfig(FtpProps ftpProps) {
        this.ftpProps = ftpProps;
    }

    @Bean
    public DefaultFtpSessionFactory defaultFtpSessionFactory() {
        DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
        sessionFactory.setHost(ftpProps.host);
        sessionFactory.setPort(ftpProps.port);
        sessionFactory.setUsername(ftpProps.user);
        sessionFactory.setPassword(ftpProps.pass);
        sessionFactory.setClientMode(ftpProps.clientMode);
        sessionFactory.setConnectTimeout(ftpProps.connectionTimeout);
        sessionFactory.setDefaultTimeout(ftpProps.commandTimeout);
        sessionFactory.setDataTimeout(ftpProps.dataTimeout);
        return sessionFactory;
    }

    @Bean
    public FtpRemoteFileTemplate ftpRemoteFileTemplate(DefaultFtpSessionFactory defaultFtpSessionFactory) {
        FtpRemoteFileTemplate ftpRemoteFileTemplate = new FtpRemoteFileTemplate(defaultFtpSessionFactory);
        ftpRemoteFileTemplate.setRemoteDirectoryExpression(new LiteralExpression("/"));
        ftpRemoteFileTemplate.afterPropertiesSet();
        return ftpRemoteFileTemplate;
    }
}
