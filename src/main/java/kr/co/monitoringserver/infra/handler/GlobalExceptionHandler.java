package kr.co.monitoringserver.infra.handler;

import kr.co.monitoringserver.infra.global.exception.*;
import kr.co.monitoringserver.service.dtos.response.ResponseDTO;
import kr.co.monitoringserver.infra.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDTO<String> handleArgumentException(Exception e) {
        return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }



    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

        log.error("handleBusinessException throws BusinessException : {}", e.getErrorCode());

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
