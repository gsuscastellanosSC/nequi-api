package com.nequi.api.nequiapi.domain.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FranquiciaSucursales {
    private String franquiciaId;
    private String nombre;
    private Sucursal sucursale;
}
