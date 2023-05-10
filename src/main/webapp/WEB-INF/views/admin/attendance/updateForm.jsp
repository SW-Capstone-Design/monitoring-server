<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
        <div class="form-group">
            <input type="hidden" class="form-control" value="${list.user.userId}" id="userId">
            <div class="form-group">
                <label for="identity">Id</label>
                <input type="identity" class="form-control" value="${list.user.identity}" id="identity" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="date">Date</label>
            <input type="date" class="form-control" value="${list.attendance.date}" id="date" readonly>
        </div>
		<div class="form-group">
			<label for="enterTime">EnterTime</label>
			<input type="time" class="form-control" id="enterTime">
		</div>
        <div class="form-group">
            <label for="leaveTime">LeaveTime</label>
            <input type="time" class="form-control" id="leaveTime">
        </div>
	</form>
	<button id="btn-update" class="btn btn-dark">완료</button>
	<button id="btn-del" class="btn btn-dark">삭제</button>
</div>

<script src="/js/attendance/attendance.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
