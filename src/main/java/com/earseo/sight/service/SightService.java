package com.earseo.sight.service;

import ch.hsr.geohash.GeoHash;
import com.earseo.sight.common.exception.BaseException;
import com.earseo.sight.common.exception.SightError;
import com.earseo.sight.dto.projection.SearchSightItemDto;
import com.earseo.sight.dto.projection.SightDetailItemDto;
import com.earseo.sight.dto.projection.SightMapItemDto;
import com.earseo.sight.dto.response.*;
import com.earseo.sight.entity.Curation;
import com.earseo.sight.entity.Docent;
import com.earseo.sight.entity.Theme;
import com.earseo.sight.repository.CurationRepository;
import com.earseo.sight.repository.DocentRepository;
import com.earseo.sight.repository.EnSightRepository;
import com.earseo.sight.repository.KoSightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SightService {
    private static final int GEOHASH_PRECISION = 9;

    private final KoSightRepository koSightRepository;
    private final EnSightRepository enSightRepository;
    private final DocentRepository docentRepository;
    private final CurationRepository curationRepository;

    public SightMapInfoList getMapRectangle(Double minLongitude, Double minLatitude, Double maxLongitude, Double maxLatitude, String lang) {
        if (minLongitude >= maxLongitude || minLatitude >= maxLatitude) {
            throw new BaseException(SightError.INVALID_COORDINATE_RANGE);
        }

        List<SightMapItemDto> sights;
        if (lang.equals("en")) {
            sights = enSightRepository.findByRectangle(minLongitude, minLatitude, maxLongitude, maxLatitude);
        } else {
            sights = koSightRepository.findByRectangle(minLongitude, minLatitude, maxLongitude, maxLatitude);
        }

        List<SightInfoResponse> sightInfos = sights.stream().map(
                item -> new SightInfoResponse(
                        item.contentId(),
                        item.title(),
                        item.mapX(),
                        item.mapY(),
                        Theme.valueOf(item.theme()),
                        getGeoHash(item.mapX(), item.mapY()))
        ).toList();

        return new SightMapInfoList(sightInfos);
    }

    public SightMapInfoList getMapCircle(Double meters, Double longitude, Double latitude, String lang) {

        List<SightMapItemDto> sights;
        if (lang.equals("en")) {
            sights = enSightRepository.findByRadius(meters, longitude, latitude);
        } else {
            sights = koSightRepository.findByRadius(meters, longitude, latitude);
        }

        List<SightInfoResponse> sightInfos = sights.stream().map(
                item -> new SightInfoResponse(
                        item.contentId(),
                        item.title(),
                        item.mapX(),
                        item.mapY(),
                        Theme.valueOf(item.theme()),
                        getGeoHash(item.mapX(), item.mapY()))
        ).toList();

        return new SightMapInfoList(sightInfos);
    }

    public SightDetailInfoResponse getSightDetailInfo(String id, Double longitude, Double latitude, Long memberId, String lang) {

        SightDetailItemDto dto;
        if (lang.equals("en")) {
            dto = enSightRepository.findByContentId(id, longitude, latitude, memberId);
        } else {
            dto = koSightRepository.findByContentId(id, longitude, latitude, memberId);
        }

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

    public SearchSightList searchSight(
            String keyword, Double longitude, Double latitude,
            Double minLongitude, Double minLatitude,
            Double maxLongitude, Double maxLatitude, Integer limit, String lang) {
        if (minLongitude >= maxLongitude || minLatitude >= maxLatitude) {
            throw new BaseException(SightError.INVALID_COORDINATE_RANGE);
        }

        List<SearchSightItemDto> sights;
        if (lang.equals("en")) {
            sights = enSightRepository.findByKeywordAndRectangle(keyword, longitude, latitude, minLongitude, minLatitude, maxLongitude, maxLatitude, limit);
        } else {
            sights = koSightRepository.findByKeywordAndRectangle(keyword, longitude, latitude, minLongitude, minLatitude, maxLongitude, maxLatitude, limit);
        }
        List<SearchSightResponse> sightResponses = sights.stream()
                .map(SearchSightResponse::toDto)
                .toList();

        return new SearchSightList(keyword, sightResponses);
    }

    private static String getGeoHash(double longitude, double latitude) {
        return GeoHash.withCharacterPrecision(latitude, longitude, GEOHASH_PRECISION).toBase32();
    }
}
