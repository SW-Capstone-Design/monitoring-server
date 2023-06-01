<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/admin/header.jsp"%>

<div class="container">
	<form>
	    <div style="display:inline-block;">
	    <input type="hidden" value="${principal.user.identity}" class="form-control" id="identity">
		<div class="form-group" style="width:402px;">
			<label for="name">보안구역명</label>
			<input type="name" class="form-control" placeholder="보안구역명을 입력하세요." id="name">
		</div>
        <div class="form-group" style="width:402px;">
            <label for="description">명세</label>
            <input type="description" class="form-control" placeholder="보안구역명세를 입력하세요." id="description">
        </div>
		<div class="form-group" style="width:402px;">
			<label for="upperRight">A좌표</label>
			<input type="upperRight_X" class="form-control" placeholder="X좌표값을 입력하세요." id="upperRight_X">
			<input type="upperRight_Y" class="form-control" placeholder="Y좌표값을 입력하세요." id="upperRight_Y">
		</div>

		<div class="form-group" style="width:402px;">
            <label for="lowerLeft">B좌표</label>
            <input type="lowerLeft_X" class="form-control" placeholder="X좌표값을 입력하세요." id="lowerLeft_X">
            <input type="lowerLeft_Y" class="form-control" placeholder="Y좌표값을 입력하세요." id="lowerLeft_Y">
        </div>
        </div>
        <canvas style="display:inline-block; position:relative; left:7%;" id="canvas" width="220" height="315"></canvas>
	</form>
	<button id="btn-create" class="btn btn-dark">등록</button>
	<button id="btn-back" class="btn btn-dark">뒤로가기</button>
</div>
<script src="/js/securityArea/securityArea.js"></script>
<script type="application/javascript">
    function draw() {
      var canvas = document.getElementById("canvas");
      if (canvas.getContext) {
        var ctx = canvas.getContext("2d");

        ctx.fillStyle = "rgb(200,0,0)";
        ctx.fillRect (185, 10, 25, 25);
        ctx.font = "normal bold 25px Arial";
        ctx.strokeStyle = 'white';
        ctx.strokeText("A", 189, 31);



        ctx.fillStyle = "rgba(0, 0, 200, 0.5)";
        ctx.fillRect (10, 285, 25, 25);
        ctx.strokeText("B", 14, 306);

        ctx.strokeStyle = 'black';
        ctx.strokeRect(10, 10, 200, 300);
      }
    }
  </script>
<%@ include file="../../layout/admin/footer.jsp"%>
