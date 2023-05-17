<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>회원정보관리</h2>
  <span>
    회원정보 조회 및 수정
  </span>
  <form style="text-align:right;" action="info" method="get">
  <div style="display:inline-block; float:right;">
    <input type="text" style="display:inline; width:200px;" class="form-control" name="searchKeyword" placeholder="Enter Id">
    <button type="submit" class="btn btn-dark mb-1 mr-sm-1">검색</button>
  </div>
  <form>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>회원 ID</th>
        <th>로그인 ID</th>
        <th>이름</th>
        <th>부서</th>
        <th>전화번호</th>
        <th>생성일자</th>
        <th>갱신일자</th>
        <th>보안등급</th>
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
      </tr>
      </c:forEach>
    </tbody>
  </table>
  <button style="display:inline-block; float:left;" type="button" id="btn-joinForm" class="btn btn-dark">회원등록</button>

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

<script>
    let index = {
        init: function() {
                    $("#btn-joinForm").on("click", ()=>{
                        this.joinForm();
                    });
                },

                joinForm: function() {
                    location.href="/admin/joinForm"
                }
        }
    index.init();
</script>

<%@ include file="../../layout/admin/footer.jsp"%>
