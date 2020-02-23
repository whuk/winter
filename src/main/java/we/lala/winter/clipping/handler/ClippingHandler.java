package we.lala.winter.clipping.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import we.lala.winter.clipping.service.ClippingService;
import we.lala.winter.domain.Clipping;

@Component
public class ClippingHandler {

    private final ClippingService clippingService;

    public ClippingHandler(ClippingService clippingService) {
        this.clippingService = clippingService;
    }

    public Mono<ServerResponse> postClipping(ServerRequest serverRequest) {
        Mono<Clipping> clippingMono = serverRequest.bodyToMono(Clipping.class)
                .flatMap(clipping -> Mono.just(clippingService.createClipping(clipping)));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(clippingMono, Clipping.class);

    }
}
