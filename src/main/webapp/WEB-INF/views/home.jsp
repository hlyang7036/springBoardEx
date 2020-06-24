<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
	<title>Home</title>

	<meta charset="UTF-8">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<style type="text/css">
		input {
			width: 100%;
			padding: 10px 20px;
			margin: 5px 0;
			box-sizing: border-box;
		}
		input[type="text"] {
			border: solid 2px #D2691E;
			border-radius: 8px;
		}
		input[type="password"] {
			border: none;
			border-bottom: solid 2px #D2691E;
		}
		</style>	
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 
</head>

<script type="text/javascript">
	$(document).ready(function(){
		$("#logoutBtn").on("click", function(){
			location.href="member/logout";
		})
		// 회원가입 페이지 이동
		$("#registerBtn").on("click", function(){
			location.href="member/register";
		}) 
		$("#memberUpdateBtn").on("click", function(){
			location.href="member/memberUpdateView";
		})
	})
</script>
<div class="container center-block">
	<div align="center">
		<h3 class="display-4 font-weight-bold"><a href="/board/list" >게시판 바로가기</a></h3>
	</div>
	<br/>
</div>
<body >
	<div class="container center-block">
	<section id="container" style="width: 20%" class="center-block">
		<div class="row">
			<div class="span12">
				<form action="/member/login" method="post" name='homeForm' class="form-horizontal">
					<fieldset>
					<div id="legend">
						<legend>login</legend>		
					</div>
					<c:if test="${member == null}">
						<div class="control-group">
							<label for="userId" class="control-label">userId</label>
							<div class="controls">
								<input type="text" id="userId" name="userId"  class="input-xlarge">
							</div>
						</div>
						<div>
							<label for="userId" class="control-label">pass</label>
							<div class="controls">
								<input type="password" id="userPass" name="userPass" placeholder="" class="input-xlarge">
							</div>
						</div>
						<br>
						<div class="control-group">
							<div class="controls">
								<button type="submit" class="btn btn-success">로그인</button>
								<button id="registerBtn" type="button" class="btn btn-success">회원가입</button>
							</div>
						</div>
						<br>
					</c:if>
					<c:if test="${member != null }">
						<div>
							<p>${member.userId}님 환영합니다</p>
							<button id="memberUpdateBtn" type="button" class="btn btn-info">회원정보 수정</button>
							<button id="logoutBtn" type="button" class="btn btn-warning">로그아웃</button>
						</div>
					</c:if>
					<c:if test="${msg == false}">
						<p style="color : red">로그인 실패! 아이디와 비밀번호를 확인 해 주세요.</p>
					</c:if>
					</fieldset>
				</form>
			</div>
		</div>
	</section>
	</div>
</body>
</html>
