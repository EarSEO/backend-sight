package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurationResponse(
        @Schema(description = "큐레이션 ID", example = "1")
        Long curationId,

        @Schema(description = "큐레이션 제목",  example = "")
        String curationTitle,

        @Schema(description = "큐레이션 세부 설명", example = "")
        String description,

        @Schema(description = "큐레이션 이미지 URL", example = "")
        String curationImgUrl
) {
}
