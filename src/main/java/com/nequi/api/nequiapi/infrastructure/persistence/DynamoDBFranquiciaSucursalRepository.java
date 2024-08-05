package com.nequi.api.nequiapi.infrastructure.persistence;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursal;
import com.nequi.api.nequiapi.domain.repository.FranquiciaSucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class DynamoDBFranquiciaSucursalRepository implements FranquiciaSucursalRepository {

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;
    @Override
    public Mono<FranquiciaSucursal> save(FranquiciaSucursal franquiciaSucursal) {
        log.info("Guardando franquiciaSucursal: {}", franquiciaSucursal);

        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("id", AttributeValue.builder().s(franquiciaSucursal.getId()).build());
        itemMap.put("franquiciaId", AttributeValue.builder().s(franquiciaSucursal.getFranquiciaId()).build());
        itemMap.put("sucursalId", AttributeValue.builder().s(franquiciaSucursal.getSucursalId()).build());


        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("FranquiciaSucursal")
                .item(itemMap)
                .build();

        CompletableFuture<PutItemResponse> future = dynamoDbAsyncClient.putItem(putItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al guardar en DynamoDB: {}", e.getMessage(), e))
                .thenReturn(franquiciaSucursal);
    }


    @Override
    public Mono<List<FranquiciaSucursal>> findById(String franquiciaId) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String franquiciaId) {
        return null;
    }
}
