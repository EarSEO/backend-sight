package com.earseo.sight.dto.projection;

import com.earseo.sight.entity.Theme;

public record SightMetaDto(
        String contentId,
        String title,
        String addr3,
        String originImageUrl,
        Double mapY,
        Double mapX,
        String docentUrl,
        String theme
) {
}
