<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>비콘등록</h2>
  <span>
    비콘데이터를 등록할 수 있습니다.
  </span>
  <br><br>
    <form>
        <input type="hidden" id="beaconId" class="form-control" value="${lists.beaconId}" readonly/>
		<div class="form-group">
		    <label for="beaconName">비콘명</label>
		    <input type="beaconName" id="beaconName" class="form-control" placeholder="Enter name" value="${lists.beaconName}"/>
		</div>
        <div class="form-group">
        <label for="uuid">UUID</label>
            <input type="uuid" id="uuid" class="form-control" placeholder="Enter uuid" value="${lists.uuid}"/>
        </div>
        <div class="form-group">
        <label for="major">Major</label>
            <input type="major" id="major" class="form-control" placeholder="Enter major" value="${lists.major}"/>
        </div>
        <div class="form-group">
        <label for="minor">Minor</label>
            <input type="minor" id="minor" class="form-control" placeholder="Enter minor" value="${lists.minor}"/>
        </div>
        <div class="form-group">
        <label for="x">X좌표</label>
            <input type="x" id="x" class="form-control" value="${lists.location.x}" />
        </div>
        <div class="form-group">
        <label for="y">Y좌표</label>
            <input type="y" id="y" class="form-control" value="${lists.location.y}" />
        </div>
        <div class="form-group">
        <label for="battery">배터리잔량</label>
            <input type="battery" id="battery" class="form-control" value="${lists.battery}" readonly/>
        </div>
        <div class="form-group">
        <label for="beaconRole">용도</label>
            <input type="beaconRole" id="beaconRole" class="form-control" value="${lists.beaconRole}"/>
        </div>
  </form>
    <button id="btn-update" class="btn btn-dark">수정</button>
    <button id="btn-del" class="btn btn-dark">삭제</button>
</div>

<script src="/js/beacon/beacon.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
