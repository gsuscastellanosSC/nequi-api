package com.nequi.api.nequiapi.application.usecase;

import com.nequi.api.nequiapi.application.service.FranquiciaService;
import com.nequi.api.nequiapi.domain.model.Franquicia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetIDFranquiciaUseCase {

    private final FranquiciaService franquiciaService;

    public Mono<Franquicia> execute(String id) {
        log.info("Iniciando búsqueda de franquicia con ID: {}", id);
        return franquiciaService.getFranquiciaById(id)
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
