<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container">
  <h2>출결상태조회</h2>
  <span>
    출결상태를 조회합니다.
  </span>
  <div style="display:inline-block; float:right;">
  <form style="text-align:center;" action="list" method="get">
    <input type="date" style="display:inline; width:200px;" name="searchKeyword">
    <button type="submit" class="btn btn-dark">검색</button>
  </div>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>날짜</th>
        <th>이름</th>
        <th>부서</th>
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
      			    <td colspan="7" style="text-align:center;">조회결과가 없습니다.</td>
      			</tr>
      		</c:when>
      		<c:otherwise>
                <c:forEach items="${lists.content}" var="userAttendance">
                  <tr>
                    <td>${userAttendance.attendance.date}</td>
                    <td>${userAttendance.user.name}</td>
                    <td>${userAttendance.user.department}</td>
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

  <button style="display:inline-block; float:left;" type="button" id="btn-condition" class="btn btn-dark">출결현황조회</button>

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
  	</ul>
  </form>
</div>
<script>
    let index = {
        init: function() {
                    $("#btn-condition").on("click", ()=>{
                        this.condition();
                    });
                },

                condition: function() {
                    location.href="/attendance/list/condition"
                }
        }
    index.init();
</script>

<%@ include file="../../layout/user/footer.jsp"%>