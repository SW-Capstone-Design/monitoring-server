<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>보안구역조회</h2>
  <span>
    보안구역 조회 및 수정을 합니다.
  </span>
  <br><br>
  <form style="text-align:center;" action="info" method="get">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>보안구역 ID</th>
        <th>보안구역명</th>
        <th>명세</th>
        <th>위도</th>
        <th>경도</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty lists.content}">
            <tr>
                <td colspan="6" style="text-align:center;">조회결과가 없습니다.</td>
            </tr>
        </c:when>
    <c:otherwise>
        <c:forEach items="${lists.content}" var="securityArea">
          <tr>
            <td>${securityArea.id}</td>
            <td>${securityArea.name}</td>
            <td>${securityArea.description}</td>
            <td>${securityArea.securityAreaLocation.x}</td>
            <td>${securityArea.securityAreaLocation.y}</td>
            <td><b><a href="/admin/area/info/${securityArea.id}">수정</a></b></td>
          </tr>
          </c:forEach>
          </c:otherwise>
      </c:choose>
    </tbody>
  </table>
  <button style="display:inline-block; float:left;" type="button" id="btn-createForm" class="btn btn-dark">보안구역등록</button>
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
                    $("#btn-createForm").on("click", ()=>{
                        this.createForm();
                    });
                },

                createForm: function() {
                    location.href="/admin/area/create"
                }
        }
    index.init();
</script>


<%@ include file="../../layout/admin/footer.jsp"%>
