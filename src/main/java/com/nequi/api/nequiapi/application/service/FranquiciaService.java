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
public class FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<Franquicia> addFranquicia(Franquicia franquicia) {
        String franquiciaId = String.valueOf(UUID.randomUUID());
        franquicia.setFranquiciaId(franquiciaId);
        log.info("Agregando franquicia con ID: {}", franquiciaId);
        return franquiciaRepository.save(franquicia)
                .doOnSuccess(savedFranquicia -> log.info("Franquicia guardada: {}", savedFranquicia))
                .doOnError(e -> log.error("Error al guardar franquicia: {}", e.getMessage(), e));
    }

    public Mono<Franquicia> getFranquiciaById(String id) {
        log.info("Buscando franquicia con ID: {}", id);
        return franquiciaRepository.findById(id)
                .doOnSuccess(franquicia -> {
                    if (franquicia != null) {
                        log.info("Franquicia encontrada: {}", franquicia);
                    } else {
                        log.warn("Franquicia con ID: {} no encontrada", id);
                    }
                })
                .doOnError(e -> log.error("Error al buscar franquicia con ID: {}", id, e));
    }
}
