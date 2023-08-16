package io.github.poj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.Data;

/**
 * MinioConfig
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    
    private String accessKey;
    
    private String secretKey;
    
    private String bucket;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();
    }

}
