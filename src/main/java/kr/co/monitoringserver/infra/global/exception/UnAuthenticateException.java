package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class UnAuthenticateException extends BusinessException {

    public UnAuthenticateException() {
        super(ResponseStatus.FAIL_NOT_AUTHENTICATE);
    }

    public UnAuthenticateException(String message) {
        super(message, ResponseStatus.FAIL_NOT_AUTHENTICATE);
    }
}
