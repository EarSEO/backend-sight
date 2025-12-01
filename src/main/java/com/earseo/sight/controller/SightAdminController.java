package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.dto.request.CurationCreateRequest;
import com.earseo.sight.dto.response.CurationDeleteResponse;
import com.earseo.sight.dto.response.CurationResponse;
import com.earseo.sight.service.CurationService;
import com.earseo.sight.service.InitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<BaseResponse<String>> initSight() {
        initService.initSight();
        initService.initDocent();
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
