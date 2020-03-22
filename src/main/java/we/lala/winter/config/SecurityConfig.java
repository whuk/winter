package we.lala.winter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import we.lala.winter.account.AccountRepository;
import we.lala.winter.domain.Account;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final AccountRepository accountRepository;

    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/", "/info", "/account/**").permitAll()
                .pathMatchers("/admin").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return (username) -> {
            Account byUsername = accountRepository.findByUsername(username);
            return Mono.just(User.withUsername(byUsername.getUsername())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRole()).build());
        };
    }

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails ryan = User.withUsername("ryan").password("{noop}ryan").roles("USER").build();
//        UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
//        return new MapReactiveUserDetailsService(ryan, admin);
//    }
}
