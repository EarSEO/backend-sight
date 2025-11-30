package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SightMapInfoList(
        @Schema(description = "관광지 목록")
        List<SightInfoResponse> sights
) {
}
