package com.earseo.sight.service;

import ch.hsr.geohash.GeoHash;
import com.earseo.sight.common.exception.BaseException;
import com.earseo.sight.common.exception.SightError;
import com.earseo.sight.dto.projection.SightDetailItemDto;
import com.earseo.sight.dto.projection.SightMapItemDto;
import com.earseo.sight.dto.response.*;
import com.earseo.sight.entity.Curation;
import com.earseo.sight.entity.Docent;
import com.earseo.sight.repository.CurationRepository;
import com.earseo.sight.repository.DocentRepository;
import com.earseo.sight.repository.SightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SightService {
    private static final int GEOHASH_PRECISION = 9;

    private final SightRepository sightRepository;
    private final DocentRepository docentRepository;
    private final CurationRepository curationRepository;

    public SightMapInfoList getMapRectangle(Double minLongitude, Double minLatitude, Double maxLongitude, Double maxLatitude) {
        if (minLongitude >= maxLongitude || minLatitude >= maxLatitude) {
            throw new BaseException(SightError.INVALID_COORDINATE_RANGE);
        }

        List<SightMapItemDto> sights = sightRepository.findByRectangle(minLongitude, minLatitude, maxLongitude, maxLatitude);

        List<SightInfoResponse> sightInfos = sights.stream().map(
                item -> new SightInfoResponse(
                        item.contentId(),
                        item.title(),
                        item.mapX(),
                        item.mapY(),
                        getGeoHash(item.mapX(), item.mapY()))
        ).toList();

        return new SightMapInfoList(sightInfos);
    }

    public SightMapInfoList getMapCircle(Double meters, Double longitude, Double latitude) {

        List<SightMapItemDto> sights = sightRepository.findByRadius(meters, longitude, latitude);

        List<SightInfoResponse> sightInfos = sights.stream().map(
                item -> new SightInfoResponse(
                        item.contentId(),
                        item.title(),
                        item.mapX(),
                        item.mapY(),
                        getGeoHash(item.mapX(), item.mapY()))
        ).toList();

        return new SightMapInfoList(sightInfos);
    }

    public SightDetailInfoResponse getSightDetailInfo(String id, Double longitude, Double latitude, Long memberId) {

        SightDetailItemDto dto =  sightRepository.findByContentId(id, longitude, latitude, memberId);

        if (dto == null) {
            throw new BaseException(SightError.SIGHT_NOT_FOUND);
        }


        List<Curation> curations = curationRepository.findAllBySightContentId(id);
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

        return SightDetailInfoResponse.toDto(dto, curationResponses);
    }

    public DocentResponse getDocent(String sightId) {
        Docent docent = docentRepository.findByContentId((sightId));
        if (docent == null) {
            return new DocentResponse(null, null);
        }
        return new DocentResponse(docent.getScript(), docent.getDocentUrl());
    }

    private static String getGeoHash(double longitude, double latitude) {
        return GeoHash.withCharacterPrecision(latitude, longitude, GEOHASH_PRECISION).toBase32();
    }
}
