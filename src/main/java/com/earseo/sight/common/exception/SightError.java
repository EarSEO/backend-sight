package com.earseo.sight.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SightError implements ErrorCodeInterface {
    INVALID_COORDINATE_RANGE("SIT001", "최소 위도/경도는 최대 위도/경도보다 작아야 합니다.", HttpStatus.BAD_REQUEST),
    SIGHT_NOT_FOUND("SIT002", "해당 관광지를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    CURATION_NOT_FOUND("CUR001", "큐레이션이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String status;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.builder()
                .status(status)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
