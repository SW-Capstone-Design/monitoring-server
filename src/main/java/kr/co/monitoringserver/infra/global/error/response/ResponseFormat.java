package kr.co.monitoringserver.infra.global.error.response;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseFormat<T> {

    private int status;

    private String message;

    private boolean success;

    private T data;


    public static <T> ResponseFormat<T> successMessage(ErrorCode errorCode , String message) {
        return ResponseFormat.<T>builder()
                .status(errorCode.getStatus())
                .message(message)
                .success(true)
                .data(null)
                .build();
    }

    public static <T> ResponseFormat<T> successData(ErrorCode errorCode, T data) {
        return ResponseFormat.<T>builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .success(true)
                .data(data)
                .build();

    }

    public static <T> ResponseFormat<T> fail(ErrorCode errorCode) {
        return ResponseFormat.<T>builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .success(false)
                .data(null)
                .build();
    }
}
