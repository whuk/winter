package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import we.lala.winter.account.SignUpHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SignUpRouter {

    @Bean
    public RouterFunction<ServerResponse> routesSignUp(SignUpHandler signUpHandler) {
        return route(GET("/signup").and(accept(MediaType.APPLICATION_JSON)), signUpHandler::getSignUp)
                .andRoute(POST("/signup").and(accept(MediaType.APPLICATION_FORM_URLENCODED)), signUpHandler::processSignUp);
    }
}
