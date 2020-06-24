<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
			width: 60%;
			
		}
	</style>
</head>
	<script type="text/javascript">
		$(document).ready(function(){
			var formObj = $("form[name='updateForm']");
			
			$(document).on("click", "#fileDel", function(){
				$(this).parent().remove();
			})
			
			fn_addFile();
			
			$(".cancel_Btn").on("click", function(){
				event.preventDefault();
				location.href = "/board/list?bno=${update.bno}"
						+ "&page=${scri.page}"
						+ "&perPageNum=${scri.perPageNum}"
						+ "&searchType=${scri.searchType}"
						+ "&keyword=${scri.keyword}";
			})
			
			$(".update_Btn").on("click", function(){
				if (fn_valiChk()) {
					return false;
				}
				formObj.attr("action", "/board/update");
				formObj.attr("method", "post");
				formObj.submit();
				
			})
		})
		
		function fn_valiChk(){
			var updateForm = $("form[name='updateForm'] .chk").length;
			for (var i = 0; i < updateForm; i++) {
				if ($(".chk").eq(i).val()== "" || $(".chk").eq(i).val()==null) {
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		function fn_addFile(){
			var fileIndex = 1;
			// $("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right' id='fileAddBtn'>"+"추가"+"</button></div>");
			$(".fileAdd_Btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' style='float:right' id='fileDelBtn'>"+"삭제"+"</button></div>");
			});
			$(document).on("click", "#fileDelBtn", function(){
				$(this).parent().remove();
			});
		}
		var fileNoArray= new Array();
		var fileNameArray= new Array();
		function fn_del(value, name){
			
			fileNoArray.push(value);
			fileNameArray.push(name);
			$("#fileNoDel").attr("value", fileNoArray);
			$("#fileNameDel").attr("value", fileNameArray);
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
			<form action="/board/update" method="POST" role="form" name = "updateForm" enctype="multipart/form-data">
				<input type="hidden" id="mno" class="mno" name="mno" value="${member.mno}">
				<input type="hidden" id="bno" name="bno" value="${update.bno}" readonly = "readonly">
				<input type="hidden" id="page" name="page" value="${scri.page}">
				<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}">
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}">
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}">
				<input type="hidden" id="fileNoDel" name="fileNoDel[]" value="">
				<input type="hidden" id="fileNameDel" name="fileNameDel[]" value="">
				<table>
					<tbody>
						<tr>
							<td>
							<div class="form-group" >
								<label for="title" class="col-xm-2 control-label">제목</label>
								<input type="text" id="title" name="title" value="${update.title}" class="chk form-control" title="제목을 입력하세요">
							</div>
							</td>
						</tr>
						<tr>
							<td>
							<div class="form-group">
								<label for="content" class="col-xm-2 control-label">내용</label>
								<textarea id="content" name="content" class="chk form-control" title="내용을 입력하세요"><c:out value="${update.content}"/></textarea>
							</div>
							</td>
						</tr>
						<tr>
							<td>
							<div class="form-group">
								<label for="writer" class="col-xm-2 control-label">작성자</label>
								<input type="text" id="writer" name="writer" value="${update.writer}" class="form-control" readonly="readonly">
							</div>
							</td>
						</tr>
						<tr>
						
							<td>
							<div class="form-group">
								<label for="regdate" class="col-xm2 control-label">작성날짜</label>
								<fmt:formatDate value="${update.regdate}" pattern="yyyy-MM-dd"/>
							</div>
							</td>
						</tr>
						<tr>
							<td id="fileIndex">
								<c:forEach var="file" items="${file}" varStatus="var">
									<div>
										<input type="hidden" id="FILE_NO" name="FILE_NO" value="${file.FILE_NO}">
										<input type="hidden" id="FILE_NAME" name="FILE_NAME" value="FILE_NO_${var.index}">
										<a href="#" id="fileName" onclick="return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}KB)
										<button id="fileDel" onclick="fn_del('${file.FILE_NO}', 'FILE_NO_${var.index}');" type="button" class="btn btn-warning">삭제</button><br>
									</div>
								</c:forEach>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<div>
					<button type="button" class="update_Btn btn btn-info">저장</button>
					<button type="button" class="cancel_Btn btn btn-info">취소</button>
					<button type="button" class="fileAdd_Btn btn btn-success">파일 추가</button>
				</div>
			</form>
		</section>
	</div>
</body>
</html>