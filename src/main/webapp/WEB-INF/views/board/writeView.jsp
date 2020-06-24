<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<title>게시판</title>
	<style type="text/css">
		table {
			width: 60%
		}
	</style>
</head>
	<script type="text/javascript">
		$(document).ready(function(){
			var formObj = $("form[name='writeForm']");
			$(".write_Btn").on("click", function(){
				if(fn_valiChk()){
					return false;
				}
				formObj.attr("action","/board/write");
				formObj.attr("method","post");
				formObj.submit();
			});
			fn_addFile();
			
			$(".cancel_Btn").on("click", function(){
				if (confirm("작성을 취소 하겠습니까?")) {
					location.href="/board/list";
				
				}
			})
		})
		function fn_valiChk(){
			var regForm = $("form[name='writeForm'] .chk").length;
			for (var i = 0; i < regForm; i++) {
				if($(".chk").eq(i).val()=="" || $(".chk").eq(i).val()==null) {
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		function fn_addFile(){
			var fileIndex = 1;
			//$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right;' id='fileAddBtn'>"+"추가"+"</button></div>");
			$(".fileAdd_Btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right;' id='fileDelBtn'>"+"삭제"+"</button></div>");
			});
			$(document).on("click", "#fileDelBtn", function(){
				$(this).parent().remove();
			});
		}
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
		<section id="container" style="width: 60%" class="center-block">
			<form name="writeForm" action="/board/write" method="post" enctype="multipart/form-data">
				<input type="hidden" id="mno" class="mno" name="mno" value="${member.mno}">
				<table>
					<tbody>
						<c:if test="${member != null }">
							<tr>
								<td>
								<div class="form-group">
									<label for="title" class="col-xm2 control-label">제목</label>
									<input type="text" id="title" name="title" class="chk form-control" title="제목을 입력하세요"/>
								</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="form-group">
									<label for="content" class="col-xm2 control-label">내용</label>
									<textarea id="content" name="content" class="chk form-control" title="내용을 입력하세요"></textarea>
									</div>
								</td>
							</tr>
							<tr>
								<td>
								<div class="form-group">
									<label for="writer" class="col-xm2 controll-label">작성자</label>
									<input type="text" id="writer" name="writer" class="chk form-control" title="작성자를 입력하세요" value="${member.userName}" readonly="readonly"/>
								</div>
								</td>
							</tr>
							<tr>
								<td id="fileIndex">
									
								</td>
							</tr>
							<tr>
								<td>
									<button type="submit" class="write_Btn btn btn-info">작성</button>
									<button type="button" class="cancel_Btn btn btn-info">취소</button>
									<button type="button" class="fileAdd_Btn btn btn-success">파일 추가</button>
								</td>
							</tr>
						</c:if>
						<c:if test="${member == null}">
							<p>로그인 이후에 작성하실 수 있습니다</p>
						</c:if>
					</tbody>
				</table>
				
			</form>
			
		</section>
		
	</div>

</body>
</html>