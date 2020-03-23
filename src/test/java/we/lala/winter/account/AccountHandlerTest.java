package we.lala.winter.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithAnonymousUser
    @DisplayName("index 페이지로 접근하면 200 ok 가 리턴된다.")
    void index_anonymous() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
//    @WithMockUser(username = "ryan", roles = "USER")
    @WithRyanUser
    @DisplayName("index 페이지로 User 가 접근하면 200 ok 가 리턴된다.")
    void index_user() {
        webTestClient
//                .mutateWith(mockUser("ryan").roles("USER"))
                .get().uri("/")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
//    @WithMockUser(username = "ryan", roles = "USER")
    @WithRyanUser
    @DisplayName("admin 페이지로 User 가 접근하면 403 forbidden  리턴된다.")
    void admin_user() {
        webTestClient
                .get().uri("/admin")
                .exchange()
                .expectStatus()
                .isForbidden();
    }
}