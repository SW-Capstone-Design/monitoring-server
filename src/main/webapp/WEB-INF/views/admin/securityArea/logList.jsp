<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>경고조회</h2>
  <span>
    경고를 조회 : 접근자 및 해당 보안구역을 조회합니다.
  </span>
  <br><br>
  <form style="text-align:center;" action="info" method="get">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>접근시간</th>
        <th>접근자</th>
        <th>보안구역명</th>
        <th>보안구역명세</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${lists.content}" var="userSecurityArea">
      <tr>
        <td>${userSecurityArea.accessTime}</td>
        <td>${userSecurityArea.user.name}</td>
        <td>${userSecurityArea.securityArea.name}</td>
        <td>${userSecurityArea.securityArea.description}</td>
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
