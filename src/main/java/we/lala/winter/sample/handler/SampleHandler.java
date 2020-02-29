package we.lala.winter.sample.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
public class SampleHandler {

    public Mono<ServerResponse> getSample(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        modelMap.put("message", "Hello Spring Security");

        return ServerResponse.ok().render("index", modelMap);
    }

    public Mono<ServerResponse> info(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        modelMap.put("message", "Info");
        return ServerResponse.ok().render("info", modelMap);
    }

    public Mono<ServerResponse> dashboard(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        return serverRequest.principal()
                .switchIfEmpty(Mono.error(CredentialException::new))
                .map(Principal::getName)
                .flatMap(username -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .render("dashboard", modelMap.put("message", "Hello " + username)))
                ;
    }

    public Mono<ServerResponse> admin(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        return serverRequest.principal()
                .map(Principal::getName)
                .flatMap(username -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .render("dashboard", modelMap.put("message", "Hello " + username)));
    }
}
