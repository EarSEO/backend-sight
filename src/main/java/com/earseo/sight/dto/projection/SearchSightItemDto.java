package com.earseo.sight.dto.projection;

public record SearchSightItemDto(
        String contentId,
        String title,
        String subTheme,
        String addr3,
        Double mapX,
        Double mapY,
        Double distance
) {
}
