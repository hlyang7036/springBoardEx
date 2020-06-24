<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>게시판</title>
	<style type="text/css">
		li {
			list-style: none;
			float: left;
			padding: 6px;
		}
		/* form {
    		/* Just to center the form on the page */
    		margin: 0 auto;
	    	width: 750px;
	    	/* To see the outline of the form */
	    	padding: 1em;
	    	border: 1px solid #CCC;
	    	border-radius: 1em;
		} */
		
		
	</style>
	
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css" id="bootstrap-css">
	
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<div id="container" style="width: 700px" class="center-block">
		<header>
			<h1>게시판</h1>
		</header>
		<div>
			<hr/>
				<div>
					<%@include file="nav.jsp" %>
				</div>
		</div>
		
		<section id="container" style="margin: auto;" class="center-block">
			<form action="/board/list" role="form" method="get">
				<table class="table table-striped" style="margin: auto;" >
					<thead class="mdb-color darken-3">
					<tr class="text-white">
						<th>번호</th><th>제목</th><th>작성자</th><th>등록일</th><th>조회수</th>
					</tr>
					</thead>
					<c:forEach items="${list}" var="list">
						<tr>
							<td><c:out value="${list.bno}"></c:out></td>
							<td><a href="/board/readView?bno=${list.bno}&
														page=${scri.page}&
														perPageNum=${scri.perPageNum}&
														searchType=${scri.searchType}&
														keyword=${scri.keyword}"><c:out value="${list.title}"></c:out>&nbsp<c:out value="[${list.replyCount}]"></c:out></a></td>
							<td><c:out value="${list.writer}"></c:out></td>
							<td><fmt:formatDate value="${list.regdate}" pattern="yyyy-MM-dd"/></td>
							<td><c:out value="${list.hit}"></c:out></td>
						</tr>
					</c:forEach>
					
				</table>
				<div class="search row">
					<div class="col-xs-2 col-sm-2">
						<select name="searchType" class="form-control">
							<option value="n"<c:out value="${scri.searchType == null ? 'selected' : ''}"/>>-----</option>
							<option value="t"<c:out value="${scri.searchType eq 't' ? 'selected' : ''}"/>>제목</option>
							<option value="c"<c:out value="${scri.searchType eq 'c' ? 'selected' : ''}"/>>내용</option>
							<option value="w"<c:out value="${scri.searchType eq 'w' ? 'selected' : ''}"/>>작성자</option>
							<option value="tc"<c:out value="${scri.searchType eq 'tc' ? 'selected' : ''}"/>>제목+내용</option>
						</select>
					</div>	
						<div class="col-xm-10 col-sm-10" style="width: 60%">
							<div class="input-group">
								<input type="text" name="keyword" id="keywordInput" value="${scri.keyword}" class="form-control"/>
								<span class="input-group-btn">
									<button id="searchBtn" type="button" class="btn btn-default">검색</button>
								</span>
							</div>
						</div>
						<script>
							$(function(){
								$('#searchBtn').click(function(){
									self.location = "list" + '${pageMaker.makeQuery(1)}' + "&searchType=" + $("select option:selected").val() + "&keyword=" + encodeURIComponent($('#keywordInput').val());
								});
							});
						</script>
					</div>
				<div class="col-md-offset3">
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
							<li><a href="list${pageMaker.makeSearch(pageMaker.startPage - 1)}">이전</a></li>
						</c:if>
						
						<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
							<li <c:out value="${pageMaker.cri.page == idx ? 'class=info' : '' }"/>>
							<a href="list${pageMaker.makeSearch(idx)}">${idx}</a></li>
						</c:forEach>
						
						<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
							<li><a href="list${pageMaker.makeSearch(pageMaker.endPage + 1)}">다음</a></li>
						</c:if>
					</ul>
				</div>
			</form>
		</section>
			
	</div>
</body>
</html>