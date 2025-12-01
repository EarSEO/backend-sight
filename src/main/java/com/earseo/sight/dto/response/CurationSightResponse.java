package com.earseo.sight.dto.response;

import com.earseo.sight.dto.projection.CurationSightItemDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record CurationSightResponse(
        @Schema(description = "관광지 고유 ID", example = "126508")
        String sightId,

        @Schema(description = "관광지 이름", example = "경복궁")
        String title,

        @Schema(description = "관광지 테마 (예: 문화시설, 자연관광지 등)", example = "인문")
        String theme,

        @Schema(description = "현재 위치로부터의 직선 거리 (미터)", example = "1234.56")
        Double distance,

        @Schema(description = "주소 요약 (구/동 단위)", example = "서울 종로구")
        String address
) {
    public static CurationSightResponse toDto(CurationSightItemDto dto) {
        return new CurationSightResponse(
                dto.contentId(),
                dto.title(),
                dto.cat2(),
                dto.distance(),
                dto.addr3()
        );
    }
}
