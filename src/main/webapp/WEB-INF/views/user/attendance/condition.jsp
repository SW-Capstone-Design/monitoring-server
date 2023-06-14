<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container">
<h2>출결현황조회</h2>
<span>
    출결상태 현황을 조회합니다.
    <br><br>
<hr>
</span>
<div style="display:inline-block;">
	<form>
		<div class="form-group" style="width:402px;">
			<label for="work">정상출퇴근</label>
			<input type="text" value="${work}" class="form-control" id="work" readonly>
		</div>
        <div class="form-group" style="width:402px;">
            <label for="tardiness">지각</label>
            <input type="text" value="${tardiness}" class="form-control" id="tardiness" readonly>
        </div>
        <div class="form-group" style="width:402px;">
            <label for="earlyLeave">조퇴</label>
            <input type="text" value="${earlyLeave}" class="form-control" id="earlyLeave" readonly>
        </div>
        <div class="form-group" style="width:402px;">
            <label for="absent">결근</label>
            <input type="text" value="${absent}" class="form-control" id="absent" readonly>
        </div>
	</form>
	<button id="btn-back" class="btn btn-dark">뒤로가기</button>
</div>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div style="display:inline-block;">
   <canvas id="myChart" style="display:inline-block;" width=500 height=400></canvas>
</div>
</div>

<script>
    let index = {
        init: function() {
                    $("#btn-back").on("click", ()=>{
                        this.back();
                    });
                },

                back: function() {
                    window.history.back();
                }
        }
    index.init();
</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
const ctx = document.getElementById('myChart');
          var work = $("#work").val();
          var tardiness = $("#tardiness").val();
          var earlyLeave = $("#earlyLeave").val();
          var absent = $("#absent").val();

          new Chart(ctx, {
            type: 'bar',
            data: {
              labels: ['정상출퇴근', '지각', '조퇴', '결근'],
              datasets: [{
                label: '데이터',
                data: [work, tardiness, earlyLeave, absent],
                borderWidth: 1,
                backgroundColor: 'rgba(255, 99, 132, 0.4)'
              }]
            },
            options: {
              responsive: false,
              scales: {
                y: {
                  title: {
                       display: true,
                       text: '개수'
                  },
                  beginAtZero: true,
                  ticks: {
                      stepSize: 1
                  }
                }
              }
            }
          });
</script>
<%@ include file="../../layout/user/footer.jsp"%>
