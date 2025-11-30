package com.earseo.sight.service.internal;

import com.earseo.sight.dto.internal.SightMetaResponse;
import com.earseo.sight.dto.projection.SightMetaDto;
import com.earseo.sight.repository.SightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalSightService {

    private final SightRepository sightRepository;

    @Transactional(readOnly = true)
    public List<SightMetaResponse> getSightByIds(List<String> ids) {
        List<SightMetaDto> dtos = sightRepository.findByContentId(ids);

        return dtos.stream()
                .map(
                        SightMetaResponse::toDto
                )
                .toList();
    }
}
