<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
  <h2>알림 - ${count}개</h2>
  <span>
    알림 조회 및 삭제
  </span>
  <br><br>
  <form style="text-align:center;" action="info" method="get">
  <div style="display:inline-block; float:right;">
  </div>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th width="15%">시간</th>
        <th>내용</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${alerts.content}" var="alert">
      <tr>
        <input type="hidden" class="form-control" value="${alert.indexAlertId}" id="indexAlertId">
        <td width="10%">${alert.indexAlertTime}</td>
        <td>${alert.indexAlertContent}</td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
  <button style="display:inline-block; float:left;" type="button" id="btn-delAlertAll" class="btn btn-dark">최근알림 10개 삭제</button>

  <ul class="pagination" style="position:relative; left:35%">
  	<c:choose>
  		<c:when test="${alerts.first}">
  			<li class="page-item disabled"><a class="page-link" href="?page=${alerts.number-1}">Previous</a></li>
  		</c:when>
  		<c:otherwise>
  			<li class="page-item"><a class="page-link" href="?page=${alerts.number-1}">Previous</a></li>
  		</c:otherwise>
  	</c:choose>

  	<c:forEach var="i" begin="1" end="${alerts.totalPages}">
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
  		<c:when test="${alerts.last}">
  			<li class="page-item disabled"><a class="page-link" href="?page=${alerts.number+1}">Next</a></li>
  		</c:when>
  		<c:otherwise>
  			<li class="page-item"><a class="page-link" href="?page=${alerts.number+1}">Next</a></li>
  		</c:otherwise>
  	</c:choose>

</div>
</form>

<script src="/js/admin.js"></script>
<%@ include file="../layout/admin/footer.jsp"%>
