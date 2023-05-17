package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ResponseStatus.FAIL_BAD_REQUEST);
    }
}
