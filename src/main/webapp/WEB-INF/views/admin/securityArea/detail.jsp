<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
	        <input type="hidden" value="${principal.user.identity}" class="form-control" id="identity">
            <input type="hidden" value="${list.id}" class="form-control" id="securityAreaId">
		<div class="form-group" style="width:402px;">
			<label for="name">보안구역명</label>
			<input type="text" value="${list.name}" class="form-control" placeholder="보안구역명을 입력하세요." id="name">
		</div>
		<div class="form-group" style="width:402px;">
			<label for="description">명세</label>
			<input type="description" value="${list.description}" class="form-control" placeholder="보안구역명세를 입력하세요." id="description">
		</div>
		<div class="form-group" style="width:402px;">
			<label for="x">X좌표</label>
			<input type="x" value="${list.securityAreaLocation.x}" class="form-control" placeholder="X좌표값을 입력하세요." id="x">
		</div>
		<div class="form-group" style="width:402px;">
            <label for="y">Y좌표</label>
            <input type="y" value="${list.securityAreaLocation.y}" class="form-control" placeholder="Y좌표값을 입력하세요." id="y">
        </div>
	</form>
    <button id="btn-update" class="btn btn-dark">저장</button>
    <button id="btn-del" class="btn btn-dark">삭제</button>
</div>

<script src="/js/securityArea/securityArea.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
