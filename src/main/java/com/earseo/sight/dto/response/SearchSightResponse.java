package com.earseo.sight.dto.response;

import com.earseo.sight.dto.projection.SearchSightItemDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchSightResponse(
        @Schema(description = "관광지 고유 ID", example = "126508")
        String sightId,

        @Schema(description = "관광지 이름", example = "경복궁")
        String title,

        @Schema(description = "관광지 중분류", example = "체험관광지")
        String detailTheme,

        @Schema(description = "주소 요약 (구/동 단위)", example = "서울 종로구")
        String address,

        @Schema(description = "경도 (Longitude)", example = "126.9770")
        Double longitude,

        @Schema(description = "위도 (Latitude)", example = "37.5796")
        Double latitude,

        @Schema(description = "현재 위치로부터의 직선 거리 (미터)", example = "1234.56")
        Double distance
) {
    public static SearchSightResponse toDto(SearchSightItemDto dto){
        return new SearchSightResponse(
                dto.contentId(),
                dto.title(),
                dto.cat2(),
                dto.addr3(),
                dto.mapX(),
                dto.mapY(),
                dto.distance()
        );
    }
}
