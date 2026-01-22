package com.earseo.sight.dto.projection;

import com.earseo.sight.entity.Theme;

public record SightMapItemDto(
        String contentId,
        String title,
        Double mapX,
        Double mapY,
        String theme
) {
}
