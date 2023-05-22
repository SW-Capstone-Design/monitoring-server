package kr.co.monitoringserver.infra.global.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class ResponseFormat<T> {

    private HttpStatus httpStatus;

    private String message;

    private boolean success;

    private T data;


    public static <T> ResponseFormat<T> successMessage(ResponseStatus responseStatus, String message) {
        return ResponseFormat.<T>builder()
                .httpStatus(responseStatus.getHttpStatus())
                .message(message)
                .success(true)
                .data(null)
                .build();
    }

    public static <T> ResponseFormat<T> successData(ResponseStatus responseStatus, T data) {
        return ResponseFormat.<T>builder()
                .httpStatus(responseStatus.getHttpStatus())
                .message(responseStatus.getMessage())
                .success(true)
                .data(data)
                .build();

    }

    public static <T> ResponseFormat<T> fail(ResponseStatus responseStatus) {
        return ResponseFormat.<T>builder()
                .httpStatus(responseStatus.getHttpStatus())
                .message(responseStatus.getMessage())
                .success(false)
                .data(null)
                .build();
    }
}
