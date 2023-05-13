<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
    <div>
        <br>

        <h1>관리자 페이지</h1>

            <br>
            솔문님 보안구역-경고알림 : 구현 완
            <br><br>
            관리자페이지 ui 한 페이지로 통합 : 조회 유형별 통합 / 조회(수정, 삭제) + 등록 통합
            <br><br>

            비콘 api
            <br>
            /auth/transferBeacon : 모바일에서 서버의 비콘 데이터 참조
            <br>
            /auth/receiveBeacon/{user_id} : 모바일에서 서버로 rssi 전송시
            <br>
            /auth/deleteBeaconConnection/{user_id} : 유저가 비콘의 영역에서 벗어나거나 로그아웃 등을 할 경우 해당 유저에 대한 user_beacon data 모두 삭제
            <br>
            요청하신 부분은 아니지만 넣어봤습니다.
            <br><br>
            솔문님 : 웹푸시알림 구현중
            <br>
            김진형 : 피드백 후 비콘 api 수정 및 삼변측량 구현 예정
            <br>
    </div>
</div>

<%@ include file="../layout/admin/footer.jsp"%>
