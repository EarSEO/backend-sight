package com.earseo.sight.dto.internal;

import com.earseo.sight.dto.projection.SightMetaDto;
import com.earseo.sight.entity.Theme;

public record SightMetaResponse(
        String id,
        String name,
        String address,
        String imageUrl,
        Double latitude,
        Double longitude,
        String docentUrl,
        Theme theme
) {
    public static SightMetaResponse toDto(SightMetaDto dto) {
        return new SightMetaResponse(
                dto.contentId(),
                dto.title(),
                dto.addr3(),
                dto.originImageUrl(),
                dto.mapY(),
                dto.mapX(),
                dto.docentUrl(),
                Theme.valueOf(dto.theme())
        );
    }
}
