package com.earseo.sight.controller.internal;

import com.earseo.sight.dto.internal.SightMetaResponse;
import com.earseo.sight.service.internal.InternalSightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SightInternalController {
    private final InternalSightService internalSightService;

    @Operation(
            summary = "여러 관광지 메타 정보 조회",
            description = "관광지 ID 목록을 받아 해당 관광지들의 메타 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SightMetaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            )
    })
    @GetMapping("/internal/sight/meta")
    List<SightMetaResponse> getSightByIds(
            @Parameter(
                    description = "조회할 관광지 ID 목록",
                    required = true,
                    example = "126508,129854,130212"
            )
            @RequestParam
            @NotEmpty(message = "최소 1개 이상의 ID가 필요합니다")
            List<String> ids
    ) {
        return internalSightService.getSightByIds(ids);
    }
}
