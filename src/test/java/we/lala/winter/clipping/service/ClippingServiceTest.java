package we.lala.winter.clipping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import we.lala.winter.domain.Clipping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ClippingServiceTest {

    @Autowired
    private ClippingService clippingService;

    @BeforeEach
    void beforeEach() {
        assertNotNull(clippingService);
    }

    @Test
    @DisplayName("ClippingService 를 이용한 기본 생성 테스트")
    void givenClipping_whenCreateClippingWithClippingService_thenSaveClipping() {
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

        // When
        Clipping savedClipping = clippingService.createClipping(clipping);
        System.out.println(savedClipping.toString());

        // Then
        assertNotNull(savedClipping);
    }

    @Test
    @DisplayName("ClippingService 를 이용한 저장 후 조회 테스트")
    void givenClipping_whenCreateClippingAndSelectWithClippingService_thenReturnClipping() {
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

        // When
        Clipping savedClipping = clippingService.createClipping(clipping);
        System.out.println(savedClipping.toString());
        Long savedId = savedClipping.getId();
        Optional<Clipping> selectClippingById = clippingService.selectClippingById(savedId);

        // Then
        assertTrue(selectClippingById.isPresent());
    }
}
