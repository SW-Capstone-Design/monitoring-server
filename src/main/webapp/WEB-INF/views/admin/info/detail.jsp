<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
            <input type="hidden" value="${list.userId}" class="form-control" id="userId">
		<div class="form-group" style="width:402px;">
			<label for="identity">ID</label>
			<input type="text" value="${list.identity}" class="form-control" placeholder="아이디를 입력하세요." id="identity" readOnly>
		</div>
		<p id="valid_identity"></p>
		<div class="form-group" style="width:402px;">
			<label for="pwd">PW</label> <input type="password" class="form-control" placeholder="패스워드를 입력하세요." id="password">
		</div>
		<p id="valid_password"></p>
		<div class="form-group" style="width:402px;">
			<label for="name">이름</label>
			<input type="name" value="${list.name}" class="form-control" placeholder="이름을 입력하세요." id="name">
		</div>
		<p id="valid_name"></p>
		<div class="form-group" style="width:402px;">
            <label for="department">부서</label>
            <input type="department" value="${list.department}" class="form-control" placeholder="부서를 입력하세요." id="department">
        </div>
        <p id="valid_department"></p>
		<div class="form-group" style="width:402px;">
        	<label for="telephone" >전화번호</label>
        	<input type="telephone" value="${list.telephone}" class="form-control" placeholder="전화번호를 입력하세요." id="telephone">
        </div>
        <p id="valid_telephone"></p>
       <div class="form-group" style="width:402px;">
            <label for="userRoleType">보안등급</label>
            <input type="userRoleType" value="${list.userRoleType}" class="form-control" placeholder="보안등급을 입력하세요." id="userRoleType">
        <p style="color:red;">USER1, USER2, USER3</p>
       </div>
	</form>
	<button id="btn-update" class="btn btn-dark">저장</button>
	<button id="btn-del" class="btn btn-dark">삭제</button>
</div>

<script src="/js/admin.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
