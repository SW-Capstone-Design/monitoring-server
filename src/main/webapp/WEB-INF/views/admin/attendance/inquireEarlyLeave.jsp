<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>조퇴회원조회</h2>
  <span>
    금일 혹은 특정날짜의 조퇴한 회원을 조회합니다.

  </span>
  <form style="text-align:center;" action="earlyLeave" method="get">
  <div style="display:inline-block; float:right;">
    <input type="date" style="display:inline-block;width:200px;" class="form-control" name="searchKeyword">
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
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty lists.content}">
            <tr>
                <td colspan="9" style="text-align:center;">조회결과가 없습니다.</td>
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
          </tr>
          </c:forEach>
          </c:otherwise>
      </c:choose>
    </tbody>
  </table>

<button style="display:inline-block; float:left;" type="button" id="btn-back" class="btn btn-dark">뒤로가기</button>

  <ul class="pagination" style="position:relative; left:35%">
  	<c:choose>
  		<c:when test="${lists.first}">
  			<li class="page-item disabled"><a class="page-link" href="?page=${lists.number-1}">Previous</a></li>
  		</c:when>
  		<c:otherwise>
  			<li class="page-item"><a class="page-link" href="?page=${lists.number-1}">Previous</a></li>
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

<script>
    let index = {
        init: function() {
                    $("#btn-back").on("click", ()=>{
                        this.back();
                    });
                },

                back: function() {
                    window.history.back();
                }
        }
    index.init();
</script>

<%@ include file="../../layout/admin/footer.jsp"%>