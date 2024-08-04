package com.nequi.api.nequiapi.domain.model;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;
import java.util.UUID;

@Data
public class Sucursal {
    private String sucursalId;
    private String nombre;
    private List<Producto> productos;
}
