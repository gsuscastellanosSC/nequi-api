package com.nequi.api.nequiapi.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sucursal {
    private String sucursalId;
    private String nombre;
}
