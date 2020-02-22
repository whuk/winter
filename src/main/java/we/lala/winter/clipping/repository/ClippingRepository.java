package we.lala.winter.clipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import we.lala.winter.domain.Clipping;

public interface ClippingRepository extends JpaRepository<Clipping, Long> {
}
