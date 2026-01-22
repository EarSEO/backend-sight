package com.earseo.sight.service.internal;

import com.earseo.sight.dto.internal.SightMetaResponse;
import com.earseo.sight.dto.projection.SightMetaDto;
import com.earseo.sight.repository.KoSightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InternalSightService {

    private final KoSightRepository koSightRepository;

    @Transactional(readOnly = true)
    public List<SightMetaResponse> getSightByIds(List<String> ids) {
        List<SightMetaDto> dtos = koSightRepository.findByContentId(ids);

        Map<String, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            orderMap.put(ids.get(i), i);
        }

        return dtos.stream()
                .sorted(Comparator.comparingInt(dto -> orderMap.get(dto.contentId())))
                .map(SightMetaResponse::toDto)
                .toList();
    }
}
