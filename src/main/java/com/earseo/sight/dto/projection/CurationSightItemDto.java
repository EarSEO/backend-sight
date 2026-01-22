package com.earseo.sight.dto.projection;

public record CurationSightItemDto(
        String contentId,
        String title,
        String subTheme,
        String originImgUrl,
        Double distance,
        String addr3
) {
}
