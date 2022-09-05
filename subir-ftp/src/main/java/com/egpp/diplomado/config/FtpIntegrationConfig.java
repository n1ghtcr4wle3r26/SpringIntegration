package com.egpp.diplomado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ImageBanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.ReflectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

@Configuration
public class FtpIntegrationConfig {

	@Value("${remote-directory:upload-facturas}")
    private String remoteDirectory;
	
	@Bean
    DefaultFtpSessionFactory ftpFileSessionFactory(
        @Value("${ftp.host:localhost}") String host,
        @Value("${ftp.port:2121}") int port,
        @Value("${ftp.username:admin}") String username,
        @Value("${ftp.password:admin}") String password) {
        DefaultFtpSessionFactory ftpSessionFactory = new DefaultFtpSessionFactory();
        ftpSessionFactory.setHost(host);
        ftpSessionFactory.setPort(port);
        ftpSessionFactory.setPassword(password);
        ftpSessionFactory.setUsername(username);
        ftpSessionFactory.setClientMode(2);     // Passive Mode
        System.out.println("session ftp: " + ftpSessionFactory.toString());	
        return ftpSessionFactory;
    }
	

    @Bean
    IntegrationFlow files(@Value("${input-directory:../generar-factura/facturas/}") File in, Environment environment,
                                  DefaultFtpSessionFactory ftpSessionFactory) {

        return IntegrationFlows
            .from(Files.inboundAdapter(in).autoCreateDirectory(true).preventDuplicates(true).patternFilter("*.json"),
                  poller -> poller.poller(pm -> pm.fixedRate(1000)))
            .handle(Ftp.outboundAdapter(ftpSessionFactory)
                       .remoteDirectory(remoteDirectory)
                       .fileNameGenerator(message -> {
                           Object o = message.getHeaders().get(FileHeaders.FILENAME);
                           String fileName = String.class.cast(o);
                           return fileName.split("\\.")[0]+".json";
                       })).get();
    }
}
