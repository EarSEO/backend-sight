package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.dto.response.BookmarkList;
import com.earseo.sight.dto.response.BookmarkStatusResponse;
import com.earseo.sight.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user/sight")
@RequiredArgsConstructor
public class SightUserController {

    private final BookmarkService bookmarkService;

    @Operation(
            summary = "북마크 추가",
            description = """
                    특정 관광지를 북마크에 추가합니다.
                    - 이미 북마크된 경우 멱등적으로 성공 응답을 반환합니다.
                    - 회원 인증이 필요한 API입니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 추가 성공",
                    content = @Content(schema = @Schema(implementation = BookmarkStatusResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "관광지 ID 누락",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "관광지 ID는 필수입니다",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "회원 ID 누락",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "회원 ID는 필수입니다",
                                                        "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @PostMapping("/{sightId}/bookmark")
    public ResponseEntity<BaseResponse<BookmarkStatusResponse>> addBookmark(
            @Parameter(
                    description = "회원 ID (Gateway에서 JWT 검증 후 전달)",
                    required = true,
                    example = "1"
            )
            @RequestHeader("X-USER-ID")
            @NotNull(message = "회원 ID는 필수입니다")
            Long memberId,

            @Parameter(
                    description = "관광지 Content ID",
                    required = true,
                    example = "129854"
            )
            @PathVariable
            @NotBlank(message = "관광지 ID는 필수입니다")
            String sightId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(bookmarkService.addBookmark(memberId, sightId, "ko")));
    }

    @Operation(
            summary = "북마크 삭제",
            description = """
                    특정 관광지를 북마크에서 제거합니다.
                    - 이미 삭제된 경우 멱등적으로 성공 응답을 반환합니다.
                    - 회원 인증이 필요한 API입니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 삭제 성공",
                    content = @Content(schema = @Schema(implementation = BookmarkStatusResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "관광지 ID 누락",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "관광지 ID는 필수입니다",
                                                        "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "회원 ID 누락",
                                            value = """
                                                    {
                                                        "status": "ARGUMENT_ERROR",
                                                        "message": "회원 ID는 필수입니다",
                                                        "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @DeleteMapping("/{sightId}/bookmark")
    public ResponseEntity<BaseResponse<BookmarkStatusResponse>> deleteBookmark(
            @Parameter(
                    description = "회원 ID (Gateway에서 JWT 검증 후 전달)",
                    required = true,
                    example = "1"
            )
            @RequestHeader("X-USER-ID")
            @NotNull(message = "회원 ID는 필수입니다")
            Long memberId,

            @Parameter(
                    description = "관광지 Content ID",
                    required = true,
                    example = "129854"
            )
            @PathVariable
            @NotBlank(message = "관광지 ID는 필수입니다")
            String sightId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(bookmarkService.deleteBookmark(memberId, sightId)));
    }

    @Operation(
            summary = "북마크 목록 조회",
            description = """
                    회원이 북마크한 모든 관광지 목록을 조회합니다.
                    - 북마크가 없는 경우 빈 배열을 반환합니다.
                    - 회원 인증이 필요한 API입니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BookmarkList.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "회원 ID 누락",
                                    value = """
                                            {
                                                "status": "ARGUMENT_ERROR",
                                                "message": "회원 ID는 필수입니다",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })

    @GetMapping("/bookmark")
    public ResponseEntity<BaseResponse<BookmarkList>> getBookmark(
            @Parameter(
                    description = "회원 ID (Gateway에서 JWT 검증 후 전달)",
                    required = true,
                    example = "1"
            )
            @RequestHeader("X-USER-ID")
            @NotNull(message = "회원 ID는 필수입니다")
            Long memberId
    ) {
        return ResponseEntity.ok(BaseResponse.ok(bookmarkService.getBookmarkList(memberId)));
    }
}
