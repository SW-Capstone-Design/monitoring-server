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

    <ul class="pagination justify-content-center">
      	<c:choose>
      		<c:when test="${lists.first}">
      			<li class="page-item disabled"><a class="page-link" href="?page=${lists.number-1}">Previous</a></li>
      		</c:when>
      		<c:otherwise>
      			<li class="page-item"><a class="page-link" href="?page=${user.number-1}">Previous</a></li>
      		</c:otherwise>
      	</c:choose>

      	<c:forEach var="i" begin="1" end="${lists.totalPages}">
      	<c:choose>
          		<c:when test="${!empty param.searchKeyword}">
          		    <li class="page-item"><a class="page-link" href="?page=${i-1}&searchKeyword=${param.searchKeyword}">${i}</a></li>
          		</c:when>
          		<c:otherwise>
          	        <li class="page-item"><a class="page-link" href="?page=${i-1}">${i}</a></li>
          		</c:otherwise>
          	</c:choose>
        </c:forEach>

      	<c:choose>
      		<c:when test="${lists.last}">
      			<li class="page-item disabled"><a class="page-link" href="?page=${lists.number+1}">Next</a></li>
      		</c:when>
      		<c:otherwise>
      			<li class="page-item"><a class="page-link" href="?page=${lists.number+1}">Next</a></li>
      		</c:otherwise>
      	</c:choose>
    </form>
</div>

<%@ include file="../../layout/admin/footer.jsp"%>
