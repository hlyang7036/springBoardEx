<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<meta charset="UTF-8">
	<title>게시판</title>
	
</head>
	<script type="text/javascript">
		$(document).ready(function(){
			var formObj = $("form[name='updateForm']");
			
			$(".cancel_btn").on("click", function(){
				location.href = "/board/readView?bno=${replyUpdate.bno}"
						+ "&page=${scri.page}"
						+ "&perPageNum=${scri.perPageNum}"
						+ "&searchType=${scri.searchType}"
						+ "&keyword=${scri.keyword}";
			})
		})
	</script>
<body>
	<div id="container" class="center-block">
		<header>
			<h1>게시판</h1>
		</header>
		<hr/>
			<div>
				<%@include file="nav.jsp" %>
			</div>
		<section id="container" class="center-block">
		<div>
			<form name="updateForm" role="form" method="post" action="/board/replyUpdate">
				<input type="hidden" id="bno" name="bno" value="${replyUpdate.bno}" readonly="readonly"/>
				<input type="hidden" id="rno" name="rno" value="${replyUpdate.rno}" readonly="readonly"/>
				<input type="hidden" id="page" name="page" value="${scri.page}"/>
				<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}">
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}">
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}">
				<table>
					<tbody>
						<tr>
							<td>
								<label for="content">댓글 내용</label><input type="text" id="content" name="content" value="${replyUpdate.content}" />
							</td>
						</tr>
					</tbody>
				</table>
				<div>
					<button type="submit" class="update_btn">저장</button>
					<button type="button" class="cancel_btn">취소</button>
				</div>
			</form>
		</div>
		</section>
	</div>		
</body>
</html>