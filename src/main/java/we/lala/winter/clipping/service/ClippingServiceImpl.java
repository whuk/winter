package we.lala.winter.clipping.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import we.lala.winter.clipping.repository.ClippingRepository;
import we.lala.winter.domain.Clipping;

import java.util.Optional;

@Service
@Slf4j
public class ClippingServiceImpl implements ClippingService {

    private final ClippingRepository clippingRepository;

    public ClippingServiceImpl(ClippingRepository clippingRepository) {
        this.clippingRepository = clippingRepository;
    }

    @Override
    public Clipping createClipping(Clipping clipping) {
        return clippingRepository.save(clipping);
    }

    @Override
    public Optional<Clipping> selectClippingById(Long id) {
        return clippingRepository.findById(id);
    }
}
