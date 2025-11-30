package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkStatusResponse(
        @Schema(description = "현재 북마크 상태", example = "true")
        boolean isLiked,

        @Schema(description = "관광지 ID", example = "1234345")
        String sightId
) {
}
