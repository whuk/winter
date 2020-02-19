package we.lala.winter.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemoTest {

    @Test
    void memoBuilderTest() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(now);
        Memo memo = Memo.builder()
                .id(1L)
                .text("text")
                .check(false)
                .createDt(now)
                .modifiedDt(now)
                .build();
        assertNotNull(memo);
    }
}
