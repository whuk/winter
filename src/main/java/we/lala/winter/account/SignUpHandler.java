package we.lala.winter.account;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import we.lala.winter.domain.Account;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class SignUpHandler {

    private final AccountService accountService;

    public SignUpHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public Mono<ServerResponse> getSignUp(ServerRequest serverRequest) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("account", new Account());
        return ok().render("signup", modelMap);
    }

    public Mono<ServerResponse> processSignUp(ServerRequest serverRequest) {
        return serverRequest.formData().map(formData ->
                Mono.just(accountService.createNew(Account.builder().username(String.valueOf(formData.get("username").get(0)))
                        .password(String.valueOf(formData.get("password").get(0)))
                        .role("USER").build())))
                .flatMap(accountMono -> ok().render("redirect:/"));
    }
}
