package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    /**
     * 출석 서비스
     * 출석 기록과 출석 상태 간의 관계를 설정하고, 이를 활용하여 출석 상태를 출력하는 기능
     */

    private final AttendanceRepository attendanceRepository;

    /** Get User Attendance Records Service
     *  특정 사용자의 출석 기록을 조회
     */


    /** Get User Attendance Records By Date Service
     *  특정 일자의 모든 사용자의 출석 기록을 조회
     */


    /** Get User Attendance Records By Specific Period Service
     *  특정 기간 동안의 모든 사용자의 출석 기록을 조회
     */


    /** Create Attendance Go-Work & Leave-Work Recording Service
     *  특정 사용자의 출근 및 퇴근 기록을 등록
     */


    /** Update Attendance Records Service
     *  등록된 출석 기록을 수정
     */


    /** Delete Attendance Records Service
     *  등록된 출석 기록을 삭제
     */
    @Transactional
    public void deleteAttendance(Long attendanceId) {

        final Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        attendanceRepository.delete(attendance);
    }
}