<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container" >
	<form action="/attendance/createProc" method="POST">
		<div class="form-group">
			<label for="userId">Id</label>
			<input type="userId" class="form-control" placeholder="Enter userId" id="userId">
		</div>
			<button id="btn-create" class="btn btn-primary">출결</button>
	</form>
</div>
<%@ include file="../../layout/user/footer.jsp"%>
