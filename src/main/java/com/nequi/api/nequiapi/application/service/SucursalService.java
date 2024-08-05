package com.nequi.api.nequiapi.application.service;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursales;
import com.nequi.api.nequiapi.domain.model.Sucursal;
import com.nequi.api.nequiapi.domain.repository.FranquiciaRepository;
import com.nequi.api.nequiapi.domain.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public Mono<Sucursal> addSucursal(Sucursal sucursal) {
        String sucursalId = String.valueOf(UUID.randomUUID());
        sucursal.setSucursalId(sucursalId);
        return sucursalRepository.save(sucursal)
                .doOnSuccess(savedFranquicia -> log.info("sucursal guardada: {}", savedFranquicia))
                .doOnError(e -> log.error("Error al guardar franquicia: {}", e.getMessage(), e));
    }
}
