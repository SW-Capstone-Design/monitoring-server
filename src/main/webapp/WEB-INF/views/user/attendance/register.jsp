<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container" >
    <h2>출결등록</h2>
    <span>
        출근 및 퇴근상태를 등록합니다.
    </span>
    <br><br>
    <hr>
	<form>
		<div class="form-group">
		    <input type="hidden" id="userId" value="${principal.user.userId}" />
		    <input type="hidden" id="userIdentity" value="${principal.user.identity}" />
		</div>
	</form>
	<button id="btn-register" class="btn btn-dark">출근</button>
	<button id="btn-leave" class="btn btn-dark">퇴근</button>
</div>

<script src="/js/attendance/attendance.js"></script>
<%@ include file="../../layout/user/footer.jsp"%>
