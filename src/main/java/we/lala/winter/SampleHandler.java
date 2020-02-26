package we.lala.winter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SampleHandler {

    public Mono<ServerResponse> getSample(ServerRequest serverRequest) {
        return ServerResponse.ok().render("index");
    }
}
