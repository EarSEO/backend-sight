package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.dto.response.*;
import com.earseo.sight.service.CurationService;
import com.earseo.sight.service.SightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/sight")
@RequiredArgsConstructor
public class SightController {

    private final SightService sightService;
    private final CurationService curationService;

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
            Double latitude,

            @Parameter(
                    description = "사용자 ID",
                    example = "1"
            )
            @RequestHeader(value = "X-USER-ID", required = false)
            Long memberId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(sightService.getSightDetailInfo(id, longitude, latitude, memberId)));
    }

    @Operation(
            summary = "도슨트 정보 조회",
            description = "관광지 ID로 해당 관광지의 도슨트 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = DocentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효하지 않은 sightId)"
            )
    })
    @GetMapping("/docent")
    public ResponseEntity<BaseResponse<DocentResponse>> getDocent(
            @Parameter(
                    description = "관광지 ID",
                    required = true,
                    example = "123432"
            )
            @RequestParam
            String sightId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(sightService.getDocent(sightId)));
    }

    @Operation(
            summary = "큐레이션 목록 조회",
            description = "큐레이션 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "큐레이션 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CurationList.class)
                    )
            )
    })
    @GetMapping("/curation")
    public ResponseEntity<BaseResponse<CurationList>> getCurationList() {
        return ResponseEntity.ok(BaseResponse.ok(curationService.getCurationList()));
    }

    @Operation(
            summary = "큐레이션에 포함된 관광지 목록 조회",
            description = "지정된 큐레이션 ID에 포함된 관광지들을 중심점(위도/경도) 기준 거리순으로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = CurationSightList.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "파라미터 오류"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "큐레이션을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "CURATION_NOT_FOUND",
                                                "message": "큐레이션이 존재하지 않습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/curation/{curationId}")
    public ResponseEntity<BaseResponse<CurationSightList>> getCurationSight(
            @Parameter(
                    description = "큐레이션 ID",
                    required = true,
                    example = "1"
            )
            @PathVariable
            Long curationId,
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
        return ResponseEntity.ok(BaseResponse.ok(curationService.getCurationSight(curationId, longitude, latitude)));
    }

    @Operation(
            summary = "관광지 키워드 검색 (사각 영역)",
            description = """
            지정된 사각형 영역 내에서 키워드를 포함하는 관광지를 검색합니다.
            - 관광지 이름 중에서 키워드가 포함된 관광지를 반환합니다.
            - 요청 좌표로부터의 거리를 함께 반환합니다.
            - 거리가 가까운 순서대로 반환합니다.
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SearchSightList.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "키워드 누락",
                                            value = """
                            {
                                "status": "ARGUMENT_ERROR",
                                "message": "검색 키워드는 필수입니다",
                                "data": null
                            }
                            """
                                    ),
                                    @ExampleObject(
                                            name = "키워드 길이 초과",
                                            value = """
                            {
                                "status": "ARGUMENT_ERROR",
                                "message": "검색 키워드는 20자 이하여야 합니다",
                                "data": null
                            }
                            """
                                    ),
                                    @ExampleObject(
                                            name = "좌표 범위 오류",
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
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<SearchSightList>> searchTitle(
            @Parameter(
                    description = "검색 키워드",
                    required = true,
                    example = "맛집"
            )
            @RequestParam
            @NotBlank(message = "검색 키워드는 필수입니다")
            @Size(max = 20, message = "검색 키워드는 20자 이하여야 합니다")
            String keyword,

            @Parameter(
                    description = "기준 경도",
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
                    description = "기준 위도",
                    required = true,
                    example = "37.5665",
                    schema = @Schema(minimum = "33.0", maximum = "39")
            )
            @RequestParam
            @NotNull(message = "위도는 필수입니다")
            @DecimalMin(value = "33.0", message = "위도는 33 이상이어야 합니다")
            @DecimalMax(value = "39", message = "위도는 39 이하여야 합니다")
            Double latitude,

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
            Double maxLatitude,


            @Parameter(
                    description = "반환할 최대 결과 개수",
                    example = "10",
                    schema = @Schema(minimum = "1", maximum = "100")
            )
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "제한 개수는 1 이상이어야 합니다")
            @Max(value = 100, message = "제한 개수는 100 이하여야 합니다")
            Integer limit
    ) {
        return ResponseEntity.ok(BaseResponse.ok(
                sightService.searchSight(keyword, longitude, latitude, minLongitude, minLatitude, maxLongitude, maxLatitude, limit)
        ));
    }
}
