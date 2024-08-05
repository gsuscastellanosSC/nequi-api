package com.nequi.api.nequiapi.domain.repository;

import com.nequi.api.nequiapi.domain.model.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalRepository {
    Mono<Sucursal> save(Sucursal sucursal);

    Mono<Sucursal> findById(String sucursalId);

    Mono<Void> deleteById(String sucursalId);
}
