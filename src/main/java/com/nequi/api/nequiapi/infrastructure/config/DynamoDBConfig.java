package com.nequi.api.nequiapi.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
@Slf4j
@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.region}")
    private String dynamoDbRegion;

    @Value("${aws.dynamodb.access-key}")
    private String dynamoDbAccessKey;

    @Value("${aws.dynamodb.secret-key}")
    private String dynamoDbSecretKey;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        log.info("Configurando DynamoDbAsyncClient con la región: {}", dynamoDbRegion);
        log.info("Configurando DynamoDbAsyncClient con el accessKey: {}", dynamoDbAccessKey);

        return DynamoDbAsyncClient.builder()
                .region(Region.of(dynamoDbRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(dynamoDbAccessKey, dynamoDbSecretKey)))
                .build();

    }
}
