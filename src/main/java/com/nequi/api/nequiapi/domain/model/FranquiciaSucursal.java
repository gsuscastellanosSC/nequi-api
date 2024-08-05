package com.nequi.api.nequiapi.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FranquiciaSucursal {
    private String id;
    private String franquiciaId;
    private String sucursalId;
}
