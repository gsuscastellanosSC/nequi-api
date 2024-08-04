package com.nequi.api.nequiapi.domain.repository;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository {
    Mono<Franquicia> save(Franquicia franquicia);

    Mono<Franquicia> findById(String franquiciaId);

    Mono<Void> deleteById(String franquiciaId);
}
