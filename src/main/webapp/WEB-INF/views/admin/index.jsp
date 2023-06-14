<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/admin/header.jsp"%>

<div class="container">
    <div>
        <br>
        <input type="hidden" value="${userCounts}" class="form-control" id="userCounts">
        <input type="hidden" value="${beaconCounts}" class="form-control" id="beaconCounts">
        <input type="hidden" value="${securityAreaCounts}" class="form-control" id="securityAreaCounts">
        <input type="hidden" value="${indexNotificationCounts}" class="form-control" id="indexNotificationCounts">

        <input type="hidden" value="${day1}" class="form-control" id="day1">
        <input type="hidden" value="${day2}" class="form-control" id="day2">
        <input type="hidden" value="${day3}" class="form-control" id="day3">

        <h1>관리자 페이지</h1>
        <br>
        <div>
          <canvas id="myChart" style="display:inline-block;" width=500 height=400></canvas>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <canvas id="lineChart" style="display:inline-block;" width=500 height=400></canvas>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <script>
          let today = new Date();
          let year = today.getFullYear();
          let month = today.getMonth() + 1;
          let date = today.getDate();

          const ctx = document.getElementById('myChart');
          const line = document.getElementById('lineChart');
          var userCounts = $("#userCounts").val();
          var beaconCounts = $("#beaconCounts").val();
          var securityAreaCounts = $("#securityAreaCounts").val();
          var indexNotificationCounts = $("#indexNotificationCounts").val();

          new Chart(ctx, {
            type: 'bar',
            data: {
              labels: ['회원', '비콘', '보안구역', '알림'],
              datasets: [{
                label: '데이터',
                data: [userCounts, beaconCounts, securityAreaCounts, indexNotificationCounts],
                borderWidth: 1,
                backgroundColor: 'rgba(0, 255, 0, 0.4)'
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
           new Chart(line, {
                  type: 'line',
                  data: {
                    labels: [year+"/"+month+"/"+(date-2), year+"/"+month+"/"+(date-1), year+"/"+month+"/"+date],
                    datasets: [{
                      label: '경고',
                      data: [day1, day2, day3],
                      borderWidth: 1
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
        <br>
    </div>
</div>

<%@ include file="../layout/admin/footer.jsp"%>
