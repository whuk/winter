package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.SampleHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SampleRouter {

    @Bean
    public RouterFunction<ServerResponse> routesSample(SampleHandler sampleHandler) {
        return route().GET("/", sampleHandler::getSample).build();
    }
}
