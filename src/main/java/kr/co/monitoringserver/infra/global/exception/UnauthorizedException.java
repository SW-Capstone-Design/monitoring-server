package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
