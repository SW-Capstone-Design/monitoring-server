<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>비콘정보관리</h2>
  <span>
    비콘정보 조회 및 수정
  </span>
  <br><br>
  <form style="text-align:right;" action="info" method="get">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Id</th>
        <th>UUID</th>
        <th>Major</th>
        <th>Minor</th>
        <th>RSSI</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="beacon">
      <tr>
        <td>${beacon.beaconId}</td>
        <td>${beacon.uuid}</td>
        <td>${beacon.major}</td>
        <td>${beacon.minor}</td>
        <td>${beacon.rssi}</td>
        <td><a href="/admin/beacon/info/${beacon.uuid}">삭제</a></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="../../layout/admin/footer.jsp"%>
