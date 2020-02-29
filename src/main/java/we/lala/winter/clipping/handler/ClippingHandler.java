package we.lala.winter.clipping.handler;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import we.lala.winter.clipping.dto.ClippingDto;
import we.lala.winter.clipping.service.ClippingService;
import we.lala.winter.domain.Clipping;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Component
public class ClippingHandler {

    private final ClippingService clippingService;

    public ClippingHandler(ClippingService clippingService) {
        this.clippingService = clippingService;
    }

    public Mono<ServerResponse> postClipping(ServerRequest serverRequest) {
        Mono<Clipping> clippingMono = serverRequest.bodyToMono(ClippingDto.class)
                .flatMap(clippingDto -> Mono.just(clippingService.createClipping(clippingDto)));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(clippingMono, Clipping.class);
    }

    public Mono<ServerResponse> putClipping(ServerRequest serverRequest) {
        String pathVariable = serverRequest.pathVariable("id");

        if (!NumberUtils.isCreatable(pathVariable)) {
            return badRequest().build();
        }

        return serverRequest.bodyToMono(ClippingDto.class)
                .flatMap(clippingDto -> Mono.just(clippingService.modifyClipping(Long.parseLong(pathVariable), clippingDto)))
                .flatMap(clippingOptional -> {
                    if (!clippingOptional.isPresent()) {
                        return ServerResponse.badRequest().build();
                    }
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(clippingOptional.get()), Clipping.class);
                });
    }

    public Mono<ServerResponse> getClipping(ServerRequest serverRequest) {
        String pathVariable = serverRequest.pathVariable("id");

        if (!NumberUtils.isCreatable(pathVariable)) {
            return badRequest().build();
        }

        Optional<Clipping> optionalClipping = clippingService.selectClippingById(Long.parseLong(pathVariable));

        if (!optionalClipping.isPresent()) {
            return badRequest().build();
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(optionalClipping.get()), Clipping.class);
    }
}
