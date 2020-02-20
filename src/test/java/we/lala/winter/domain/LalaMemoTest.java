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
        LalaMemo lalaMemo = LalaMemo.builder()
                .id(1L)
                .text("text")
                .check(false)
                .createDt(now)
                .modifiedDt(now)
                .build();
        assertNotNull(lalaMemo);
    }

    @Test
    @DisplayName("메모 생성자 테스트")
    void memoConstructorTest() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        LalaMemo lalaMemo = new LalaMemo(1L, "text", false, now, now);
        assertNotNull(lalaMemo);
    }
}