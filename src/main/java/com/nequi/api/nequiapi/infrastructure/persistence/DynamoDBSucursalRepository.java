package com.nequi.api.nequiapi.infrastructure.persistence;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.Sucursal;
import com.nequi.api.nequiapi.domain.repository.FranquiciaRepository;
import com.nequi.api.nequiapi.domain.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class DynamoDBSucursalRepository implements SucursalRepository {

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        log.info("Guardando Sucursal: {}", sucursal);

        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("sucursalId", AttributeValue.builder().s(sucursal.getSucursalId()).build());
        itemMap.put("nombre", AttributeValue.builder().s(sucursal.getNombre()).build());


        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("Sucursales")
                .item(itemMap)
                .build();

        CompletableFuture<PutItemResponse> future = dynamoDbAsyncClient.putItem(putItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al guardar en DynamoDB: {}", e.getMessage(), e))
                .thenReturn(sucursal);
    }

    @Override
    public Mono<Sucursal> findById(String sucursalId) {
        log.info("Obteniendo Sucursal con franquiciaId: {}", sucursalId);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("sucursalId", AttributeValue.builder().s(sucursalId).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("Sucursales")
                .key(key)
                .build();

        return Mono.fromFuture(dynamoDbAsyncClient.getItem(getItemRequest))
                .doOnError(e -> log.error("Error al obtener item de DynamoDB: {}", e.getMessage(), e))
                .map(GetItemResponse::item)
                .filter(item -> item != null && !item.isEmpty())
                .map(item -> Sucursal.builder()
                        .sucursalId(item.get("sucursalId").s())
                        .nombre(item.get("nombre").s())
                        .build());
    }

    @Override
    public Mono<Void> deleteById(String sucursalId) {
        log.info("Eliminando sucursal con franquiciaId: {}", sucursalId);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("sucursalId", AttributeValue.builder().s(sucursalId).build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName("Sucursales")
                .key(key)
                .build();

        CompletableFuture<DeleteItemResponse> future = dynamoDbAsyncClient.deleteItem(deleteItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al eliminar item de DynamoDB: {}", e.getMessage(), e))
                .then();
    }
}
