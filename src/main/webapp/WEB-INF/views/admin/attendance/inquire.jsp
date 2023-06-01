<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>회원출결관리</h2>
  <span>
    출결상태를 조회 및 수정합니다.
  </span>
  <form style="text-align:center;" action="list" method="get">
  <div style="display:inline-block; float:right;">
    <input type="date" style="display:inline; width:200px;" class="form-control" name="searchKeyword">
    <button type="submit" class="btn btn-dark mb-1 mr-sm-1">검색</button>
  </div>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>날짜</th>
        <th>ID</th>
        <th>이름</th>
        <th>부서</th>
        <th>등급</th>
        <th>출근시간</th>
        <th>퇴근시간</th>
        <th>지각여부</th>
        <th>조퇴여부</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
            <c:when test="${empty lists.content}">
                <tr>
                    <td colspan="10" style="text-align:center;">조회결과가 없습니다.</td>
                </tr>
            </c:when>
            <c:otherwise>
            <c:forEach items="${lists.content}" var="userAttendance">
              <tr>
                <td>${userAttendance.attendance.date}</td>
                <td>${userAttendance.user.identity}</td>
                <td>${userAttendance.user.name}</td>
                <td>${userAttendance.user.department}</td>
                <td>${userAttendance.user.userRoleType}</td>
                <td>${userAttendance.attendance.enterTime}</td>
                <td>${userAttendance.attendance.leaveTime}</td>
                <td>${userAttendance.attendance.goWork}</td>
                <td>${userAttendance.attendance.leaveWork}</td>
                <td><b><a href="/admin/attendance/list/${userAttendance.user.userId}/${userAttendance.attendance.date}">수정</a></b></td>
              </tr>
              </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
  </table>

  <div class="btn-group" style="float:left">
    <button type="button" class="btn btn-dark dropdown-toggle" data-toggle="dropdown">
       유형별 조회
    </button>
    <div class="dropdown-menu">
      <a class="dropdown-item" href="/admin/attendance/list/tardiness">지각회원조회</a>
      <a class="dropdown-item" href="/admin/attendance/list/earlyLeave">조퇴회원조회</a>
    </div>
  </div>

  <ul class="pagination" style="position:relative; left:35%">
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

<script src="/js/attendance/attendance.js"></script>
<%@ include file="../../layout/admin/footer.jsp"%>