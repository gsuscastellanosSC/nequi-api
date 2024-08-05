package com.nequi.api.nequiapi.infrastructure.web.controller;

import com.nequi.api.nequiapi.application.usecase.AddFranquiciaUseCase;
import com.nequi.api.nequiapi.application.usecase.GetIDFranquiciaUseCase;
import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.model.FranquiciaSucursales;
import com.nequi.api.nequiapi.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/franquicias")
public class FranquiciaController {

    private final AddFranquiciaUseCase addFranquiciaUseCase;
    private final GetIDFranquiciaUseCase getIDFranquiciaUseCase;

    @PostMapping("/addFranquicia")
    public Mono<ResponseEntity<Franquicia>> addFranquicia(@RequestBody Franquicia franquicia) {
        return addFranquiciaUseCase.execute(franquicia)
                .map(savedFranquicia -> ResponseEntity.status(HttpStatus.CREATED).body(savedFranquicia))
                .doOnError(e -> {
                    log.error("Error al agregar franquicia: {}", e.getMessage(), e);
                    throw new CustomException("Error al agregar franquicia");
                });
    }

    @GetMapping("getFranquiciaById/{id}")
    public Mono<ResponseEntity<Franquicia>> getFranquiciaById(@PathVariable String id) {
        return getIDFranquiciaUseCase.execute(id)
                .map(franquicia -> ResponseEntity.ok(franquicia))
                .switchIfEmpty(Mono.error(new CustomException("Franquicia no encontrada")))
                .doOnError(e -> log.error("Error al buscar franquicia: {}", e.getMessage(), e));
    }
}
