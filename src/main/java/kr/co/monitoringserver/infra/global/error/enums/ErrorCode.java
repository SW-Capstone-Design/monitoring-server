package kr.co.monitoringserver.infra.global.error.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    SUCCESS_EXECUTE(200, "COMMON-200", "성공적으로 실행되었습니다"),
    SUCCESS_CREATED(201, "COMMON-201", "성공적으로 실행되었고, 새 리소스가 생성되었습니다"),

    FAIL(203, "COMMON-203", "실행에 실패했습니다"),
    FAIL_INVALID_VALUE(400, "COMMON-400", "실행에 실패했고, 이유는 필수 항목을 입력하지 않았습니다"),
    FAIL_BAD_REQUEST(400, "COMMON-400", "실행에 실패했고, 이유는 형식에 맞지 않는 값을 입력하였습니다"),

    // User
    NOT_FOUND_USER(404, "USER-404", "해당 사용자 정보를 찾지 못하였습니다"),
    NOT_AUTHENTICATE_USER(401, "USER-401", "해당 사용자의 권한이 없어 접근할 수 없습니다"),

    // Attendance
    NOT_FOUND_ATTENDANCE(404, "ATTENDANCE-404", "해당 출석 정보를 찾지 못하였습니다"),
    DUPLICATE_ATTENDANCE(400, "ATTENDANCE-400", "해당 사용자는 이미 출석하였습니다"),

    // AttendanceStatus
    NOT_FOUND_ATTENDANCE_STATUS(404, "ATTENDANCE_STATUS-404", "해당 출석 상태 정보를 찾지 못하였습니다");

    private final int status;

    private final String code;

    private final String message;


    ErrorCode(int status,
              String code,
              String message) {

        this.status = status;
        this.code = code;
        this.message = message;
    }
}
