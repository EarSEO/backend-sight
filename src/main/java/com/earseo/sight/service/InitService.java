package com.earseo.sight.service;

import com.earseo.sight.dto.etl.DocentItemDto;
import com.earseo.sight.dto.etl.SightItemDto;
import com.earseo.sight.entity.*;
import com.earseo.sight.repository.DocentRepository;
import com.earseo.sight.repository.EnSightRepository;
import com.earseo.sight.repository.KoSightRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitService {

    private final KoSightRepository koSightRepository;
    private final EnSightRepository enSightRepository;
    private final DocentRepository docentRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cloudFrontDomain;

    @Transactional
    public void initSight(String lang) {
        RestClient client = RestClient.create();
        String s3Key = String.format("core/master/master_data_%s_.json", lang);
        String cdnUrl = getCloudFrontDomain(s3Key);

        try {
            JsonNode jsonContent = client.get()
                    .uri(cdnUrl)
                    .retrieve()
                    .body(JsonNode.class);

            List<SightItemDto> dtos = objectMapper.convertValue(
                    jsonContent,
                    new TypeReference<>() {
                    }
            );

            if(lang.equals("en")) {
                List<EnSight> sights = dtos.stream()
                        .map(this::convertEnSight)
                        .toList();

                enSightRepository.saveAll(sights);
            } else {
                List<KoSight> sights = dtos.stream()
                        .map(this::convertKoSight)
                        .toList();

                koSightRepository.saveAll(sights);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void initDocent() {
        RestClient client = RestClient.create();
        String s3Key = "core/docent/docent_data.json";
        String cdnUrl = getCloudFrontDomain(s3Key);

        try {
            JsonNode jsonContent = client.get()
                    .uri(cdnUrl)
                    .retrieve()
                    .body(JsonNode.class);

            List<DocentItemDto> dtos = objectMapper.convertValue(
                    jsonContent,
                    new TypeReference<>() {
                    }
            );

            List<Docent> docents = dtos.stream()
                    .map(this::convertDocent)
                    .collect(Collectors.toList());

            docentRepository.saveAll(docents);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KoSight convertKoSight(SightItemDto dto) {
        Point geom = null;
        if (dto.mapX() != null && dto.mapY() != null) {
            geom = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.mapX(), dto.mapY()));
        }

        return KoSight.builder().
                contentId(dto.contentId())
                .theme(Theme.valueOf(dto.cat1Code()))
                .subTheme(SubTheme.valueOf(dto.cat2Code()))
                .title(dto.title())
                .addr1(dto.addr1())
                .addr2(dto.addr2())
                .addr3(dto.addr3())
                .mapX(dto.mapX())
                .mapY(dto.mapY())
                .tel(dto.tel())
                .overview(dto.overview())
                .originImgUrl(dto.originImgUrl())
                .usetime(dto.usetime())
                .restdate(dto.restdate())
                .parking(dto.parking())
                .usefee(dto.usefee())
                .geom(geom)
                .build();
    }

    private EnSight convertEnSight(SightItemDto dto) {
        Point geom = null;
        if (dto.mapX() != null && dto.mapY() != null) {
            geom = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.mapX(), dto.mapY()));
        }

        return EnSight.builder().
                contentId(dto.contentId())
                .theme(Theme.valueOf(dto.cat1Code()))
                .subTheme(SubTheme.valueOf(dto.cat2Code()))
                .title(dto.title())
                .addr1(dto.addr1())
                .addr2(dto.addr2())
                .addr3(dto.addr3())
                .mapX(dto.mapX())
                .mapY(dto.mapY())
                .tel(dto.tel())
                .overview(dto.overview())
                .originImgUrl(dto.originImgUrl())
                .usetime(dto.usetime())
                .restdate(dto.restdate())
                .parking(dto.parking())
                .usefee(dto.usefee())
                .geom(geom)
                .build();
    }

    private Docent convertDocent(DocentItemDto dto) {
        return Docent.builder()
                .contentId(dto.contentId())
                .script(dto.script())
                .docentUrl(dto.docentUrl())
                .build();
    }

    private String getCloudFrontDomain(String s3Key){
        return String.format("%s%s", cloudFrontDomain, s3Key);
    }
}
