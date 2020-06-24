<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		/* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
    
        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 50%; /* Could be more or less, depending on screen size */                          
        }
        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
	</style>
	<meta charset="UTF-8">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<title>게시글 상세</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			
			var formObj = $("form[name='readForm']");
			
			// 수정
			$(".update_btn").on("click", function(){
				formObj.attr("action", "/board/updateView");
				formObj.attr("method", "get");
				formObj.submit();
			})
			
			// 삭제
			$(".delete_btn").on("click", function(){
				
				var deleteYN = confirm("삭제하시겠습니까?")
				if(deleteYN == true) {
				
					formObj.attr("action", "/board/delete");
					formObj.attr("method", "post");
					formObj.submit();
					
				}
				location.href = "/board/list";
				
			})
			
			// 목록
			$(".list_btn").on("click", function(){
				location.href = "/board/list?page=${scri.page}"
								+"&perPageNum=${scri.perPageNum}"
								+"&searchType=${scri.searchType}&keyword=${scri.keyword}";
			})
			
			// 댓글 작성
			$(".replyWrite_btn").on("click", function(){
				var formObj = $("form[name='replyForm']");
				
					if (fn_valiChk()) {
						return false;
					}
					formObj.attr("action", "/board/replyWrite");
					formObj.submit();
				
			});
			
			
			
/* 			// 댓글 수정 view
			$(".replyUpdate_btn").on("click", function(){
							
				location.href = "/board/replyUpdateView?bno=${read.bno}"
								+ "&page=${scri.page}"
								+ "&perPageNum=${scri.perPageNum}"
								+ "&searchType=${scri.searchType}"
								+ "&keyword=${scri.keyword}"
								+ "&rno="+$(this).attr('data-rno');
			}); */
			
			/* // 댓글 삭제 view
			$(".replyDelete_btn").on("click", function(){
				var replyDeleteYN = confirm("삭제하시겠습니까?")
				if(replyDeleteYN == true) {
					
					fn_replyDelete(rno);
					
					formObj.attr("action", "/board/delete");
					formObj.attr("method", "post");
					formObj.submit();
					
				}
				return;
				
				
				location.href = "/board/replyDeleteView?bno=${read.bno}"
								+ "&page=${scri.page}"
								+ "&perPageNum=${scri.perPageNum}"
								+ "&searchType=${scri.searchType}"
								+ "&keyword=${scri.keyword}"
								+ "&rno="+$(this).attr('data-rno');
				 
			}); */
			
			
			showReplyList();
		})
		
		// 댓글 값 체크
		function fn_valiChk(){
			var regForm = $("form[name='replyForm'] .chk").length;
			for (var i = 0; i < regForm; i++) {
				if($(".chk").eq(i).val()=="" || $(".chk").eq(i).val()==null) {
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		// 파일 다운로드
		function fn_fileDown(fileNo){
			var formObj = $("form[name='readForm']");
			$("#FILE_NO").attr("value", fileNo);
			formObj.attr("action", "/board/fileDown");
			formObj.submit();
		}
		
/* 		// 댓글 수정
		function fn_replyUpdate(){
			location.href = "/board/replyUpdateView?bno=${read.bno}"
							+ "&page=${scri.page}"
							+ "&perPageNum=${scri.perPageNum}"
							+ "&searchType=${scri.searchType}"
							+ "&keyword=${scri.keyword}"
							+ "&rno="+$(".replyUpdate_btn").attr('data-rno');
		} */
		/* // 댓글 삭제
		function fn_replyDelete(){
			location.href = "/board/replyDeleteView?bno=${read.bno}"
							+ "&page=${scri.page}"
							+ "&perPageNum=${scri.perPageNum}"
							+ "&searchType=${scri.searchType}"
							+ "&keyword=${scri.keyword}"
							+ "&rno="+$(".replyDelete_btn").attr('data-rno');
		} */
		
		// ajax 댓글 수정
		function fn_replyUpdate(rno){
			var myModal = document.getElementById('myModal');
			var selectContent = $("#replyRno"+rno);
				
			var replyNo = rno;
			var bno = $("#bno").val();
			var replyContent = $(selectContent).data("content");
			$("#modalContent").val(replyContent);
			var content = $("#modalContent").val();
			console.log(content);
			myModal.style.display = "block";
 			$("#modalUpdate").on("click", function(){
				if (confirm("댓글을 수정하시겠습니까?")) {
 					$.ajax({
						url : "/board/replyUpdate",
						type : "POST",
//						dataTypr : "json",
						data : {
							'rno' : replyNo,
							'content' : $("#modalContent").val(),
							'bno' : bno
							},
						success : function(data){
							myModal.style.display = "none";
							showReplyList();
						},
						error : function(error) {
							console.log("에러 : " + error);
						}
						
					});			
				}
				return;
			})
		}
		
		// ajax 댓글 삭제
 		function fn_replyDelete(rno){
			var replyDeleteYN = confirm("삭제하시겠습니까?")
			if(replyDeleteYN == true) {
				$.ajax({
					url : "/board/replyDelete",
					type : "POST",
//					dataType : "json", 리턴타입이 없다면 쓸필요가 없다 쓰면 오히려 작동안됨
					data : {'rno' : rno},
					success : function(){
						showReplyList();
						
					},
					error : function(error) {
						console.log("에러 : " + error);
					}
				});
				
			}
			return;
		}
		
		// 댓글 리스트
		function showReplyList(){
			
			var userName = $("#userName").val();
			$.ajax({
				url : "/board/replyList",
				type : "GET",
				dataType : "json",
				data : $("#bno").serializeArray(),
				success : function(data){
					var a = "<form name='replyList' class='form-horizantal' method='post'>";
					
					$.each(data, function(key, value){
						var regdate = new Date(value.regdate);
						var dateFormatted = regdate.toISOString().substr(0,10);
						
						a += "<ol class='replyList list-group'>";
						a += "<li class='list-group-item'>";
						a += "<p> 작성자 : '"+value.writer+"'</p>";
 						a += "<p> 작성 날짜 : '"+dateFormatted+"'</p><br/>";
						a += "<p class='replyContent' id='replyRno"+value.rno+"' data-content='"+value.content+"'>"+value.content+"</p>";
						a += "<div>";
						if (userName == value.writer) {
							a += "<button type='button' class='replyUpdate_btn btn btn-primary' data-rno='"+value.rno+"' onclick='fn_replyUpdate("+value.rno+")'>수정</button>&nbsp&nbsp";
							a += "<button type='button' class='replyDelete_btn btn btn-primary' data-rno='"+value.rno+"' onclick='fn_replyDelete("+value.rno+")'>삭제</button>";
						}
						a += "</div>";
						a += "</li>";
						a += "</ol>";
					});
					a += "</form>";
					$(".replyList").remove();
					$(".showReply").html(a);
				}	
				
			});
		}
		
        // Get the modal
//        var modal = document.getElementById('myModal');
 
//        // Get the button that opens the modal
//        var btn = document.getElementById("myBtn");
 
        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];                                          
 
//        // When the user clicks on the button, open the modal 
//        btn.onclick = function() {
//            modal.style.display = "block";
//        }
 
        // When the user clicks on <span> (x), close the modal
        function fn_modalClose() {
            myModal.style.display = "none";
        }
 
        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target == myModal) {
                myModal.style.display = "none";
            }
        }

		
	</script>
</head>
<body>
	<div id="container" class="center-block">
		<header>
			<div>
				<h1>게시판</h1>
			</div>
		</header>
		<hr/>
			<div>
				<%@include file="nav.jsp" %>
			</div>
		<hr/>
		<section id="container" style="width: 60%" class="center-block">
			<form name="readForm" role="form" method="post">
				<input type="hidden" id="bno" name="bno" value="${read.bno}"/>
				<input type="hidden" id="page" name="page" value="${scri.page}"/>
				<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}">
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}">
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}">
				<input type="hidden" id="FILE_NO" name="FILE_NO" value="">
				
			</form>
			<div class="form-group">
				<label for="title" class="col-xm-2 control-label">제목</label>
				<input type="text" id="title" name="title" class="form-control" value="${read.title}" readonly="readonly">
			</div>
			<div class="form-group">	
				<label for="content" class="col-xm-2 control-label">내용</label>
				<textarea id="content" name="content" class="form-control" readonly="readonly"><c:out value="${read.content}"></c:out></textarea>
			</div>
			<div class="form-group">	
				<label for="writer" class="col-xm-2 control-label">작성자</label>
				<input type="text" id="writer" name="writer" class="form-control" value="${read.writer}" readonly="readonly">
			</div>
			<div class="form-group">	
				<label for="regdate" class="col-xm-2 control-label">작성 날짜</label>
				<fmt:formatDate value="${read.regdate}" pattern="yyyy-MM-dd"/>
			</div>
			<hr>
			<span>파일 목록</span>
			<div class="form-group" style="border: 1px solid #dbdbdb">
				<c:forEach var="file" items="${file}">
					<a href="#" onclick="fn_fileDown('${file.FILE_NO}'); return fales;">${file.ORG_FILE_NAME}</a>
					(${file.FILE_SIZE}KB)<br>
				</c:forEach>
			</div>
			<div>
				<c:if test="${member.userName == read.writer}">
				<button type="submit" class="update_btn btn btn-info">수정</button>
				<button type="submit" class="delete_btn btn btn-info">삭제</button>
				<button type="submit" class="list_btn btn btn-success">목록</button>
				</c:if>
			</div>
			<br/>
			<hr>
			
			<br/>
			<h3>댓글 목록</h3>
			
			<div class="showReply">
				
			</div>
			<form name="replyForm" method="post" class="form-horizantal">
				<c:if test="${member != null}">
					<input type="hidden" id="userName" class="userName" value="${member.userName}">
				</c:if>
				<input type="hidden" id="bno" name="bno" value="${read.bno}"/>
				<input type="hidden" id="page" name="page" value="${scri.page}"/>
				<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}">
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}">
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}">
				<c:if test="${member != null}">
					<div class="form-group">
						<label for="writer" class="col-sm-2 control-label">댓글 작성자</label>
							<div class="col-sm-10">
								<input type="text" id="writer" name="writer" class="chk form-control" value="${member.userName}" readonly="readonly" title="로그인 후 작성 할 수 있습니다"/>
							</div>
					</div>
					<div class="form-group">						
						<label for="content" class="col-sm-2 control-label">댓글 내용</label>
						<div class="col-sm-10">
							<input type="text" id="content" name="content" class="chk form-control" title="댓글을 입력 해주세요"/>
						</div>
					</div>
					<br/>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="replyWrite_btn btn btn-success">댓글 작성</button>
						</div>
					</div>
				</c:if>
				<c:if test="${member == null}">
					<label for="content" class="col-sm-10 control-label">댓글 작성</label><br/>
					<div class="col-sm-10">
						<input type="text" class="form-control" readonly="readonly" value="로그인 이후 작성 할 수 있습니다.">
					</div>
				</c:if>
			</form>
			<br/>
		</section>
		<!-- The Modal -->
		<div id="myModal" class="modal">
			<!-- Modal content -->
			<div class="modal-content">
				<span class="close" onclick="fn_modalClose()">&times;</span> 
				<input type="text" id="modalContent">
				<input type="hidden" id="modalCommentId">
				<input type="button" id="modalUpdate" class="btn btn-success btn-sm" value="수정">
			</div>
		</div>	
	</div>
</body>
</html>