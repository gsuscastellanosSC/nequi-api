package com.nequi.api.nequiapi.domain.repository;

import com.nequi.api.nequiapi.domain.model.FranquiciaSucursal;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FranquiciaSucursalRepository {
    Mono<FranquiciaSucursal> save(FranquiciaSucursal franquiciaSucursal);

    Mono<List<FranquiciaSucursal>> findById(String franquiciaId);

    Mono<Void> deleteById(String franquiciaId);
}
