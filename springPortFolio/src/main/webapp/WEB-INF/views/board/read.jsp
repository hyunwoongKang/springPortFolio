 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${board.board_title }</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<link href="../resources/main/css/board.css?ver=31" rel="stylesheet" type="text/css" />
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">


<script type="text/javascript">

var bno=${board.bno};
var replyPage=1;
var userImage="";

var getReply=function(url)
{

	$.getJSON(url,function(data){
		console.log(data);
		 printData(data.reply,$(".allReply ul"),$('#template')); 
		 replyPaging(data.pk); 
});
}
	
var printData=function(replyArr,target,templateObject)
{
		
	var template=Handlebars.compile(templateObject.html());
		
	var html=template(replyArr);
	//추가한 이유 
	//댓글을 추가했을때 getReply가 두번 발생되, 댓글 중복이 일어나 
	//기존에 있던거 제거하고 다시 새로 추가 하는 형식으로.
	$(".replyLi").remove();
	target.append(html);
}

var replyPaging=function(pk){
	var temp="";
	
	//prev
	if(pk.prev){
		
		temp+= "<li class='page-item'><a class='page-link' href='"+(pk.startPage-1)+"' aria-label='Previous'><span aria-hidden='true'>&laquo;</span><span class='sr-only'>Previous</span></a></li>";
	}
	//page
	for(var i=pk.startPage; i<=pk.endPage; i++)
	{
		//원하는 페이지를 누른 상태
		if(pk.page==i){
		 temp+="<li class='page-item active'><a class='page-link' href='"+i+"'>"+i+"<span class='sr-only'>(current)</span></a></li>" 
		}
		//현재 페이지가 아닌 다른 페이지 상태 처리
		else{
			temp+="<li class='page-item'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
		}
	}
	//next
	if(pk.next)
	{
		temp+= "<li class='page-item'><a class='page-link' href='"+(pk.endPage+1)+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span><span class='sr-only'>Next</span></a></li>";
	}
	
	$(".pagination").html(temp);
	
}
 
 	
$(document).ready(function(){
	
	var formObj=$(".readForm");
	
	//목록
	$(".returnList").on("click",function(){
		formObj.attr("action","list");
		formObj.submit();
	});
	
	//삭제
	$(".deleteBoard").on("click",function(){
		formObj.attr("action","deleteBoard");
		formObj.attr("method","POST");
		formObj.submit();
	});
	
	//수정
	$(".modifyBoard").on("click",function(){
		
		formObj.submit();
	});
	
//댓글 관련 스크립트

	//모든 댓글 가져오기
 	getReply("/reply/all/"+bno+"/"+replyPage); 
 	
	//댓글 페이징 처리
	$(".pagination").on("click","li a",function(event){
		event.preventDefault();
		replyPage=$(this).attr("href");
		getReply("/reply/all/"+bno+"/"+replyPage);
		
	});
	

 	//date 형식 포맷
 	//javascript template랑 jstl은 같이 쓸수 없다.
 	Handlebars.registerHelper("dateFormat",function(date){
 		var dateObj=new Date(date);
 		var year=dateObj.getFullYear();
 		var month=dateObj.getMonth()+1;
 		var day=dateObj.getDate();
 		var hour=dateObj.getHours();
 		var minute=dateObj.getMinutes();
		
 		var division="오전";
 		if(12<hour){
 			division="오후";
 			hour=hour-12;
 		}
 		
 		
 		return year+"/"+month+"/"+day+"  "+division+" "+hour+":"+minute;
 		
 	});
 	
 	//댓글 수정,삭제 권한
	Handlebars.registerHelper("ReplyerEq",function(replyer,block){
		if(replyer == '${login.nickName}')
		{
			return block.fn(this);
		}
	});
	
 	
	
 	//댓글 등록하기
 	$('.replyRegistButton').on("click",function(){
 		var replyText=$("#replyContent").val();
 		var replyer='${login.nickName}';
 		
 		$.ajax({
 			type:'POST',
 			url:'/reply/addReply',
 			data:{bno:bno,replyText:replyText,replyer:replyer },
 		     success:function(data){
 		    	 
 		    	if(data=='success'){
 		    		alert("댓글을 추가 하였습니다.");
 		    		$("#replyContent").html(" ");
 		    		getReply("/reply/all/"+bno+"/1");
 		    	}
 		     }
 		}); 
 	});
 	
 	//댓글 수정하기
 	$(".btn-primary").on("click",function(){
 		
 		var rno=$(".modifyRno").val();
 		var replyer=$(".modifyReplyer").html();
 		var replyText=$(".modalTextarea").val();

 		$.ajax({
			  type:"put",
			  url:"/reply/modify",
			  headers:{"Content-Type":"application/json", "X-HTTP-Method-Override":"POST"},
			  dataType:"text",
			  data:JSON.stringify({bno:bno,rno:rno,replyer:replyer,replyText:replyText}),
			  success:function(result)
			  {
				  if(result=="success")
				  {
					  getReply("/reply/all/"+bno+"/"+replyPage);
					 alert("수정이 완료 되었습니다.");
		    		$(".btn-secondary").click();
					 
				  }
			  }
		  });
		  
 	});

 	
 	$(".loadingDiv").hide();
 	
 	//좋아요 관련
 	$(".likeImage").on("click",function(event){
 		event.preventDefault();

 		var check=$(".imgCehck").attr("check");
 		var nickname='${login.nickName}';
 		
 		
 		if(nickname==""){
 			alert("로그인 후 이용해 주세요.");
 		}
 		else {
 		       
 			    $.ajax({
 	 			url:"/board/addLike",
 	 			dataType:"text",
 	 			data:({bno:'${board.bno}',nickname:nickname,likeCheck:check}), 
 	 			type:"get",
 	 			beforeSend:function(){
 	 				$(".loadingDiv").show();
 	 			},
 	 			success:function(data){
 	 				if(data=='success'){
 	 					location.reload();
 	 				}
 	 			},complete:function()
 	 			{
 	 				$(".loadingDiv").hide();
 	 			}
 	 			
 	 		}); 
 			    
 		}
 		
 	});
 	
});


//댓글 수정 모달 열기
function replyModify(rno,replyer,replytext,image)
{
	var rnoForm="<input type='hidden' class='modifyRno' value='"+rno+"'>";
	var title="<img class='userImage' src='"+image+"'>"+"<span class='modifyReplyer' >"+replyer+"</span>"+rnoForm;
	
	$(".modal-title").html(title);
	$(".modalTextarea").html(replytext);
}

//댓글 삭제하기
function replyDelete(rno)
{	
    $.ajax({
    	url:"/reply/delete/"+rno,
    	type:"delete",
  		headers:{"Content-Type":"application/json","X-HTTP-Method-Override":"delete"},
	    dataType:"text",
	     data:JSON.stringify({bno:bno,rno:rno}),
    	success:function(data){
    		 if(data=='success'){
    			alert("삭제가 완료 되었습니다.");
    			getReply("/reply/all/"+bno+"/"+replyPage);
    		} 
    	}
    });
}

</script>


</head>
<body>
<%@include file="../include/header.jsp" %>		

 <form action="modifyBoard" method="get" class="readForm">
  <input type="hidden" name="bno" value="${board.bno}"> 
   <input type="hidden" name="title" value="${pk.title}">
   <input type="hidden" name="page" value="${pk.page}">
    <input type="hidden" name="perPageNum" value="${pk.perPageNum}"> 
 </form>

<div class="loadingDiv">
<img alt="" src="/resources/main/gif/loading.gif" class="loadingGif">
</div>

<div class="readDiv">
	<div class="readDiv2">
	
		<div class="readTitle">
			<h1>${board.board_title }</h1>
		</div>
		
		<div class="container">
  			<div class="row">
			  <!-- 섬네일 -->
			  <div class="col" ></div>
		      <!-- 유저 -->
		      <div class="col-md-auto">
			    <table>
				  <tr>
						<td rowspan="2" ><img alt="" src="${board.user_image}" class="userImage"></td>
						<!-- 임시로 로그인시 세션 만들면 수정 필요! -->
						<td><span id="writer" name="writer">${board.writer}</span></td>
				  </tr> 
				  <tr>
					<td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
				  </tr>
			    </table>
		      </div>	
  		   </div>
	   </div>
	   
			<div class="readContent">
				${board.content}
			</div>
		
		    <div class="readContentButton">
		<c:if test="${login.nickName eq board.writer }">
		    	  <button class="btn btn-secondary btn-sm modifyBoard">수정</button> 
		    	  <button class="btn btn-danger btn-sm deleteBoard">삭제</button>
		</c:if>  
		    	 
		    	      <a href="#" class="likeImage" style="float: left; margin-top: 1%;">
		    	 
		    	      <c:choose>
		    	        <c:when test="${likeCheck.likeCheck eq 'Y'.charAt(0)}">
		    	      	  <img src="/resources/main/img/like.png" class="imgCehck" check="Y" style="height: 2.5em;"> 
		    	  	 	</c:when>
		    	  	 	
		    	  	 	<c:otherwise>
		    	  	 	  <img src="/resources/main/img/like2.png" class="imgCehck" check="N" style="height: 2.5em;">
		    	  	 	</c:otherwise>
		    	  	 </c:choose> 
		    	      좋아요 ${board.good }개
		    	      </a>
		    	      <button class="btn btn-dark btn-sm returnList">목록</button>
		    </div>
		  
			<div class="readReply">
			    <span class="replyCount">댓글 ${board.replycnt }개</span>

				<!-- 댓글 보여주기 -->
			    <div class="allReply">
			    	 <ul> 
			     	</ul> 
			    </div>

 				 <!-- 댓글 달기 -->
 			<c:if test="${!empty login }">
			    <div class="replyRegister">
			    	<img alt="" src="${login.user_image }" class="userImage">
			    	<input type="text" id="replyContent" >
			    	<button class="replyRegistButton btn btn-outline-dark btn-sm" style="vertical-align: middle;">등록</button>
			    </div>
			</c:if>	
				<!-- 댓글 페이징 -->
			    <div class="replyPaging">
			       <nav aria-label="Page navigation example">
  					 <ul class="pagination pagination-sm  justify-content-center">
  					</ul>
					</nav>
			    </div>
		   </div>
		   
		   
	</div>
</div>


<!-- 댓글 수정시 사용 -->
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      	 <div class="modal-header">
        	<h5 class="modal-title" ></h5>
         </div>
         <div class="modal-body" >
         		<textarea rows="" cols="" class="modalTextarea"></textarea>
         </div>
     	 <div class="modal-footer">
        	<button type="button" class="btn btn-primary">확인</button>
       		<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      </div>
      
    </div>
  </div>
</div>


<!--댓글 template -->
<script type="text/x-handlebars-template" id="template">
{{#each .}}
	<li class="replyLi">
	   <div class="allReplyImage">
		  <img alt="" src="{{user_image}}"> 
		</div>
		<div class="allReplyContent">
			<div class="replayDate"><span>{{replyer}}</span><small>{{dateFormat updateDate}}</small></div>
			<div class="replyContent" ><span>{{replyText}}</span>
		</div>

{{#ReplyerEq replyer}}
 		<div  class="replyButton">
		  <button class="btn btn-outline-dark btn-sm" onclick="replyModify({{rno}},'{{replyer}}','{{replyText}}','{{user_image}}');" data-toggle="modal" data-target=".bd-example-modal-lg" >수정</button>
		  <button class="btn btn-outline-danger btn-sm" onclick="replyDelete({{rno}});">삭제</button>
		</div>
{{/ReplyerEq}}
		</div> 
   </li>	
{{/each}}

</script>

<%@include file="../include/footer.jsp" %>	
</body>
</html>