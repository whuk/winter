package we.lala.winter.login.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoginHandler {

    public Mono<ServerResponse> getLogin(ServerRequest serverRequest) {
        return ServerResponse.ok().render("login", new ModelMap());
    }

    public Mono<ServerResponse> getLogout(ServerRequest serverRequest) {
        return ServerResponse.ok().render("logout", new ModelMap());
    }
}
