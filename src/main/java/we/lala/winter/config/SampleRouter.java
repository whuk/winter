package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.sample.handler.SampleHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SampleRouter {

    @Bean
    public RouterFunction<ServerResponse> routesSample(SampleHandler sampleHandler) {
//        return route().GET("/", sampleHandler::getSample).build();
        return route(GET("/").and(accept(MediaType.APPLICATION_JSON)), sampleHandler::getSample)
                .andRoute(GET("info").and(accept(MediaType.APPLICATION_JSON)), sampleHandler::info)
                .andRoute(GET("/dashboard").and(accept(MediaType.APPLICATION_JSON)), sampleHandler::dashboard)
                .andRoute(GET("/admin").and(accept(MediaType.APPLICATION_JSON)), sampleHandler::admin)
                .andRoute(GET("/user").and(accept(MediaType.APPLICATION_JSON)), sampleHandler::user);
    }
}
