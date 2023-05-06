<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>보안구역조회</h2>
  <span>
    보안구역 조회 및 수정을 합니다.
  </span>
  <br><br>
  <form style="text-align:right;" action="info" method="get">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Latitude</th>
        <th>Longitude</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="securityArea">
      <tr>
        <td>${securityArea.id}</td>
        <td>${securityArea.name}</td>
        <td>${securityArea.description}</td>
        <td>${securityArea.location.latitude}</td>
        <td>${securityArea.location.longitude}</td>
        <td><b><a href="/admin/area/info/${securityArea.id}">수정</a></b></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="../../layout/admin/footer.jsp"%>
