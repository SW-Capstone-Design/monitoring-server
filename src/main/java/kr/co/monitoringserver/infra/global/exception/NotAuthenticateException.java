package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class NotAuthenticateException extends BusinessException {

    public NotAuthenticateException() {
        super(ErrorCode.NOT_AUTHENTICATE_USER);
    }
}
