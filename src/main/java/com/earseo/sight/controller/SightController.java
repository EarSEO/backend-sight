package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.dto.response.SightDetailInfoResponse;
import com.earseo.sight.dto.response.SightMapInfoList;
import com.earseo.sight.service.SightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/sight")
@RequiredArgsConstructor
public class SightController {

    private final SightService sightService;

    @Operation(
            summary = "지도 사각형 영역 내 관광지 조회",
            description = """
                    지도에서 사각형 영역 내의 관광지 목록을 조회합니다.
                    - 좌측 하단(minLongitude, minLatitude)과 우측 상단(maxLongitude, maxLatitude) 좌표를 받습니다.
                    - 해당 영역 내에 포함된 모든 관광지의 이름과 위치 정보를 반환합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SightMapInfoList.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "좌표 범위 오류",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "최소 위도/경도는 최대 위도/경도보다 작아야 합니다.",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "경도 범위 초과",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "경도는 124 이상이어야 합니다",
                                                        "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @GetMapping("/map/rectangle")
    public ResponseEntity<BaseResponse<SightMapInfoList>> getSightRectangle(
            @Parameter(
                    description = "사각형 영역의 최소 경도 (좌측 하단)",
                    required = true,
                    example = "126.9000",
                    schema = @Schema(minimum = "124", maximum = "133")
            )
            @RequestParam
            @NotNull(message = "최소 경도는 필수입니다")
            @DecimalMin(value = "124", message = "경도는 124 이상이어야 합니다")
            @DecimalMax(value = "133", message = "경도는 133 이하여야 합니다")
            Double minLongitude,

            @Parameter(
                    description = "사각형 영역의 최소 위도 (좌측 하단)",
                    required = true,
                    example = "37.5000",
                    schema = @Schema(minimum = "33.0", maximum = "39")
            )
            @RequestParam
            @NotNull(message = "최소 위도는 필수입니다")
            @DecimalMin(value = "33.0", message = "위도는 33 이상이어야 합니다")
            @DecimalMax(value = "39", message = "위도는 39 이하여야 합니다")
            Double minLatitude,

            @Parameter(
                    description = "사각형 영역의 최대 경도 (우측 상단)",
                    required = true,
                    example = "127.1000",
                    schema = @Schema(minimum = "124", maximum = "133")
            )
            @RequestParam
            @NotNull(message = "최대 경도는 필수입니다")
            @DecimalMin(value = "124", message = "경도는 124 이상이어야 합니다")
            @DecimalMax(value = "133", message = "경도는 133 이하여야 합니다")
            Double maxLongitude,

            @Parameter(
                    description = "사각형 영역의 최대 위도 (우측 상단)",
                    required = true,
                    example = "37.6000",
                    schema = @Schema(minimum = "33.0", maximum = "39")
            )
            @RequestParam
            @NotNull(message = "최대 위도는 필수입니다")
            @DecimalMin(value = "33.0", message = "위도는 33 이상이어야 합니다")
            @DecimalMax(value = "39", message = "위도는 39 이하여야 합니다")
            Double maxLatitude
    ) {

        return ResponseEntity.ok(BaseResponse.ok(sightService.getMapRectangle(minLongitude, minLatitude, maxLongitude, maxLatitude)));
    }


    @Operation(
            summary = "특정 지점 반경 내 관광지 조회",
            description = """
                    특정 좌표를 중심으로 지정한 반경(미터) 내의 관광지 목록을 조회합니다.
                    - 중심 좌표와 반경을 입력받습니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SightMapInfoList.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "반경 범위 오류",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "반경은 1 이상이어야 합니다",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "경도 범위 초과",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "경도는 124 이상이어야 합니다",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "최대 반경 초과",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "반경은 30000 이하여야 합니다",
                                                        "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @GetMapping("/map/circle")
    public ResponseEntity<BaseResponse<SightMapInfoList>> getSightCircle(
            @Parameter(
                    description = "검색 반경 (미터 단위)",
                    required = true,
                    example = "1000",
                    schema = @Schema(minimum = "1", maximum = "30000")
            )
            @RequestParam
            @NotNull(message = "반경은 필수입니다")
            @DecimalMin(value = "1", message = "반경은 1 이상이어야 합니다")
            @DecimalMax(value = "30000", message = "반경은 30000 이하여야 합니다")
            Double meters,

            @Parameter(
                    description = "중심점 경도",
                    required = true,
                    example = "126.9780",
                    schema = @Schema(minimum = "124", maximum = "133")
            )
            @RequestParam
            @NotNull(message = "경도는 필수입니다")
            @DecimalMin(value = "124", message = "경도는 124 이상이어야 합니다")
            @DecimalMax(value = "133", message = "경도는 133 이하여야 합니다")
            Double longitude,

            @Parameter(
                    description = "중심점 위도",
                    required = true,
                    example = "37.5665",
                    schema = @Schema(minimum = "33.0", maximum = "39")
            )
            @RequestParam
            @NotNull(message = "위도는 필수입니다")
            @DecimalMin(value = "33.0", message = "위도는 33 이상이어야 합니다")
            @DecimalMax(value = "39", message = "위도는 39 이하여야 합니다")
            Double latitude
    ) {
        return ResponseEntity.ok(BaseResponse.ok(sightService.getMapCircle(meters, longitude, latitude)));
    }

    @Operation(
            summary = "관광지 상세 정보 조회",
            description = """
                    특정 관광지의 상세 정보를 조회합니다.
                    - 관광지 ID와 현재 위치 좌표를 받아 거리 정보를 포함한 상세 정보를 반환합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SightDetailInfoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "좌표 범위 오류",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "최소 위도/경도는 최대 위도/경도보다 작아야 합니다.",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "경도 범위 초과",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "경도는 124 이상이어야 합니다",
                                                        "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "관광지를 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SIGHT_NOT_FOUND",
                                                "message": "해당 관광지를 찾을 수 없습니다",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/detail")
    public ResponseEntity<BaseResponse<SightDetailInfoResponse>> getSightDetail(
            @Parameter(
                    description = "관광지 고유 ID",
                    required = true,
                    example = "126508"
            )
            @RequestParam
            @NotBlank(message = "ID는 필수입니다")
            String id,

            @RequestParam
            @NotNull(message = "경도는 필수입니다")
            @DecimalMin(value = "124", message = "경도는 124 이상이어야 합니다")
            @DecimalMax(value = "133", message = "경도는 133 이하여야 합니다")
            Double longitude,

            @Parameter(
                    description = "중심점 위도",
                    required = true,
                    example = "37.5665",
                    schema = @Schema(minimum = "33.0", maximum = "39")
            )
            @RequestParam
            @NotNull(message = "위도는 필수입니다")
            @DecimalMin(value = "33.0", message = "위도는 33 이상이어야 합니다")
            @DecimalMax(value = "39", message = "위도는 39 이하여야 합니다")
            Double latitude
    ) {
        return ResponseEntity.ok(BaseResponse.ok(sightService.getSightDetailInfo(id, longitude, latitude)));
    }
}
