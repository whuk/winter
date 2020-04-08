package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.login.handler.LoginHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoginRouter {

    @Bean
    public RouterFunction<ServerResponse> routesLogin(LoginHandler loginHandler) {
        return route(GET("/login").and(accept(MediaType.APPLICATION_JSON)), loginHandler::getLogin)
                .andRoute(GET("/logout").and(accept(MediaType.APPLICATION_JSON)), loginHandler::getLogout);
    }
}
