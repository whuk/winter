package we.lala.winter.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import we.lala.winter.WinterApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = WinterApplication.class)
class SignUpHandlerTest {

    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient webTestClient;

    @BeforeEach
    void beforeEach() {
        webTestClient = WebTestClient
                .bindToApplicationContext(this.applicationContext)
                .apply(springSecurity())
                .configureClient()
                .build();
    }

    @Test
    @DisplayName("회원가입 폼을 호출한다.")
    void signUpForm() {
        String responseBody = webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        // hidden csrf token check
        assertTrue(responseBody.contains("_csrf"));
    }

    @Test
    @DisplayName("회원가입을 처리한다.")
    void processSignUp() {
        webTestClient
                .mutateWith(csrf())
                .post().uri("/signup")
                .body(BodyInserters.fromFormData("username", "ryan").with("password", "123"))
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }

}