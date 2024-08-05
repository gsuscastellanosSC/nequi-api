package com.nequi.api.nequiapi.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Producto {
    private String productoId;
    private String nombre;
    private int stock;
}
