package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.account.AccountHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> routesAccount(AccountHandler accountHandler) {
        return route(GET("/account/{role}/{username}/{password}").and(accept(MediaType.APPLICATION_JSON)), accountHandler::makeAccount);
    }
}
