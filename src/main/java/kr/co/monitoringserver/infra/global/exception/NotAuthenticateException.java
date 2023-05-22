package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class NotAuthenticateException extends BusinessException {

    public NotAuthenticateException() {
        super(ResponseStatus.FAIL_NOT_AUTHENTICATE);
    }
}
