package com.nequi.api.nequiapi.domain.model;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@Data
public class Producto {
    private String productoId;
    private String nombre;
    private int stock;
}
