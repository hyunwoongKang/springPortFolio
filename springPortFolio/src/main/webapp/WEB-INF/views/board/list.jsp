<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../resources/main/css/board.css?ver=14" rel="stylesheet" type="text/css" />
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	var formObj=$(".addBoardForm");
	$(".newBoard").on("click",function(){
		formObj.submit();
	});
	
});
</script>
<title>${pk.title}</title>
</head>
<body>
<%@include file="../include/header.jsp" %>		

<form action="register" method="get" class="addBoardForm">
<input type="hidden" value="${pk.page}"  name="page">
<input type="hidden" value="${pk.perPageNum}"  name="perPageNum">
<input type="hidden" value="${pk.title}"  name="title">
</form>
<div id="pattern" class="pattern">
    <ul class="list img-list">
   

    <div style="width: 100%;" class="resultSearch">
       <c:if test="${pk.title ==null }">
    	<img alt='recentReadImage' src='../resources/main/img/rocket.png' class='userImage'>
    	<span style='font-weight: bold; padding-left: 10px;'>검색 결과</span>
	 </c:if>
	 <c:if test="${pk.title !=null }">
	 	<img alt='recentReadImage' src='/displayFile?fileName=${title.title_img}' class='userImage'>
    	<span style='font-weight: bold; padding-left: 10px;'>${title.title}</span>
	 </c:if> 
	</div>
  
  
     <c:forEach var="board" items="${list}">
        <li>
            <a href="/board/read${pk.makeUri(pk.page)}&bno=${board.bno}">
                <div class="li-img">
                    <c:choose>
                      <c:when test="${board.thumbnail eq board.title }">
                      	<img src="/resources/main/img/thumbnail/${board.title}.png" alt="Image Alt Text" class="listThumbnail"/>
                      </c:when>
                    	<c:otherwise>
                    		<img src="/displayFile?fileName=${board.thumbnail}" alt="Image Alt Text" class="listThumbnail"/>
                    	</c:otherwise>
                    </c:choose>
            	 <span>${board.board_title}</span>
                </div>
             </a>
                <div class="li-text">
                    <p class="li-sub"><a href="#"><small>${board.writer }</small></a></p> 
                    <p><small>조회수 ${board.viewcnt}회</small> <small class="date"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${board.regdate }"/></small></p>
          	   </div>
        </li>
         </c:forEach> 
    </ul>
</div>

<c:if test="${!empty login}">
	<button type="button" class="btn btn-dark btn-sm newBoard">게시판 등록</button>
</c:if>

<!-- paging -->
 <div class="pagingDiv">
	<ul class="pagingUl">
		<!-- prev-->
		 <c:if test="${pk.prev}">
		    <li><a href="list${pk.makeUri(pk.startPage-1)}"><span>&laquo;</span></a></li>	
		</c:if> 
		<!-- displayNum -->
		<c:forEach begin="${pk.startPage }" end="${pk.endPage }" var="index">
			<li>
		 			<a href="list${pk.makeUri(index)}"><span>${index}</span></a> 
			</li>
		</c:forEach>
		<!-- next -->
	 		<c:if test="${pk.next && pk.endPage >0 }">
		    <li>
		    	<a href="list${pk.makeUri(pk.endPage+1)}"><span>&raquo;</span></a>
		    </li>
		</c:if> 
	</ul>
</div> 

<%@include file="../include/footer.jsp" %>	


</body>
</html>