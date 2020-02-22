package we.lala.winter.clipping.service;

import we.lala.winter.domain.Clipping;

import java.util.Optional;

public interface ClippingService {

    Clipping createClipping(Clipping clipping);

    Optional<Clipping> selectClippingById(Long id);
}
