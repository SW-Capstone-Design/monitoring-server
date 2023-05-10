<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="work">정상출퇴근</label>
			<input type="text" value="${work}" class="form-control" id="work" readonly>
		</div>
        <div class="form-group">
            <label for="tardiness">지각</label>
            <input type="text" value="${tardiness}" class="form-control" id="tardiness" readonly>
        </div>
        <div class="form-group">
            <label for="earlyLeave">조퇴</label>
            <input type="text" value="${earlyLeave}" class="form-control" id="earlyLeave" readonly>
        </div>
        <div class="form-group">
            <label for="absent">결근</label>
            <input type="text" value="${absent}" class="form-control" id="absent" readonly>
        </div>
	</form>
</div>

<%@ include file="../../layout/user/footer.jsp"%>
