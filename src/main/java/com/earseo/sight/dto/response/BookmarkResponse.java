package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkResponse(
        @Schema(description = "관광지 ID", example = "123423")
        String sightId,

        @Schema(description = "사용자 ID", example = "1")
        Long memberId
) {
}
