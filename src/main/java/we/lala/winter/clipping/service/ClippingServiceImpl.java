package we.lala.winter.clipping.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import we.lala.winter.clipping.dto.ClippingDto;
import we.lala.winter.clipping.repository.ClippingRepository;
import we.lala.winter.domain.Clipping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ClippingServiceImpl implements ClippingService {

    private final ClippingRepository clippingRepository;

    private final ModelMapper modelMapper;

    public ClippingServiceImpl(ClippingRepository clippingRepository, ModelMapper modelMapper) {
        this.clippingRepository = clippingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Clipping createClipping(ClippingDto clippingDto) {

        Clipping clipping = modelMapper.map(clippingDto, Clipping.class);
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        clipping.setCreateDt(now);
        clipping.setModifiedDt(now);
        return clippingRepository.save(clipping);
    }

    @Override
    public Optional<Clipping> selectClippingById(Long id) {
        return clippingRepository.findById(id);
    }

    @Override
    public Optional<Clipping> modifyClipping(Long savedId, ClippingDto clippingDto) {
        Optional<Clipping> optionalClipping = this.selectClippingById(savedId);
        if (!optionalClipping.isPresent()) {
            log.warn("Not exists clipping -> Id is {}", savedId);
            return Optional.empty();
        }
        Clipping getClipping = optionalClipping.get();
        modelMapper.map(clippingDto, getClipping);
        return Optional.of(clippingRepository.save(getClipping));
    }

    @Override
    public void deleteClippingById(Long id) {
        clippingRepository.deleteById(id);
        log.info("{}'s clipping is deleted!", id);
    }
}
