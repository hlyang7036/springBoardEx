<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<!-- 합쳐지고 최소화된 최신 CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- 부가적인 테마 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	 	
	 	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		
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
		
		<title>회원탈퇴</title>
	</head>
	<script type="text/javascript">
		$(document).ready(function(){
			
			//취소
			$(".cancel").on("click", function(){
				location.href="/";
				
			})
			
			$("#submit").on("click", function(){
				if ($("#userPass").val()=='') {
					alert("비밀번호를 입력 해주세요");
					$("#userPass").focus();
					return false;
				}
				$.ajax({
					url:"/member/passChk",
					type: "POST",
					async:false,
					dataType: "json",
					data: $("#delForm").serializeArray(),
					success: function(data){
						if (data==false) {
							alert("패스워드가 틀렸습니다");
							return;
						} else {
							if (confirm("회원 탈퇴를 진행하시겠습니까?")) {
								/* $("#submit").attr('type','submit'); */
								$("#delForm").submit();
							}
						} 
					},
					 error:function(request,status,error){
					        alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
					       }
				})
			});
			
		})
	</script>
<body>
	<div id="container" class="center-block">
	<br/>
	<section id="container" style="width: 20%" class="center-block">
		<form action="/member/memberDelete" method="post" id="delForm">
			<div class="form-group has-feedback">
				<label class="control-label" for="userId">아이디</label>
				<input class="form-control" type="text" id="userId" name="userId" value="${member.userId}" readonly="readonly">
			</div>
			<div class="form-group has-feedback">
				<label class="control-label" for="userName">이름</label>
				<input class="form-control" type="text" id="userName" name="userName" value="${member.userName}" readonly="readonly">
			</div>
			<div class="form-group has-feedback">
				<label class="control-label" for="userPass">패스워드</label>
				<input class="form-control" type="password" id="userPass" name="userPass">
			</div>
		</form>
			<div class="form-group has feedback">
				<button class="btn btn-success" type="button" id="submit">회원 탈퇴</button>
				<button class="cancel btn btn-danger" type="button">취소</button>
			</div>
		<div>
			<c:if test="${msg==false}">
				비밀번호가 잘못 됐습니다
			</c:if>
		</div>
	</section>
	</div>
</body>
</html>