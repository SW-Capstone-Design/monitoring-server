package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class InvalidInputException extends BusinessException {

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}
