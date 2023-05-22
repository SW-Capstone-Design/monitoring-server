package kr.co.monitoringserver.infra.handler;

import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseErrorFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseErrorFormat> handleNotFoundException(NotFoundException e) {

        log.warn("======= HandleNotFoundException ======", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .httpStatus(ResponseStatus.FAIL_NOT_FOUND.getHttpStatus())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorFormat);
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ResponseErrorFormat> handleDuplicatedException(DuplicatedException e) {

        log.warn("======= HandleDuplicatedException ======", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .httpStatus(ResponseStatus.FAIL_BAD_REQUEST.getHttpStatus())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorFormat);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseErrorFormat> handleRuntimeException(RuntimeException e) {

        log.warn("======= HandleRuntimeException ======", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .httpStatus(ResponseStatus.FAIL_BAD_REQUEST.getHttpStatus())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorFormat);
    }
}
