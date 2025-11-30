package com.earseo.sight.dto.projection;

public record SightMetaDto(
        String contentId,
        String title,
        String addr3,
        String originImageUrl,
        Double mapY,
        Double mapX,
        String docentUrl,
        String cat1
) {
}
