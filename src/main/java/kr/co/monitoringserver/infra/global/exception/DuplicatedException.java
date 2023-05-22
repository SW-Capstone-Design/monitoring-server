package kr.co.monitoringserver.infra.global.exception;

import kr.co.monitoringserver.infra.global.model.ResponseStatus;

public class DuplicatedException extends BusinessException {

    public DuplicatedException(ResponseStatus responseStatus) {
        super(responseStatus);
    }
}
