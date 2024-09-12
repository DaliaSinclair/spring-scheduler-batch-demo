package org.example.shedlock;

import java.net.URI;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.dynamodb2.DynamoDBLockProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class ShedlockConfig {

    @Bean
    public DynamoDbClient dynamoDbClient(final @Value("aws.dynamodb.accessKey") String accessKey,
                                         final @Value("aws.dynamodb.secretKey") String secretKey) {
        AwsCredentialsProvider credentials = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(accessKey, secretKey));
        return DynamoDbClient.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(credentials)
                .endpointOverride(URI.create("https://localhost.localstack.cloud:4566"))
                .build();
    }

    @Bean
    public LockProvider lockProvider(software.amazon.awssdk.services.dynamodb.DynamoDbClient dynamoDbClient) {
        return new DynamoDBLockProvider(dynamoDbClient, "Shedlock");
    }
}
