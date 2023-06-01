package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class NotFoundException extends BusinessException {

    public NotFoundException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public NotFoundException(String message) {
        super(message, ResponseStatus.FAIL_NOT_FOUND);
    }
}
