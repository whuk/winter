package we.lala.winter.clipping.service;

import we.lala.winter.clipping.dto.ClippingDto;
import we.lala.winter.domain.Clipping;

import java.util.Optional;

public interface ClippingService {

    Clipping createClipping(ClippingDto clipping);

    Optional<Clipping> selectClippingById(Long id);

    Optional<Clipping> modifyClipping(Long savedId, ClippingDto clippingDto);

    void deleteClippingById(Long id);
}
