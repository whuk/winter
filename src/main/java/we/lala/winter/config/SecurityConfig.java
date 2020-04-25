package we.lala.winter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import we.lala.winter.account.AccountRepository;
import we.lala.winter.domain.Account;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private final AccountRepository accountRepository;

    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
//                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/", "/info", "/account/**", "/signup", "/login").permitAll()
                .pathMatchers("/admin").hasRole("ADMIN")
                .pathMatchers("/user")
                .access(new CustomReactiveAuthorizationManager<>("USER", roleHierarchy()))
                .anyExchange().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .httpBasic()
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler("/"))
                .and()
                .exceptionHandling().accessDeniedHandler(new ServerAccessDeniedHandler() {
                    @Override
                    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
                        ServerHttpRequest request = serverWebExchange.getRequest();
                        URI origin = request.getURI();
                        URI target = null;
                        try {
                            target = new URI(
                                    "http",
                                    origin.getUserInfo(),
                                    origin.getHost(),
                                    origin.getPort(),
                                    "/forbidden",
                                    origin.getQuery(),
                                    origin.getFragment()
                            );
                        } catch (URISyntaxException uriSyntaxException) {
                            log.error("### Forbidden address if wrong!!");
                        }
                        ServerHttpResponse response = serverWebExchange.getResponse();
                        response.getHeaders().setLocation(target);
                        response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                        return Mono.empty();
                    }
                })
                .and()
                .build();
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler(String url) {
        RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler
                = new RedirectServerLogoutSuccessHandler();
        redirectServerLogoutSuccessHandler.setLogoutSuccessUrl(URI.create(url));
        return redirectServerLogoutSuccessHandler;
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
