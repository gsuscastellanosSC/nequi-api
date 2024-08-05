package com.nequi.api.nequiapi.infrastructure.web.controller;

import com.nequi.api.nequiapi.application.service.SucursalService;
import com.nequi.api.nequiapi.application.usecase.AddSucursalFranquiciaUseCase;
import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursales;
import com.nequi.api.nequiapi.domain.model.Sucursal;
import com.nequi.api.nequiapi.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final AddSucursalFranquiciaUseCase addSucursalFranquiciaUseCase;

    @PostMapping("/addSucursalFranquicia/{franquiciaId}")
    public Mono<ResponseEntity<FranquiciaSucursales>> addSucursalFranquicia(@RequestBody Sucursal sucursal, @PathVariable String franquiciaId) {
        log.info("franquiciaId::",franquiciaId);
        return addSucursalFranquiciaUseCase.execute(sucursal, franquiciaId)
                .map(franquicia -> ResponseEntity.ok(franquicia))
                .switchIfEmpty(Mono.error(new CustomException("Error al agregar sucursal")))
                .doOnError(e -> log.error("Error al agregar sucursal: {}", e.getMessage(), e));
    }
}
