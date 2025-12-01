package com.earseo.sight.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CurationCreateRequest(
        @NotBlank(message = "제목은 필수입니다")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다")
        String title,

        @Size(max = 500, message = "설명은 500자 이하여야 합니다")
        String subtitle,

        String curationImgUrl,

        @NotEmpty(message = "관광지 목록은 필수입니다")
        List<String> contentIds
) {

}