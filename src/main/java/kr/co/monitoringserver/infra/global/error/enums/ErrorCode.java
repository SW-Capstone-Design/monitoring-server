package kr.co.monitoringserver.infra.global.error.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    SUCCESS_EXECUTE(200, HttpStatus.OK, "성공적으로 실행되었습니다"),
    SUCCESS_CREATED(201, HttpStatus.CREATED, "성공적으로 실행되었고, 새 리소스가 생성되었습니다"),

    FAIL_EXECUTE(203, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "실행에 실패했습니다"),
    FAIL_INVALID_VALUE(400, HttpStatus.BAD_REQUEST, "실행에 실패했고, 이유는 필수 항목을 입력하지 않았습니다"),
    FAIL_BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "실행에 실패했고, 이유는 형식에 맞지 않는 값을 입력하였습니다"),

    // User
    DUPLICATE_USER_ATTENDANCE(400, HttpStatus.BAD_REQUEST, "해당 사용자는 이미 출석하였습니다"),
    NOT_FOUND_USER(404, HttpStatus.NOT_FOUND, "해당 사용자 정보를 찾지 못하였습니다"),
    NOT_AUTHENTICATE_USER(401, HttpStatus.UNAUTHORIZED, "해당 사용자의 권한이 없어 접근할 수 없습니다"),

    // Attendance
    NOT_FOUND_ATTENDANCE(404, HttpStatus.NOT_FOUND, "해당 출석 기록 정보를 찾지 못하였습니다"),

    // AttendanceStatus
    NOT_FOUND_ATTENDANCE_STATUS(404, HttpStatus.NOT_FOUND, "해당 출석 상태 정보를 찾지 못하였습니다");

    private final int status;

    private final HttpStatus httpStatus;

    private final String message;


    ErrorCode(int status,
              HttpStatus httpStatus,
              String message) {

        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
