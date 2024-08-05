package com.nequi.api.nequiapi.application.service;


import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursal;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursales;
import com.nequi.api.nequiapi.infrastructure.persistence.DynamoDBFranquiciaSucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FranquiciaSucursalService {

    private final DynamoDBFranquiciaSucursalRepository dynamoDBFranquiciaSucursalRepository;

    public Mono<FranquiciaSucursal> addFranquiciaSucursal(FranquiciaSucursal franquiciaSucursal) {
        String id = String.valueOf(UUID.randomUUID());
        franquiciaSucursal.setId(id);
        log.info("Agregando franquiciaSucursal con ID: {}", id);
        return dynamoDBFranquiciaSucursalRepository.save(franquiciaSucursal)
                .doOnSuccess(savedFranquiciaSucursal -> log.info("FranquiciaSucursal guardada: {}", savedFranquiciaSucursal))
                .doOnError(e -> log.error("Error al guardar franquicia: {}", e.getMessage(), e));
    }

}
