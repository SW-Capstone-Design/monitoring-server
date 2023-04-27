<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container" >
	<form>
		<div class="form-group">
			<label for="userId">Id</label>
			<input type="userId" class="form-control" placeholder="Enter userId" id="userId">
		</div>
			<button id="btn-register" class="btn btn-primary">출결</button>
	</form>
</div>

<script src="/js/attendance/register.js"></script>
<%@ include file="../../layout/user/footer.jsp"%>
