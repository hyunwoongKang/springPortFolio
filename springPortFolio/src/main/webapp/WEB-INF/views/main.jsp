<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
 <link href="../resources/main/css/board.css?ver=16" rel="stylesheet" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	

	//베스트5 게시물 받기
	$.getJSON("board/bestBoard",function(data){
		
		
		 $.each(data,function(key,value){ 
		
			 showData(value,$("#pattern1"),$("#template"));
		 });
	});
	
    var showData=function(list,target,template)
    {
    	var template=Handlebars.compile(template.html());
    	
    	var html=template(list);
    	
    	target.append(html);
    }
    
     Handlebars.registerHelper("TitleEq",function(block){
    	
    	
    	if(this.title == this.thumbnail){
    		return block.fn(this);
    	}else{
    		return block.inverse(this);
    	}
    	
    }); 
     
     Handlebars.registerHelper("dateFormat",function(date){
    	 var date=new Date(date);
    	 
    	 var year=date.getFullYear();
    	 var month=date.getMonth()+1;
    	 var day=date.getDate();
    	 var hour=date.getHours();
    	 var minute=date.getMinutes();
    	 
    	 var result=year+"-"+month+"-"+day+" "+hour+":"+minute;
    	 
    	 return result;
     });
	
});

</script>
<body>
<%@include file="include/header.jsp" %>	
<div style="min-height: 600px;">

<c:if test="${cookieLength !=1 && cookieLength !=null}">
<div id="pattern" class="pattern">
    <ul class="list img-list">
	<div style="width: 100%;">
		<img alt="recentReadImage" src="../resources/main/img/secondLogo.png" class="userImage">
		<span style="font-weight: bold; padding-left: 10px;">최근 본 게시판</span> </div>
  <c:forEach items="${recentReadBoard}" var="board">
       <li>
            <a href="/board/read?title=${board.title}&page=1&perPageNum=20&bno=${board.bno}">
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
</c:if>

<div id="pattern1" class="pattern">
</div>



</div>
<script type="text/x-handlebars-template" id="template">
	<ul class="list img-list">	
		<div style="width: 100%;">
{{#each .}}

{{#if @first}}
		<img alt="recentReadImage" src="/displayFile?fileName={{title_img}}" class="boardTitleImage">
		<span style="font-weight: bold; padding-left: 10px;" class="bestBoardTitle">{{title}} 베스트</span></div>
{{/if}}
       <li>
            <a href="/board/read?title={{title}}&page=1&perPageNum=20&bno={{bno}}">
                <div class="li-img">

					{{#TitleEq}}					
                      	<img src="/resources/main/img/thumbnail/{{title}}.png" alt="Image Alt Text" class="listThumbnail"/>
					{{else}}
                    	<img src="/displayFile?fileName={{thumbnail}}" alt="Image Alt Text" class="listThumbnail"/>
					{{/TitleEq}}

            	 <span>{{board_title}}</span>
                </div>
             </a>
                <div class="li-text">
                    <p class="li-sub"><a href="#"><small>{{writer}}</small></a></p> 
                    <p><small>조회수 {{viewcnt}}회</small><small class="date">{{dateFormat regdate}}</small></p>
          	   </div>
        </li>
 {{/each}} 
    </ul>
</script>
<%@include file="include/footer.jsp" %>	
</body>
</html>