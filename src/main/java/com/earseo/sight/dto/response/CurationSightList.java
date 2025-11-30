package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CurationSightList(
        @Schema(description = "큐레이션 제목",  example = "시간이 머문 서울의 길을 걸어보세요")
        String curationTitle,

        @Schema(description = "큐레이션 세부 설명", example = "서울의 유산이 살아 숨쉬는 이야기로 안내합니다.")
        String description,

        @Schema(description = "큐레이션 내 관광지 목록")
        List<CurationSightResponse> curationSightList
) {
}
