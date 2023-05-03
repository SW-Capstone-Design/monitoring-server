<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="identity">Id</label>
			<input type="text" value="${principal.user.identity}" class="form-control" placeholder="Enter identity"  id="identity" readonly>
		</div>
		<p id="valid_identity"></p>
        <div class="form-group">
            <label for="pwd">Password</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <p id="valid_password"></p>
        <div class="form-group">
        	<label for="name">Name</label>
        	<input type="name" value="${principal.user.name}" class="form-control" placeholder="Enter name" id="name">
        </div>
        <p id="valid_name"></p>
        <div class="form-group">
            <label for="department">Department</label>
            <input type="department" value="${principal.user.department}" class="form-control" placeholder="Enter department" id="department">
        </div>
        <p id="valid_department"></p>
		<div class="form-group">
			<label for="telephone">Telephone</label>
			<input type="telephone" value="${principal.user.telephone}" class="form-control" placeholder="Enter telephone" id="telephone">
		</div>
        <p id="valid_telephone"></p>

	</form>
	<button id="btn-update" class="btn btn-dark">수정</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/user/footer.jsp"%>
