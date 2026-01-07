package com.earseo.sight.dto.projection;

public record SightMapItemDto(
        String contentId,
        String title,
        Double mapX,
        Double mapY,
        String cat1
) {
}
