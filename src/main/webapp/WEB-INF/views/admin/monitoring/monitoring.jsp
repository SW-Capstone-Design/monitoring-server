<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
  <h2>모니터링</h2>
  <span>
    회원의 보안구역 접근 여부를 모니터링합니다.
  </span>
  <br><br>
    <div class="panael panel-primary">
        <textarea id="pack"></textarea>

    </div>
</div>
<script>
    $(document).ready(function() {
        var urlEndPoint = '/auth/subscribe';
        var eventSource = new EventSource(urlEndPoint);

        eventSource.addEventListener("latest", function(event){
            var articleData = JSON.parse(event.data);
            addBlock(articleData.title, articleData.text);
            })
        })

        function addBlock(title, text){
        var textarea_str = $("#pack").val();
        textarea_str = textarea_str + text + "\n";
        document.getElementById("pack").value = textarea_str;

        var vScrollDown = $("#pack").prop('scrollHeight');
        $("#pack").scrollTop(vScrollDown);
        }

</script>
<%@ include file="../../layout/admin/footer.jsp"%>
