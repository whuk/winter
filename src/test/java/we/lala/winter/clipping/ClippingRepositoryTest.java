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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Clipping 저장 후 조회 테스트")
    void givenClipping_whenSaveClippingAndSelect_thenReturnClipping() {
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
        List<Clipping> clippingList = clippingRepository.findAll();

        // Then
        System.out.println(clippingList.toString());
        assertNotEquals(0, clippingList.size());
    }

    @Test
    @DisplayName("Clipping 저장 후 삭제 테스트")
    void givenClipping_whenSaveClippingAndDelete_thenDeleteClipping() {
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
        Long savedClippingId = savedClipping.getId();
        System.out.println(savedClipping.toString());
        clippingRepository.delete(savedClipping);

        // Then
        Optional<Clipping> clippingById = clippingRepository.findById(savedClippingId);
        assertFalse(clippingById.isPresent());
    }
}
