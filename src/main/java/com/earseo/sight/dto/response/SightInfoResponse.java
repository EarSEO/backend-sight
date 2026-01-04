package com.earseo.sight.dto.response;

import com.earseo.sight.entity.Theme;
import io.swagger.v3.oas.annotations.media.Schema;

public record SightInfoResponse(
        @Schema(description = "관광지 고유 ID", example = "126508")
        String id,

        @Schema(description = "관광지 이름", example = "경복궁")
        String title,

        @Schema(description = "경도 (Longitude, X좌표)", example = "126.9770")
        Double longitude,

        @Schema(description = "위도 (Latitude, Y좌표)", example = "37.5796")
        Double latitude,

        @Schema(description = "관광지 테마(코드)", example = "CU")
        Theme theme,

        @Schema(description = "이야기 스팟 조회용 GeoHash", example = "wydm6dqkm")
        String geoHash
) {
}
