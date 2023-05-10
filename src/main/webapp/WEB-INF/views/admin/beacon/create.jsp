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
		    <label for="beaconName">BeaconName</label>
		    <input type="beaconName" id="beaconName" class="form-control" placeholder="Enter name"/>
		</div>
        <div class="form-group">
        <label for="uuid">UUID</label>
            <input type="uuid" id="uuid" class="form-control" placeholder="Enter uuid" />
        </div>
        <div class="form-group">
        <label for="major">Major</label>
            <input type="major" id="major" class="form-control" placeholder="Enter major" />
        </div>
        <div class="form-group">
        <label for="minor">Minor</label>
            <input type="minor" id="minor" class="form-control" placeholder="Enter minor" />
        </div>
  </form>
    <button id="btn-create" class="btn btn-dark">전송</button>
</div>

<script src="/js/beacon/beacon.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>
