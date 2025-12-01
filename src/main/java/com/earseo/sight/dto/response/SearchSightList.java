package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SearchSightList(
        @Schema(description = "사용자 검색 키워드", example = "경복궁")
        String keyword,

        @Schema(description = "검색 결과")
        List<SearchSightResponse> sights
) {
}
