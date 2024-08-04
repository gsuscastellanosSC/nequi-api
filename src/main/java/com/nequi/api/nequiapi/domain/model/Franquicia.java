package com.nequi.api.nequiapi.domain.model;

import lombok.Data;

import java.util.List;


@Data
public class Franquicia {
    private String franquiciaId;
    private String nombre;
    private List<Sucursal> sucursales;
}
