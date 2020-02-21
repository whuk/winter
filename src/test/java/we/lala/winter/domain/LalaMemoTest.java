package we.lala.winter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LalaMemoTest {

    @Test
    @DisplayName("메모 빌더 테스트")
    void memoBuilderTest() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(now);
        Clipping clipping = Clipping.builder()
                .id(1L)
                .textMessage("textMessage")
                .check(false)
                .createDt(now)
                .modifiedDt(now)
                .build();
        assertNotNull(clipping);
    }

    @Test
    @DisplayName("메모 생성자 테스트")
    void memoConstructorTest() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Clipping clipping = new Clipping(1L, "textMessage", "clippedUrl", false, 1, now, now);
        assertNotNull(clipping);
    }
}
