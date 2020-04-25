package we.lala.winter.sample.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
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
                .flatMap(username -> {
                    modelMap.put("message", "Hello " + username);
                    return ServerResponse.ok()
                            .render("dashboard", modelMap);
                });
    }

    public Mono<ServerResponse> admin(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        return serverRequest.principal()
                .switchIfEmpty(Mono.error(CredentialException::new))
                .map(Principal::getName)
                .flatMap(username -> {
                    modelMap.put("message", "Hello " + username);
                    return ServerResponse.ok()
                            .render("admin", modelMap);
                });
    }

    public Mono<ServerResponse> user(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        return serverRequest.principal()
                .switchIfEmpty(Mono.error(CredentialException::new))
                .map(Principal::getName)
                .flatMap(username -> {
                    modelMap.put("message", "Hello " + username);
                    return ServerResponse.ok()
                            .render("user", modelMap);
                });
    }

    public Mono<ServerResponse> forbidden(ServerRequest serverRequest) {
        Map<String, String> modelMap = new HashMap<>();
        return serverRequest.principal()
                .switchIfEmpty(Mono.error(CredentialException::new))
                .map(Principal::getName)
                .flatMap(username -> {
                    modelMap.put("message", username + " can not access this page!! - Forbidden");
                    return ServerResponse.ok()
                            .render("forbidden", modelMap);
                });
    }
}
