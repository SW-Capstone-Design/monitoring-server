<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<title>팀투 통합관제시스템</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
textarea {
  width: 100%;
  height: 400px;
  padding: 12px 20px;
  box-sizing: border-box;
  border: 2px solid #ccc;
  border-radius: 4px;
  background-color: #f8f8f8;
  font-size: 20px;
  resize: none;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/admin/index">통합관제시스템</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
		
		<c:choose>
			<c:when test="${empty principal}" >
				<ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link">로그인</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul id="result" class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" href="/admin/monitoring">모니터링</a></li>
				            <li class="nav-item"><a class="nav-link" href="/admin/area/accessInfo">경고조회</a></li>
                            <li class="nav-item"><a class="nav-link" href="/admin/attendance/list">출결관리</a></li>
							<li class="nav-item"><a class="nav-link" href="/admin/beacon/info">비콘관리</a></li>
        					<li class="nav-item"><a class="nav-link" href="/admin/area/info">보안구역관리</a></li>
        					<li class="nav-item"><a class="nav-link" href="/admin/info">회원관리</a></li>
        					<li class="nav-item"><a class="nav-link" href="/">사용자페이지</a></li>
        					<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
        					<!-- Trigger the modal with a button -->
        					<div style="position:relative; left:90%;">
        					<c:choose>
                                <c:when test="${empty alerts.content}" >
                                <div>
                                    <button type="button" style="width:75px;" class="btn btn-secondary btn-lg" data-toggle="modal" data-target="#myModal">🔔</button>
                                </div>
                                </c:when>
                                <c:otherwise>
                                <div>
                                    <button type="button" style="width:75px;" class="btn btn-secondary btn-lg" data-toggle="modal" data-target="#myModal">🔔❗</button>
                                </div>
                                </c:otherwise>
                            </c:choose>
                            </div>
                            <!-- Modal -->
                            <div id="myModal" class="modal fade" role="dialog">
                              <div class="modal-dialog">

                                <!-- Modal content-->
                                <div style="width:1000px;" class="modal-content">
                                  <div class="modal-header">
                                    <h5 class="modal-title">알림 - ${count}개</h5>
                                    <a href="/admin/alert">자세히</a>
                                  </div>
                                  <div id="list" class="modal-body">
                                    <form style="text-align:center;">
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
                                      </form>
                                  </div>
                                  <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                  </div>
                                </div>
                              </div>
                            </div>
				</ul>
			</c:otherwise>
		</c:choose>
				
		</div>
	</nav>
	<br />