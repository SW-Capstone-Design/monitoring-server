<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
    <div>
        <br>

        <h1>관리자 페이지</h1>

            <br>
            서버
            <br>
            솔문님 삼변측량 구현하셨으며 테스트 및 보완이 필요하시다고 합니다.
            <br>
            저(김진형)는 SSE 연결되는 것은 확인하였으나 Service에서 클라이언트로 데이터 전송하는 부분은 논의 및 고민해야합니다.
            <br>
            엔드포인트 형식 통합 및 안쓰이는 메소드 삭제 예정
            <br><br>

            모바일 영록님
            <br>
            앱 실행시 권한 요청 및 블루투스 실행 요청 구현
            <br>
            로그인 여부 확인 / 미로그인시 로그인 요청 구현
            <br>
            서버로부터 비콘 목록 조회 구현
            <br>
            해당 비콘 RSSI값 모아 전송 구현

            <br><br>
            비콘 api
            <br>
            /auth/transferBeacon : 모바일에서 서버의 비콘 데이터 참조
            <br>
            /auth/receiveBeacon/{user_id} : 모바일에서 서버로 rssi 전송시
            <br>
            /auth/deleteBeaconConnection/{user_id} : 유저가 비콘의 영역에서 벗어나거나 로그아웃 등을 할 경우 해당 유저에 대한 user_beacon data 모두 삭제
            <br><br>
            솔문님 : 삼변측량 구현 완 테스트 방법 고민중 / 비콘 고유데이터 CRUD 변경(Location 추가)
            <br>
            김진형 : 관리자페이지 헤더알림 / sse 세팅(서버->클라이언트 데이터 전송 고민중)
            <br>
    </div>
</div>

<%@ include file="../layout/admin/footer.jsp"%>
