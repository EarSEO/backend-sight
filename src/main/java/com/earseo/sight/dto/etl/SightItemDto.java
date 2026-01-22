package com.earseo.sight.dto.etl;

public record SightItemDto(String contentId, String contentTypeId, String cat1,
                           String cat2, String cat1Code, String cat2Code, String title,
                           String addr1, String addr2, String addr3,
                           Double mapX, Double mapY, String modifiedtime, String tel, Integer mLevel, String overview,
                           String originImgUrl, String smallImgUrl, String usetime, String restdate, String parking, String usefee) {
}
