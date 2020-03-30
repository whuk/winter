package we.lala.winter.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .pathMatchers("/user")
                .access(new CustomReactiveAuthorizationManager<>("USER", roleHierarchy()))
                .anyExchange().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    private RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails ryan = User.withUsername("ryan").password("{noop}ryan").roles("USER").build();
//        UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
//        return new MapReactiveUserDetailsService(ryan, admin);
//    }
}
