<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<div class="container" >
	<form action="/auth/loginProc" method="POST">
		<div class="form-group" style="width:402px;">
			<label for="identity">ID</label>
			<input type="text" name="identity" class="form-control" placeholder="아이디를 입력하세요." id="identity">
		</div>
		<div class="form-group" style="width:402px;">
			<label for="pwd">패스워드</label>
			<input type="password" name="password" class="form-control" placeholder="패스워드를 입력하세요." id="password">
		</div>
			<button id="btn-login" class="btn btn-dark">로그인</button>
	</form>

</div>
<%@ include file="../layout/user/footer.jsp"%>
