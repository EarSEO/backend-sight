package com.earseo.sight.service;

import com.earseo.sight.dto.etl.DocentItemDto;
import com.earseo.sight.dto.etl.SightItemDto;
import com.earseo.sight.entity.Docent;
import com.earseo.sight.entity.Sight;
import com.earseo.sight.repository.DocentRepository;
import com.earseo.sight.repository.SightRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitService {

    private final SightRepository sightRepository;
    private final DocentRepository docentRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final ObjectMapper objectMapper;

    @Transactional
    public void initSight() {
        RestClient client = RestClient.create();
        String cdnUrl = "https://cdn.earseo.click/core/master/master_data_.json";

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

            List<Sight> sights = dtos.stream()
                    .map(this::convertSight)
                    .collect(Collectors.toList());

            sightRepository.saveAll(sights);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void initDocent() {
        RestClient client = RestClient.create();
        String cdnUrl = "https://cdn.earseo.click/core/docent/docent_data.json";

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

    private Sight convertSight(SightItemDto dto) {
        Point geom = null;
        if (dto.mapX() != null && dto.mapY() != null) {
            geom = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.mapX(), dto.mapY()));
        }

        return Sight.builder().
                contentId(dto.contentId())
                .contentTypeId(dto.contentTypeId())
                .cat1(dto.cat1())
                .cat2(dto.cat2())
                .cat3(dto.cat3())
                .ocat1(dto.ocat1())
                .ocat2(dto.ocat2())
                .ocat3(dto.ocat3())
                .outl(dto.outl())
                .title(dto.title())
                .addr1(dto.addr1())
                .addr2(dto.addr2())
                .addr3(dto.addr3())
                .mapX(dto.mapX())
                .mapY(dto.mapY())
                .modifiedtime(dto.modifiedtime())
                .tel(dto.tel())
                .mLevel(dto.mLevel())
                .overview(dto.overview())
                .originImgUrl(dto.originImgUrl())
                .smallImgUrl(dto.smallImgUrl())
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
}
