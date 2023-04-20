package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class DuplicatedException extends BusinessException {

    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
