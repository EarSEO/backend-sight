package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.dto.request.CurationCreateRequest;
import com.earseo.sight.dto.request.CurationUpdateRequest;
import com.earseo.sight.dto.request.InitRequest;
import com.earseo.sight.dto.response.CurationDeleteResponse;
import com.earseo.sight.dto.response.CurationResponse;
import com.earseo.sight.service.CurationService;
import com.earseo.sight.service.InitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/sight")
@RequiredArgsConstructor
public class SightAdminController {

    private final InitService initService;
    private final CurationService curationService;

    @PostMapping("/init")
    public ResponseEntity<BaseResponse<String>> initSight(
            @RequestBody
            @Valid
            InitRequest request
    ) {
        initService.initSight(request.lang());
        initService.initDocent(request.lang());
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "큐레이션 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 입력")
    })
    @PostMapping("/curation")
    public ResponseEntity<BaseResponse<CurationResponse>> createCuration(
            @RequestBody
            @Valid
            CurationCreateRequest request
    ){
        return ResponseEntity.ok(BaseResponse.ok(curationService.createCuration(request)));
    }

    @PutMapping("/curation/{curationId}")
    @Operation(
            summary = "큐레이션 수정",
            description = "큐레이션 정보를 수정합니다. 제공된 필드만 업데이트됩니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "큐레이션 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CurationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "큐레이션을 찾을 수 없음"
            )
    })
    public ResponseEntity<BaseResponse<CurationResponse>> updateCuration(
            @Parameter(description = "큐레이션 ID", required = true)
            @PathVariable Long curationId,
            @RequestBody @Valid CurationUpdateRequest request
    ) {
        CurationResponse response = curationService.updateCuration(curationId, request);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @Operation(
            summary = "큐레이션 삭제",
            description = "큐레이션을 삭제합니다. 연관된 큐레이션-관광지 매핑도 함께 삭제됩니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "큐레이션 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "큐레이션을 찾을 수 없음"
            )
    })
    @DeleteMapping("/curation/{curationId}")
    public ResponseEntity<BaseResponse<CurationDeleteResponse>> deleteCuration(
            @Parameter(description = "큐레이션 ID", required = true)
            @PathVariable Long curationId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(curationService.deleteCuration(curationId)));
    }
}
