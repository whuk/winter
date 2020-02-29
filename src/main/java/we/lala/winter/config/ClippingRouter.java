package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.clipping.handler.ClippingHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClippingRouter {

    @Bean
    public RouterFunction<ServerResponse> routesClipping(ClippingHandler clippingHandler) {
        return route(POST("/clipping").and(accept(MediaType.APPLICATION_JSON)), clippingHandler::postClipping)
                .andRoute(GET("/clipping/{id}").and(accept(MediaType.APPLICATION_JSON)), clippingHandler::getClipping)
                .andRoute(PUT("/clipping/{id}").and(accept(MediaType.APPLICATION_JSON)), clippingHandler::putClipping);
    }
}
