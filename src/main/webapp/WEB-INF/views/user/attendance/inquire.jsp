<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../../layout/user/header.jsp"%>

<div class="container">
  <h2>출결조회</h2>
  <span>
    출근 및 결근 상태를 조회할 수 있습니다.
  </span>
  <br><br>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Department</th>
        <th>RoleType</th>
        <th>EnterTime</th>
        <th>LeaveTime</th>
        <th>GoWorkType</th>
        <th>LeaveWorkType</th>
        <th>Date</th>
        <th>AttendanceDays</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="attendance">
      <tr>
        <td>${attendance.identity}</td>
        <td>${attendance.name}</td>
        <td>${attendance.department}</td>
        <td>${attendance.roleType}</td>
        <td>${attendance.enterTime}</td>
        <td>${attendance.leaveTime}</td>
        <td>${attendance.goWorkType}</td>
        <td>${attendance.leaveWorkType}</td>
        <td>${attendance.date}</td>
        <td>${attendance.attendanceDays}</td>
      </tr>
      </c:forEach>
    </tbody>
  </table>

</div>

<%@ include file="../../layout/user/footer.jsp"%>
