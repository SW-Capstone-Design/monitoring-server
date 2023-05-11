<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
					<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
					<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" href="#">모니터링</a></li>
                            <li class="nav-item dropdown">
                               <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                  회원출결관리
                               </a>
                               <div class="dropdown-menu">
                                  <a class="dropdown-item" href="/admin/attendance/list">일자별출결관리</a>
                                  <a class="dropdown-item" href="/admin/attendance/list/tardiness">지각회원조회</a>
                                  <a class="dropdown-item" href="/admin/attendance/list/earlyLeave">조퇴회원조회</a>
                               </div>
							<li class="nav-item dropdown">
                               <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                  비콘관리
                               </a>
                               <div class="dropdown-menu">
                                  <a class="dropdown-item" href="/admin/beacon/data">비콘등록</a>
                                  <a class="dropdown-item" href="/admin/beacon/info">비콘정보관리</a>
                               </div>
                            </li>
                            <li class="nav-item dropdown">
                               <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                  보안구역관리
                               </a>
                               <div class="dropdown-menu">
                                  <a class="dropdown-item" href="/admin/area/create">보안구역정보등록</a>
                                  <a class="dropdown-item" href="/admin/area/info">보안구역정보관리</a>
                               </div>
                            </li>
        					<li class="nav-item"><a class="nav-link" href="/admin/info">회원정보관리</a></li>
        					<li class="nav-item"><a class="nav-link" href="/">사용자페이지</a></li>
        					<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
				</ul>
			</c:otherwise>
		</c:choose>
				
		</div>
	</nav>
	<br />