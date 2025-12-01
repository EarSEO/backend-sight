package com.earseo.sight.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurationUpdateRequest(
        @Schema(description = "큐레이션 제목", example = "서울 핫플 여행")
        String title,

        @Schema(description = "큐레이션 설명", example = "서울의 인기 관광지를 모았습니다")
        String description,

        @Schema(description = "큐레이션 이미지 URL", example = "https://example.com/image.jpg")
        String curationImgUrl
) {
}
