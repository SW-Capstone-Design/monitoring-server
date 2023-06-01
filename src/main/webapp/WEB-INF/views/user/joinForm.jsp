<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<div class="container">
	<form>

		<div class="form-group" style="width:402px;">
			<label for="identity">ID</label> <input type="text" class="form-control" placeholder="아이디를 입력하세요." id="identity">
		</div>
		<p id="valid_identity"></p>
		<div class="form-group" style="width:402px;">
			<label for="pwd">패스워드</label> <input type="password" class="form-control" placeholder="패스워드를 입력하세요." id="password">
		</div>
		<p id="valid_password"></p>
        <div class="form-group" style="width:402px;">
            <label for="pwd">패스워드 확인</label> <input type="password" class="form-control" placeholder="패스워드를 입력하세요." id="confirm">
        </div>
		<div class="form-group" style="width:402px;">
			<label for="name">이름</label> <input type="name" class="form-control" placeholder="이름을 입력하세요." id="name">
		</div>
		<p id="valid_name"></p>
		<div class="form-group" style="width:402px;">
            <label for="department">부서</label> <input type="department" class="form-control" placeholder="부서를 입력하세요." id="department">
        </div>
        <p id="valid_department"></p>
		<div class="form-group" style="width:402px;">
        	<label for="telephone">전화번호</label> <input type="telephone" class="form-control" placeholder="전화번호를 입력하세요." id="telephone">
        </div>
        <p id="valid_telephone"></p>
	</form>
	<button id="btn-save" class="btn btn-dark">등록</button>
	<button id="btn-back" class="btn btn-dark">뒤로가기</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/user/footer.jsp"%>
