<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>비콘등록</h2>
  <span>
    비콘데이터를 등록할 수 있습니다.
  </span>
  <br><br>
    <form>
		<div class="form-group">
		    <label for="beaconName">비콘명</label>
		    <input type="beaconName" id="beaconName" class="form-control" placeholder="비콘명을 입력하세요."/>
		</div>
        <div class="form-group">
        <label for="uuid">UUID</label>
            <input type="uuid" id="uuid" class="form-control" placeholder="UUID를 입력하세요." />
        </div>
        <div class="form-group">
        <label for="major">Major</label>
            <input type="major" id="major" class="form-control" placeholder="Major를 입력하세요." />
        </div>
        <div class="form-group">
        <label for="minor">Minor</label>
            <input type="minor" id="minor" class="form-control" placeholder="Minor를 입력하세요." />
        </div>
        <div class="form-group">
        <label for="x">X좌표</label>
            <input type="x" id="x" class="form-control" placeholder="X좌표를 입력하세요." />
        </div>
        <div class="form-group">
        <label for="y">Y좌표</label>
            <input type="y" id="y" class="form-control" placeholder="Y좌표를 입력하세요." />
        </div>
        <div class="form-group">
        <label for="beaconRole">용도</label>
            <input type="beaconRole" id="beaconRole" class="form-control" placeholder="용도를 입력하세요." />
            <p style="color:red;">ATTENDANCE, LOCATION</p>
        </div>
  </form>
    <button id="btn-create" class="btn btn-dark">등록</button>
    <button id="btn-back" class="btn btn-dark">뒤로가기</button>
</div>

<script src="/js/beacon/beacon.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
