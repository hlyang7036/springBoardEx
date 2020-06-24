<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
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
	</head>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//취소
			$(".cancel").on("click", function(){
				location.href="/";
				
			})
			
			$("#submit").on("click", function(){
				if ($("#userId").val()=='') {
					alert("아이디를 입력 해주세요");
					$("#userId").focus();
					return false;
				}
				
				if ($("#userPass").val()=='') {
					alert("비밀번호를 입력 해주세요");
					$("#userPass").focus();
					return false;
				}
				
				if ($("#userName").val()=='') {
					alert("이름을 입력 해주세요");
					$("#userName").focus();
					return false;
				}
				
				var idChkVal = $("#idChk").val();
					if (idChkVal=="N") {
						alert("아이디 중복확인 버튼을 눌러주세요");
						return false;
						} else if (idChkVal=="Y") {
							/* $("#regForm").submit(); */
							/* alert("아이디 중복이 확인되었습니다"); */
						}
				var userNameChkVal = $("#userNameChk").val();
					if (userNameChkVal == 'N') {
						alert("유저이름 중복확인 버튼을 눌러주세요");
						return false;
					} else if (userNameChkVal == "Y") {
						$("#regForm").submit();
					}
				
			});
			
		})
		
		function fn_idChk(){
			$.ajax({
				url : "/member/idChk",
				type : "post",
				dataType : "json",
				data : {"userId" : $("#userId").val()},
				success : function (data) {
					if (data == 1) {
						alert("중복된 아이디 입니다");
					} else if (data == 0) {
						$("#idChk").attr("value", "Y"); /* 아이디 체크값 N에서 Y로 변경함 */
						alert("사용 가능한 아이디입니다");
					}
				}
				
			})
		}
		//ajax 유저 이름 체크
		function fn_userNameChk(){
			$.ajax({
				url : "/member/userNameChk",
				type : "post",
				dataType : "json",
				data : { "userName" : $("#userName").val()},
				success : function(data){
					if (data == 1) {
						alert("중복된 유저이름입니다");
					} else if (data==0) {
						$("#userNameChk").attr("value", "Y"); /* 유저 이름 체크값 N에서 Y로 변경함 */
						alert("사용 가능한 유저이름입니다");
					}
				}
					
				
			})
		}
		
	</script>
<body>
	<div>
	<div id="container" class="center-block" >
	<section id="container" style="width: 20%" class="center-block">
		<form action="/member/register" method="post" id="regForm">
			<div class="form-group has-feedback">
				<label class="control-label" for="userId">아이디</label>
				<input class="form-control" type="text" id="userId" name="userId">
				<button class="idChk btn btn-warning" type="button" id="idChk" onclick="fn_idChk();" value="N" >중복확인</button>
			</div>
			<div class="form-group has-feedback">
				<label class="control-label" for="userName">유저 이름</label>
				<input class="form-control" type="text" id="userName" name="userName">
				<button class="userNameChk btn btn-warning" type="button" id="userNameChk" onclick="fn_userNameChk();" value="N">유저이름 중복확인</button>
			</div>			
			<div class="form-group has-feedback">
				<label class="control-label" for="userPass">패스워드</label>
				<input class="form-control" type="password" id="userPass" name="userPass">
			</div>
			<div class="form-group has feedback">
				<button class="btn btn-success" type="submit" id="submit">회원가입</button>
				<button class="cancel btn btn-info" type="button">취소</button>
			</div>
			
		</form>
	
	</section>
	</div>
	</div>
</body>
</html>