package we.lala.winter.clipping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import we.lala.winter.domain.Clipping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ClippingRepositoryTest {

    @Autowired
    private ClippingRepository clippingRepository;

    @BeforeEach
    void beforeEach() {
        assertNotNull(clippingRepository);
    }

    @Test
    @DisplayName("Clipping Repository 기본 저장 테스트")
    void givenClipping_whenSaveClipping_thenSaveClipping() {
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
        Clipping savedClipping = clippingRepository.save(clipping);

        // Then
        assertNotNull(savedClipping);
    }
}
