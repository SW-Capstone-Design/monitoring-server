<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container" id="pack">
  <h2>모니터링</h2>
  <span>
    회원의 보안구역 접근 여부를 모니터링합니다.
  </span>
  <br><br>
    <div class="panael panel-primary">
        Alert TimeLine
    </div>
</div>
<script>
    $(document).ready(function() {

        var urlEndPoint = 'http://localhost:8080/auth/subscribe';
        var eventSource = new EventSource(urlEndPoint);

        eventSource.addEventListener("latest", function(event){
            var articleData = JSON.parse(event.data);
            addBlock(articleData.title, articleData.text);
            })
        })

        function addBlock(title, text){
        var a = document.createElement("article");
        var h = document.createElement("H3");
        var t = document.createTextNode(title);
        h.appendChild(t);

        var para = document.createElement("P");
        para.innerHTML = text;
        a.appendChild(h);
        a.appendChild(para);
        document.getElementById("pack").appendChild(a);
        }

</script>
<%@ include file="../../layout/admin/footer.jsp"%>
