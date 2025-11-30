package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record DocentResponse(
        @Schema(description = "도슨트(오디오 가이드) 스크립트")
        String script,

        @Schema(description = "도슨트(오디오 가이드) URL", example = "https://example.com/docent/126508.mp3")
        String docentUrl
) {
}
