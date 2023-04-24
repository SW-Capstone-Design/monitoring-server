package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ErrorCode.FAIL_BAD_REQUEST);
    }
}
