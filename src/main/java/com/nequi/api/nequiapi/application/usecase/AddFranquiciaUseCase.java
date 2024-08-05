package com.nequi.api.nequiapi.application.usecase;

import com.nequi.api.nequiapi.application.service.FranquiciaService;
import com.nequi.api.nequiapi.domain.model.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddFranquiciaUseCase {

    private final FranquiciaService franquiciaService;

    public Mono<Franquicia> execute(Franquicia franquicia) {
        return franquiciaService.addFranquicia(franquicia);
    }
}
