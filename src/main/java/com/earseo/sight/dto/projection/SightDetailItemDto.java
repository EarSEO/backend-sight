package com.earseo.sight.dto.projection;

public record SightDetailItemDto(
        String contentId,
        String theme,
        String subTheme,
        String overview,
        String title,
        String addr1,
        String addr3,
        Double mapX,
        Double mapY,
        String tel,
        String originImgUrl,
        String usetime,
        String restdate,
        String parking,
        String usefee,
        Double distance,
        String docentUrl,
        boolean isBookmarked
) {
}
