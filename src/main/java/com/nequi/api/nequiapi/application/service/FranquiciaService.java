package com.nequi.api.nequiapi.application.service;

import com.nequi.api.nequiapi.domain.model.Franquicia;
import com.nequi.api.nequiapi.domain.repository.FranquiciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FranquiciaService {

    @Autowired
    private FranquiciaRepository franquiciaRepository;
    public Mono<Franquicia> addFranquicia(Franquicia franquicia) {
        String franquiciaId = "FRANQ#" + UUID.randomUUID();
        franquicia.setFranquiciaId(franquiciaId);

        franquicia.getSucursales().forEach(sucursal -> {
            String sucursalId = "SUC#" + UUID.randomUUID();
            sucursal.setSucursalId(sucursalId);
            sucursal.getProductos().forEach(producto -> {
                String productoId = "PROD#" + UUID.randomUUID();
                producto.setProductoId(productoId);
            });
        });
        return franquiciaRepository.save(franquicia);
    }

    public Mono<Franquicia> getFranquiciaById(String id) {
        return franquiciaRepository.findById(id);
    }

    public Mono<Void> deleteFranquicia(String id) {
        return franquiciaRepository.deleteById(id);
    }
}
