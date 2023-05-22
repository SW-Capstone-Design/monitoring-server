package kr.co.monitoringserver.infra.handler;

import kr.co.monitoringserver.infra.global.model.ResponseErrorFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.warn("======= HandleMethodArgumentNotValidException ======", e);

        final ResponseStatus responseStatus = ResponseStatus.FAIL_INVALID_PARAMETER;

        return handleExceptionInternal(e, responseStatus);
    }


    private ResponseEntity<ResponseErrorFormat> handleExceptionInternal(final BindException e,
                                                                        final ResponseStatus responseStatus) {

        return ResponseEntity
                .status(responseStatus.getHttpStatus())
                .body(makeResponseErrorFormat(e, responseStatus));
    }

    private ResponseErrorFormat makeResponseErrorFormat(final BindException e,
                                                        final ResponseStatus responseStatus) {

        final List<ResponseErrorFormat.ValidationException> validationExceptions = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ResponseErrorFormat.ValidationException::of).toList();

        return ResponseErrorFormat.builder()
                .message(responseStatus.getMessage())
                .httpStatus(responseStatus.getHttpStatus())
                .validationExceptions(validationExceptions)
                .build();
    }
}
