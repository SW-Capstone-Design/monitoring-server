<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>비콘정보조회</h2>
  <span>
    비콘정보를 조회할 수 있습니다.
  </span>
  <br><br>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Id</th>
        <th>UUID</th>
        <th>Name</th>
        <th>BeaconType</th>
        <th>BeaconStatus</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="beacon">
      <tr>
        <td>${beacon.beaconId}</td>
        <td>${beacon.beaconUuid}</td>
        <td>${beacon.beaconName}</td>
        <td>${beacon.beaconType}</td>
        <td>${beacon.beaconStatus}</td>
      </tr>
      </c:forEach>
    </tbody>
  </table>

</div>

<%@ include file="../../layout/admin/footer.jsp"%>
