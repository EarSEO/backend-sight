package com.earseo.sight.dto.response;

import com.earseo.sight.dto.projection.CurationSightItemDto;
import com.earseo.sight.entity.SubTheme;
import io.swagger.v3.oas.annotations.media.Schema;

public record CurationSightResponse(
        @Schema(description = "관광지 고유 ID", example = "126508")
        String sightId,

        @Schema(description = "관광지 이름", example = "경복궁")
        String title,

        @Schema(description = "관광지 하위 테마 (코드)", example = "CU01")
        SubTheme subTheme,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
        String imgUrl,

        @Schema(description = "현재 위치로부터의 직선 거리 (미터)", example = "1234.56")
        Double distance,

        @Schema(description = "주소 요약 (구/동 단위)", example = "서울 종로구")
        String address
) {
    public static CurationSightResponse toDto(CurationSightItemDto dto) {
        return new CurationSightResponse(
                dto.contentId(),
                dto.title(),
                SubTheme.valueOf(dto.subTheme()),
                dto.originImgUrl(),
                dto.distance(),
                dto.addr3()
        );
    }
}
