<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>출결상태조회</h2>
  <span>
    출결상태 조회 및 수정
  </span>
  <form style="text-align:right;" action="list" method="get">
    <input type="date" name="searchKeyword">
    <button type="submit" class="btn btn-primary">검색</button>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Date</th>
        <th>Id</th>
        <th>Name</th>
        <th>Department</th>
        <th>RoleType</th>
        <th>EnterTime</th>
        <th>LeaveTime</th>
        <th>GoWork</th>
        <th>LeaveWork</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="userAttendance">
      <tr>
        <td>${userAttendance.attendance.date}</td>
        <td>${userAttendance.user.identity}</td>
        <td>${userAttendance.user.name}</td>
        <td>${userAttendance.user.department}</td>
        <td>${userAttendance.user.roleType}</td>
        <td>${userAttendance.attendance.enterTime}</td>
        <td>${userAttendance.attendance.leaveTime}</td>
        <td>${userAttendance.attendance.goWork}</td>
        <td>${userAttendance.attendance.leaveWork}</td>
        <td><a href="/admin/attendance/list/${userAttendance.user.userId}">수정</a></td>
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