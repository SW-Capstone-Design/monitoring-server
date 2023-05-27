package kr.co.monitoringserver.infra.global.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {

    // Success
    SUCCESS_EXECUTE(HttpStatus.OK, "성공적으로 실행되었습니다"),
    SUCCESS_CREATED(HttpStatus.CREATED, "성공적으로 실행되었고, 새 리소스가 생성되었습니다"),

    // Fail
    FAIL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "실행에 실패했고, 해당 요청이 잘못되었습니다"),

    // TODO : 오류 종류 추가 및 수정

    FAIL_INVALID_VALUE(HttpStatus.BAD_REQUEST, "실행에 실패했고, 해당 항목의 값이 존재하지 않습니다"),
    FAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "실행에 실패했고, 해당 정보를 찾을 수 없습니다"),
    FAIL_NOT_AUTHENTICATE(HttpStatus.UNAUTHORIZED, "실행에 실패했고, 해당 권한이 없습니다"),
    FAIL_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "실행에 실패했고, 해당 항목의 값이 존재하지 않습니다"),

    // User
    DUPLICATE_USER_ATTENDANCE(HttpStatus.BAD_REQUEST, "해당 사용자는 이미 출석하였습니다"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 사용자 정보를 찾지 못하였습니다"),

    // UserAttendance
    NOT_FOUND_ATTENDANCE(HttpStatus.NOT_FOUND, "해당 출석 기록 정보를 찾지 못하였습니다"),

    // SecurityArea
    NOT_FOUND_SECURITY_AREA(HttpStatus.NOT_FOUND, "해당 보안 구역 정보를 찾지 못하였습니다"),

    // Beacon
    NOT_FOUND_BEACON(HttpStatus.NOT_FOUND, "해당 비콘 정보를 찾지 못하였습니다");


    private final HttpStatus httpStatus;

    private final String message;


    ResponseStatus(HttpStatus httpStatus,
                   String message) {

        this.httpStatus = httpStatus;
        this.message = message;
    }
}
