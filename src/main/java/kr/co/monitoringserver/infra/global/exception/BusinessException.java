package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ResponseStatus responseStatus;

    public BusinessException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public BusinessException(String message, ResponseStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
