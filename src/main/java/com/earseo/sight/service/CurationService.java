package com.earseo.sight.service;

import com.earseo.sight.dto.response.CurationList;
import com.earseo.sight.dto.response.CurationResponse;
import com.earseo.sight.entity.Curation;
import com.earseo.sight.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurationService {

    private static final int CURATION_LIST_SIZE = 4;

    private final CurationRepository curationRepository;

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
}
