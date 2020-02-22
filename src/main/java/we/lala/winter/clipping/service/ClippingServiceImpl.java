package we.lala.winter.clipping.service;

import org.springframework.stereotype.Service;
import we.lala.winter.clipping.repository.ClippingRepository;
import we.lala.winter.domain.Clipping;

@Service
public class ClippingServiceImpl implements ClippingService {

    private final ClippingRepository clippingRepository;

    public ClippingServiceImpl(ClippingRepository clippingRepository) {
        this.clippingRepository = clippingRepository;
    }

    @Override
    public Clipping saveClipping(Clipping clipping) {
        return clippingRepository.save(clipping);
    }
}
