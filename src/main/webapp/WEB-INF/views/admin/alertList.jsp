<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
  <h2>알림 - ${count}개</h2>
  <span>
    알림 조회 및 삭제 : 클릭하면 해당 알림이 삭제됩니다.
      <button style="display:inline-block; float:right;" type="button" id="btn-delAlertAll" class="btn btn-dark">최근알림 10개 삭제</button>
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
      <tr onclick="delAlert()">
        <input type="hidden" class="form-control" value="${alert.indexAlertId}" id="indexAlertId">
        <td width="10%">${alert.indexAlertTime}</td>
        <td>${alert.indexAlertContent}</td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</form>
<script>
        function delAlert() {
                let data = {
                    indexAlertId: $("#indexAlertId").val()
                };

                $.ajax({
                    type: "DELETE",
                    url: "/admin/alert/delete",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function(result) {
                    location.reload();
                }).fail(function(error) {
                    location.reload();
                });
        }
</script>
<script src="/js/admin.js"></script>
<%@ include file="../layout/admin/footer.jsp"%>
