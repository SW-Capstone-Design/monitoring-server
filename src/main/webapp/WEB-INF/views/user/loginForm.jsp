<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<div class="container" >
	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="identity">Id</label>
			<input type="text" name="identity" class="form-control" placeholder="Enter identity" id="identity">
		</div>
		<div class="form-group">
			<label for="pwd">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
			<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>

</div>
<%@ include file="../layout/user/footer.jsp"%>
