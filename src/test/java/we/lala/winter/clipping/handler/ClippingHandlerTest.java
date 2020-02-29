package we.lala.winter.clipping.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import we.lala.winter.clipping.dto.ClippingDto;
import we.lala.winter.clipping.repository.ClippingRepository;
import we.lala.winter.domain.Clipping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClippingHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ClippingRepository clippingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void beforeEach() {
        clippingRepository.deleteAll();
    }

    @Test
    @DisplayName("Clipping webTestClient 를 이용한 post test")
    void givenClipping_whenPostWebTestClient_thenReturnOk() {
        // Given
        ClippingDto clipping = ClippingDto.builder()
                .textMessage("textMessage")
                .checked(true)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        // When
        webTestClient.post().uri("/clipping")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(clipping), ClippingDto.class)
                .exchange()
                // Then
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("textMessage").isNotEmpty()
                .jsonPath("textMessage").isEqualTo("textMessage");
    }

    @Test
    @DisplayName("Clipping webTestClient 를 이용한 get test")
    void givenClipping_whenGetWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId();

        // When
        webTestClient.get().uri("/clipping/" + savedId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("textMessage").isNotEmpty()
                .jsonPath("textMessage").isEqualTo("textMessage");
    }

    @Test
    @DisplayName("유효하지 않은 id 를 이용한 Clipping webTestClient 를 이용한 get test")
    void givenIncorrectClippingId_whenGetWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId() * -1;

        // When
        webTestClient.get().uri("/clipping/" + savedId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isBadRequest()
                .expectHeader();
    }

    @Test
    @DisplayName("Clipping webTestClient 를 이용한 put test")
    void givenClipping_whenPutWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId();
        ClippingDto modifiedClipping = modelMapper.map(savedClipping, ClippingDto.class);
        modifiedClipping.setTextMessage("modified text message");
        modifiedClipping.setClippedUrl("https://daum.net");

        // When
        webTestClient.put().uri("/clipping/" + savedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(modifiedClipping), ClippingDto.class)
                .exchange()
                // Then
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("textMessage").isNotEmpty()
                .jsonPath("textMessage").isEqualTo("modified text message")
                .jsonPath("clippedUrl").isNotEmpty()
                .jsonPath("clippedUrl").isEqualTo("https://daum.net");
    }

    @Test
    @DisplayName("유효하지 않은 id 를 이용한 Clipping webTestClient 를 이용한 put test")
    void givenIncorrectClippingId_whenPutWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId() * -1;
        ClippingDto modifiedClipping = modelMapper.map(savedClipping, ClippingDto.class);
        modifiedClipping.setTextMessage("modified text message");
        modifiedClipping.setClippedUrl("https://daum.net");

        // When
        webTestClient.put().uri("/clipping/" + savedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(modifiedClipping), ClippingDto.class)
                .exchange()
                // Then
                .expectStatus()
                .isBadRequest();
    }


    @Test
    @DisplayName("Clipping webTestClient 를 이용한 삭제 test")
    void givenClipping_whenDeleteWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId();

        // When
        webTestClient.delete().uri("/clipping/" + savedId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("유효하지 않은 id 를 이용한 Clipping webTestClient 를 이용한 삭제 test")
    void givenIncorrectClipping_whenDeleteWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        Long savedId = savedClipping.getId() * -1;

        // When
        webTestClient.delete().uri("/clipping/" + savedId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("유효하지 않은 문자 id 를 이용한 Clipping webTestClient 를 이용한 삭제 test")
    void givenIncorrectStringIdClipping_whenDeleteWebTestClient_thenReturnOk() {
        // Given
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = Clipping.builder()
                .textMessage("textMessage")
                .checked(true)
                .createDt(now)
                .modifiedDt(now)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        Clipping savedClipping = clippingRepository.save(clipping);
        String savedId = "abc";

        // When
        webTestClient.delete().uri("/clipping/" + savedId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isBadRequest();
    }
}
