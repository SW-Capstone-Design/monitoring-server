package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
