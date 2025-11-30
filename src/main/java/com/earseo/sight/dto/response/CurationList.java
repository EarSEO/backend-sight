package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CurationList(
        @Schema(description = "큐레이션 목록")
        List<CurationResponse> curationList
) {
}
