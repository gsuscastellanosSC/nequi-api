package com.nequi.api.nequiapi.infrastructure.persistence;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.repository.FranquiciaRepository;
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
public class DynamoDBFranquiciaRepository implements FranquiciaRepository {

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        log.info("Guardando franquicia: {}", franquicia);

        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("franquiciaId", AttributeValue.builder().s(franquicia.getFranquiciaId()).build());
        itemMap.put("entityId", AttributeValue.builder().s("FRANQ#" + franquicia.getFranquiciaId()).build());
        itemMap.put("nombre", AttributeValue.builder().s(franquicia.getNombre()).build());

        franquicia.getSucursales().forEach(sucursal -> {
            String sucursalId = sucursal.getSucursalId();
            itemMap.put("entityId", AttributeValue.builder().s("FRANQ#" + franquicia.getFranquiciaId() + "#SUC#" + sucursalId).build());
            itemMap.put("sucursalId", AttributeValue.builder().s(sucursalId).build());
            itemMap.put("sucursalNombre", AttributeValue.builder().s(sucursal.getNombre()).build());

            sucursal.getProductos().forEach(producto -> {
                String productoId = producto.getProductoId();
                itemMap.put("entityId", AttributeValue.builder().s("FRANQ#" + franquicia.getFranquiciaId() + "#SUC#" + sucursalId + "#PROD#" + productoId).build());
                itemMap.put("productoId", AttributeValue.builder().s(productoId).build());
                itemMap.put("productoNombre", AttributeValue.builder().s(producto.getNombre()).build());
                itemMap.put("stock", AttributeValue.builder().n(String.valueOf(producto.getStock())).build());
            });
        });

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("Franquicias")
                .item(itemMap)
                .build();

        CompletableFuture<PutItemResponse> future = dynamoDbAsyncClient.putItem(putItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al guardar en DynamoDB: {}", e.getMessage(), e))
                .thenReturn(franquicia);
    }

    @Override
    public Mono<Franquicia> findById(String franquiciaId) {
        log.info("Obteniendo franquicia con franquiciaId: {}", franquiciaId);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("franquiciaId", AttributeValue.builder().s(franquiciaId).build());
        key.put("entityId", AttributeValue.builder().s("FRANQ#" + franquiciaId).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("Franquicias")
                .key(key)
                .build();

        CompletableFuture<GetItemResponse> future = dynamoDbAsyncClient.getItem(getItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al obtener item de DynamoDB: {}", e.getMessage(), e))
                .map(getItemResponse -> {
                    Map<String, AttributeValue> item = getItemResponse.item();
                    if (item == null || item.isEmpty()) {
                        return null;
                    }
                    Franquicia franquicia = new Franquicia();
                    franquicia.setFranquiciaId(item.get("franquiciaId").s());
                    franquicia.setNombre(item.get("nombre").s());
                    // Obtener y establecer sucursales y productos anidados según sea necesario
                    return franquicia;
                });
    }

    @Override
    public Mono<Void> deleteById(String franquiciaId) {
        log.info("Eliminando franquicia con franquiciaId: {}", franquiciaId);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("franquiciaId", AttributeValue.builder().s(franquiciaId).build());
        key.put("entityId", AttributeValue.builder().s("FRANQ#" + franquiciaId).build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName("Franquicias")
                .key(key)
                .build();

        CompletableFuture<DeleteItemResponse> future = dynamoDbAsyncClient.deleteItem(deleteItemRequest);
        return Mono.fromFuture(future)
                .doOnError(e -> log.error("Error al eliminar item de DynamoDB: {}", e.getMessage(), e))
                .then();
    }
}
