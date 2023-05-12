<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
	    <input type="hidden" value="${principal.user.identity}" class="form-control" id="identity">
		<div class="form-group">
			<label for="name">보안구역명</label>
			<input type="name" class="form-control" placeholder="Enter name" id="name">
		</div>
        <div class="form-group">
            <label for="description">명세</label>
            <input type="description" class="form-control" placeholder="Enter description" id="description">
        </div>
		<div class="form-group">
			<label for="latitude">위도</label>
			<input type="latitude" class="form-control" placeholder="Enter latitude" id="latitude">
		</div>

		<div class="form-group">
            <label for="longitude">경도</label>
            <input type="longitude" class="form-control" placeholder="Enter longitude" id="longitude">
        </div>
	</form>
	<button id="btn-create" class="btn btn-dark">등록</button>
	<button id="btn-back" class="btn btn-dark">뒤로가기</button>
</div>

<script src="/js/securityArea/securityArea.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
