package we.lala.winter.clipping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import we.lala.winter.clipping.dto.ClippingDto;
import we.lala.winter.domain.Clipping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        ClippingDto clipping = ClippingDto.builder()
                .textMessage("textMessage")
                .checked(true)
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
        ClippingDto clipping = ClippingDto.builder()
                .textMessage("textMessage")
                .checked(true)
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

    @Test
    @DisplayName("ClippingService 를 이용한 저장 후 수정 테스트")
    void givenClipping_whenCreateClippingAndModifyWithClippingService_thenReturnClipping() {
        // Given
        ClippingDto clipping = ClippingDto.builder()
                .textMessage("textMessage")
                .checked(true)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        // When
        Clipping savedClipping = clippingService.createClipping(clipping);
        System.out.println(savedClipping.toString());
        Long savedId = savedClipping.getId();

        ClippingDto modifyClipping = ClippingDto.builder()
                .textMessage("modify textMessage")
                .checked(false)
                .modifiedDt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .clippedUrl("https://daum.net")
                .numbering(2)
                .build();
        Optional<Clipping> modifiedOptionalClipping = clippingService.modifyClipping(savedId, modifyClipping);

        // Then
        assertTrue(modifiedOptionalClipping.isPresent());
        Clipping modifiedClipping = modifiedOptionalClipping.get();
        assertEquals("modify textMessage", modifiedClipping.getTextMessage());
        assertFalse(modifiedClipping.getChecked());
        assertEquals("https://daum.net", modifiedClipping.getClippedUrl());
        assertEquals(2, modifiedClipping.getNumbering());
    }

    @Test
    @DisplayName("ClippingService 를 이용한 저장 후 올바르지 않은 수정 테스트")
    void givenWrongId_whenCreateClippingAndModifyWithClippingService_thenReturnEmptyOptional() {
        // Given
        // wrong Id
        Long wrongId = -1L;

        ClippingDto modifyClipping = ClippingDto.builder()
                .textMessage("modify textMessage")
                .checked(false)
                .modifiedDt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .clippedUrl("https://daum.net")
                .numbering(2)
                .build();
        Optional<Clipping> modifiedOptionalClipping = clippingService.modifyClipping(wrongId, modifyClipping);

        // Then
        assertFalse(modifiedOptionalClipping.isPresent());
    }

    @Test
    @DisplayName("ClippingService 를 이용한 저장 후 삭제 테스트")
    void givenClipping_whenCreateClippingAndDeleteWithClippingService_thenReturnDeleteClipping() {
        // Given
        ClippingDto clipping = ClippingDto.builder()
                .textMessage("textMessage")
                .checked(true)
                .clippedUrl("http://naver.com")
                .numbering(1)
                .build();

        // When
        Clipping savedClipping = clippingService.createClipping(clipping);
        System.out.println(savedClipping.toString());
        Long savedId = savedClipping.getId();

        clippingService.deleteClippingById(savedId);

        // Then
        Optional<Clipping> optionalClipping = clippingService.selectClippingById(savedId);
        assertFalse(optionalClipping.isPresent());
    }

}
