package com.earseo.sight.dto.projection;

public record SearchSightItemDto(
        String contentId,
        String title,
        String cat2,
        String addr3,
        Double mapX,
        Double mapY,
        Double distance
) {
}
