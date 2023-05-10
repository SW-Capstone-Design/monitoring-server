<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
	    <input type="hidden" value="${principal.user.identity}" class="form-control" id="identity">
		<div class="form-group">
			<label for="name">Security_Area name</label>
			<input type="name" class="form-control" placeholder="Enter name" id="name">
		</div>
        <div class="form-group">
            <label for="description">Description</label>
            <input type="description" class="form-control" placeholder="Enter description" id="description">
        </div>
		<div class="form-group">
			<label for="latitude">Latitude</label>
			<input type="latitude" class="form-control" placeholder="Enter latitude" id="latitude">
		</div>

		<div class="form-group">
            <label for="longitude">Longitude</label>
            <input type="longitude" class="form-control" placeholder="Enter longitude" id="longitude">
        </div>
	</form>
	<button id="btn-create" class="btn btn-dark">등록</button>
</div>

<script src="/js/securityArea/securityArea.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
