package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class InvalidInputException extends BusinessException {

    public InvalidInputException() {
        super(ResponseStatus.FAIL_INVALID_VALUE);
    }
}
