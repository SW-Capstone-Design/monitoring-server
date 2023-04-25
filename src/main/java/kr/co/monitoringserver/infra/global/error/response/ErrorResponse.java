package kr.co.monitoringserver.infra.global.error.response;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {

    private int status;

    private HttpStatus httpStatus;

    private String message;


    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .httpStatus(errorCode.getHttpStatus())
                        .message(errorCode.getMessage())
                        .build()
                );
    }
}
