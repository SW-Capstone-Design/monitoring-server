<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>회원정보관리</h2>
  <span>
    회원정보 조회 및 수정
  </span>
  <form style="text-align:right;" action="info" method="get">
    <input type="text" name="searchKeyword" placeholder="Enter Id">
    <button type="submit" class="btn btn-primary">검색</button>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Num</th>
        <th>Id</th>
        <th>Name</th>
        <th>Department</th>
        <th>Phone</th>
        <th>Created_at</th>
        <th>Updated_at</th>
        <th>Role_type</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="users">
      <tr>
        <td>${users.usersId}</td>
        <td>${users.identity}</td>
        <td>${users.name}</td>
        <td>${users.department}</td>
        <td>${users.phone}</td>
        <td>${users.created_at}</td>
        <td>${users.updated_at}</td>
        <td>${users.role_type}</td>
        <td><a href="/admin/info/${users.usersId}">수정</a></td>
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
  			<li class="page-item"><a class="page-link" href="?page=${users.number-1}">Previous</a></li>
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
