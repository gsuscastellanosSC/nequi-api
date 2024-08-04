package com.nequi.api.nequiapi.infrastructure.web.controller;

import com.nequi.api.nequiapi.application.service.FranquiciaService;
import com.nequi.api.nequiapi.domain.model.Franquicia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/franquicias")
public class FranquiciaController {

    @Autowired
    private FranquiciaService franquiciaService;

    @PostMapping
    public Mono<ResponseEntity<Franquicia>> addFranquicia(@RequestBody Franquicia franquicia) {
        return franquiciaService.addFranquicia(franquicia)
                .map(savedFranquicia -> ResponseEntity.status(HttpStatus.CREATED).body(savedFranquicia))
                .doOnError(e -> log.error("Error al agregar franquicia: {}", e.getMessage(), e));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Franquicia>> getFranquiciaById(@PathVariable String id) {
        return franquiciaService.getFranquiciaById(id)
                .map(franquicia -> ResponseEntity.ok(franquicia))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(e -> log.error("Error al obtener franquicia: {}", e.getMessage(), e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFranquicia(@PathVariable String id) {
        return franquiciaService.deleteFranquicia(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(e -> log.error("Error al eliminar franquicia: {}", e.getMessage(), e));
    }
}
