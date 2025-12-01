package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurationResponse(
        @Schema(description = "큐레이션 ID", example = "1")
        Long curationId,

        @Schema(description = "큐레이션 제목",  example = "시간이 머문 서울의 길을 걸어보세요")
        String curationTitle,

        @Schema(description = "큐레이션 세부 설명", example = "서울의 유산이 살아 숨쉬는 이야기로 안내합니다.")
        String description,

        @Schema(description = "큐레이션 이미지 URL", example = "https://example.com/image.jpg")
        String curationImgUrl
) {
}
