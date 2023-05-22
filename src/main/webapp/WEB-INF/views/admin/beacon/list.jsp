<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>비콘정보관리</h2>
  <span>
    비콘정보 조회 및 수정을 합니다.
  </span>
  <br><br>
  <form style="text-align:center;" action="info" method="get">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>비콘 ID</th>
        <th>비콘명</th>
        <th>UUID</th>
        <th>Major</th>
        <th>Minor</th>
        <th>배터리잔량</th>
        <th>용도</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="beacon">
      <tr>
        <td>${beacon.beaconId}</td>
        <td>${beacon.beaconName}</td>
        <td>${beacon.uuid}</td>
        <td>${beacon.major}</td>
        <td>${beacon.minor}</td>
        <td>${beacon.battery} %</td>
        <th>${beacon.beaconRole}</th>
        <td><b><a href="/admin/beacon/info/${beacon.beaconId}">수정</a></b></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>

  <button style="display:inline-block; float:left;" type="button" id="btn-createForm" class="btn btn-dark">비콘등록</button>

  <ul class="pagination" style="location:relative; left:35%">
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

<script>
    let index = {
        init: function() {
                    $("#btn-createForm").on("click", ()=>{
                        this.createForm();
                    });
                },
                createForm: function() {
                    location.href="/admin/beacon/data";
                }
        }
    index.init();
</script>

<%@ include file="../../layout/admin/footer.jsp"%>
