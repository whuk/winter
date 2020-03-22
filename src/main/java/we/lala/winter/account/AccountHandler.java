package we.lala.winter.account;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import we.lala.winter.domain.Account;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AccountHandler {

    private final AccountService accountService;
    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public Mono<ServerResponse> makeAccount(ServerRequest serverRequest) {
        Account savedAccount = accountService.createNew(Account.builder()
                .role(serverRequest.pathVariable("role"))
                .username(serverRequest.pathVariable("username"))
                .password(serverRequest.pathVariable("password"))
                .build());
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(savedAccount), Account.class);
    }
}
