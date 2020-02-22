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

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @DisplayName("ClippingService 를 이용한 기본 저장 테스트")
    void givenClipping_whenSaveClippingWithClippingService_thenSaveClipping() {
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
        Clipping savedClipping = clippingService.saveClipping(clipping);
        System.out.println(savedClipping.toString());

        // Then
        System.out.println(savedClipping.toString());

    }
}
