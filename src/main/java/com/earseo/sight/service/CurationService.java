package com.earseo.sight.service;

import com.earseo.sight.common.exception.BaseException;
import com.earseo.sight.common.exception.SightError;
import com.earseo.sight.dto.projection.CurationSightItemDto;
import com.earseo.sight.dto.response.CurationList;
import com.earseo.sight.dto.response.CurationResponse;
import com.earseo.sight.dto.response.CurationSightList;
import com.earseo.sight.dto.response.CurationSightResponse;
import com.earseo.sight.entity.Curation;
import com.earseo.sight.repository.CurationRepository;
import com.earseo.sight.repository.SightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurationService {

    private static final int CURATION_LIST_SIZE = 4;

    private final CurationRepository curationRepository;
    private final SightRepository sightRepository;

    @Transactional(readOnly = true)
    public CurationList getCurationList() {

        List<Curation> curations = curationRepository.findRandomCurations(CURATION_LIST_SIZE);
        List<CurationResponse> curationResponses = curations.stream()
                .map(
                        item -> new CurationResponse(
                                item.getId(),
                                item.getTitle(),
                                item.getDescription(),
                                item.getCurationImgUrl()
                        )
                )
                .toList();

        return new CurationList(curationResponses);
    }

    @Transactional(readOnly = true)
    public CurationSightList getCurationSight(Long curationId, Double longitude, Double latitude) {

        Curation curation = curationRepository.findById(curationId).orElseThrow(
                () -> new BaseException(SightError.CURATION_NOT_FOUND)
        );

        List<CurationSightItemDto> curationSights = sightRepository.findByCurationId(curationId, longitude, latitude);
        List<CurationSightResponse> curationSightResponses = curationSights.stream()
                .map(CurationSightResponse::toDto)
                .toList();

        return new CurationSightList(
                curation.getTitle(),
                curation.getDescription(),
                curationSightResponses
        );
    }
}
