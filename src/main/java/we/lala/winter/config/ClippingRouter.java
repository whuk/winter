package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.clipping.handler.ClippingHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClippingRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(ClippingHandler clippingHandler) {
        return route(POST("/clipping").and(accept(MediaType.APPLICATION_JSON)), clippingHandler::postClipping);
    }
}
