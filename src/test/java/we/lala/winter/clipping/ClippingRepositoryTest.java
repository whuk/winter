package we.lala.winter.clipping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void givenClipping_whenSaveClipping_thenSaveClipping() {


    }
}
