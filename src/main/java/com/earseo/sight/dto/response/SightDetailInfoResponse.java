package com.earseo.sight.dto.response;

import com.earseo.sight.dto.projection.SightDetailItemDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SightDetailInfoResponse(
        @Schema(description = "관광지 고유 ID", example = "126508")
        String id,

        @Schema(description = "관광지 대분류", example = "인문(문화/예술/역사)")
        String theme,

        @Schema(description = "관광지 중분류", example = "체험관광지")
        String detailTheme,

        @Schema(description = "관광지 개요/설명", example = "조선시대 왕궁으로 500년 역사를 간직하고 있습니다")
        String outl,

        @Schema(description = "관광지 이름", example = "경복궁")
        String title,

        @Schema(description = "전체 주소 (도로명 또는 지번 주소)", example = "서울특별시 종로구 사직로 161")
        String fullAddress,

        @Schema(description = "주소 요약 (구/동 단위)", example = "서울 종로구")
        String address,

        @Schema(description = "경도 (Longitude)", example = "126.9770")
        Double longitude,

        @Schema(description = "위도 (Latitude)", example = "37.5796")
        Double latitude,

        @Schema(description = "전화번호", example = "02-3700-3900")
        String tel,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
        String imgUrl,

        @Schema(description = "이용 시간 정보", example = "09:00 ~ 18:00")
        String useTime,

        @Schema(description = "휴무일 정보", example = "매주 화요일")
        String restDate,

        @Schema(description = "주차 가능 여부 및 정보", example = "주차 가능 (유료)")
        String parking,

        @Schema(description = "입장료/이용료 정보", example = "성인 3,000원")
        String useFee,

        @Schema(description = "현재 위치로부터의 직선 거리 (미터)", example = "1234.56")
        Double distance,

        @Schema(description = "도슨트(오디오 가이드) URL", example = "https://example.com/docent/126508.mp3")
        String docentUrl,

        @Schema(description = "북마크 여부", example = "true")
        boolean isBookmarked,

        @Schema(description = "관광지가 포함된 큐레이션 목록")
        List<CurationResponse> curationList
) {
    public static SightDetailInfoResponse toDto(SightDetailItemDto dto, List<CurationResponse> curationList) {
        return new SightDetailInfoResponse(
                dto.contentId(),
                dto.cat1(),
                dto.cat2(),
                dto.outl(),
                dto.title(),
                dto.addr1(),
                dto.addr3(),
                dto.mapX(),
                dto.mapY(),
                dto.tel(),
                dto.originImgUrl(),
                dto.usetime(),
                dto.restdate(),
                dto.parking(),
                dto.usefee(),
                dto.distance(),
                dto.docentUrl(),
                dto.isBookmarked(),
                curationList
        );
    }
}
