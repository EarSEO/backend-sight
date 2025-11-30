package com.earseo.sight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BookmarkList(
        @Schema(description = "사용자의 북마크 목록")
        List<BookmarkResponse> bookmarks
) {
}
