package com.nequi.api.nequiapi.application.usecase;

import com.nequi.api.nequiapi.application.service.FranquiciaService;
import com.nequi.api.nequiapi.application.service.FranquiciaSucursalService;
import com.nequi.api.nequiapi.application.service.SucursalService;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursal;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursales;
import com.nequi.api.nequiapi.domain.model.Sucursal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddSucursalFranquiciaUseCase {

    private final SucursalService sucursalService;
    private final FranquiciaService franquiciaService;
    private final FranquiciaSucursalService franquiciaSucursalService;

    public Mono<FranquiciaSucursales> execute(Sucursal sucursal, String franquiciaId) {
        return franquiciaService.getFranquiciaById(franquiciaId)
                .flatMap(franq -> sucursalService.addSucursal(sucursal)
                        .flatMap(savedSucursal -> {
                            FranquiciaSucursal franquiciaSucursal = FranquiciaSucursal.builder()
                                    .franquiciaId(franquiciaId)
                                    .sucursalId(savedSucursal.getSucursalId())
                                    .build();
                            return franquiciaSucursalService.addFranquiciaSucursal(franquiciaSucursal)
                                    .thenReturn(FranquiciaSucursales.builder()
                                            .franquiciaId(franq.getFranquiciaId())
                                            .nombre(franq.getNombre())
                                            .sucursale(savedSucursal)
                                            .build());
                        }))
                .doOnError(e -> log.error("Error al agregar sucursal a la franquicia: {}", e.getMessage()));
    }
}
