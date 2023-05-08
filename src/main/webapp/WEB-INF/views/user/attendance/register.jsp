<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container" >
    <h2>출결등록</h2>
    <span>
        출근 상태를 등록합니다.
    </span>
    <br><br>
    <hr>
	<form>
		<div class="form-group">
		    <input type="hidden" id="userIdentity" value="${principal.user.identity}" />
		    <input type="hidden" id="enterTime" value="01:46:00" />
		    <input type="hidden" id="leaveTime" value="20:46:00" />
		    <input type="hidden" id="date" value="2023-05-01" />
		</div>
			<button id="btn-register" class="btn btn-dark">출결</button>
	</form>
</div>

<script src="/js/attendance/attendance.js"></script>
<%@ include file="../../layout/user/footer.jsp"%>
