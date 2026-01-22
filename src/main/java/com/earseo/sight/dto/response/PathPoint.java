package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PathPoint(
        @Schema(description = "경도", example = "127.9768")
        Double longitude,

        @Schema(description = "위도", example = "37.5759")
        Double latitude
) {
}
