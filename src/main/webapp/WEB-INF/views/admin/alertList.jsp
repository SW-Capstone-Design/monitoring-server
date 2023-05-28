<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
  <h2>알림 - ${count}개</h2>
  <span>
    알림 조회 및 삭제
      <button style="display:inline-block; float:right;" type="button" id="btn-delAlertAll" class="btn btn-dark">모든알림 삭제</button>
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
        <td width="10%">${alert.indexAlertTime}</td>
        <td>${alert.indexAlertContent}</td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
    <div style="position:relative; right:35%">
    <button type="button" id="btn-delAlert" class="btn btn-dark">
       최근알림 1개 삭제
    </button>
    <button type="button" id="btn-delAlertTen" class="btn btn-dark">
       최근알림 10개 삭제
    </button>
    </div>
</div>
</form>
<script src="/js/admin.js"></script>
<%@ include file="../layout/admin/footer.jsp"%>
