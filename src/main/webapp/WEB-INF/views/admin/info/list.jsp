<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>회원정보관리</h2>
  <span>
    회원정보 조회 및 수정
  </span>
  <form style="text-align:right;" action="info" method="get">
    <input type="text" style="display:inline; width:200px;" class="form-control" name="searchKeyword" placeholder="Enter Id">
    <button type="submit" class="btn btn-dark mb-1 mr-sm-1">검색</button>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Num</th>
        <th>Id</th>
        <th>Name</th>
        <th>Department</th>
        <th>Telephone</th>
        <th>CreatedAt</th>
        <th>UpdatedAt</th>
        <th>RoleType</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="user">
      <tr>
        <td>${user.userId}</td>
        <td>${user.identity}</td>
        <td>${user.name}</td>
        <td>${user.department}</td>
        <td>${user.telephone}</td>
        <td>${user.createdAt}</td>
        <td>${user.updatedAt}</td>
        <td>${user.roleType}</td>
        <td><b><a href="/admin/info/${user.userId}">수정</a></b></td>
        <td><b><a onclick='return confirm("삭제를 진행하시겠습니까?");' href="/admin/info/${user.userId}">삭제</a></b></td>
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

</div>

<%@ include file="../../layout/admin/footer.jsp"%>
