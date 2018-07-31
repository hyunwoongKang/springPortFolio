<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 등록</title>
<link href="../resources/main/css/board.css?ver=13" rel="stylesheet" type="text/css" /> 
<!-- <script src="https://code.jquery.com/jquery.min.js"></script>  -->
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script> -->

<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script> 
    
    
<script type="text/javascript">

 $(document).ready(function(){
	 var formObj=$(".registerForm");
	 var num=0;
	 
	$(".addBoard").on("click",function(){
		
		formObj.submit(); 
	});  
 
    $('.summernote').summernote({
        height: 800,          // 기본 높이값
        minHeight: 800,       // 최소 높이값(null은 제한 없음)
        maxHeight: null,      // 최대 높이값(null은 제한 없음)
        focus: true,          // 페이지가 열릴때 포커스를 지정함
        lang: 'ko-KR',         // 한국어 지정(기본값은 en-US)
        	callbacks: { // 콜백을 사용
                // 이미지를 업로드할 경우 이벤트를 발생
			    onImageUpload: function(files, editor, welEditable) {
			    	sendFile(files[0], editor, welEditable);
			    	
			    }
        }
        
      });
    
    	function sendFile(file,editor,welEditable){
        	
    		
        	var title= $("#title").val();
        	var nickName= $("#writer").val();
        	
       		var formData=new FormData(); 	
       		formData.append('file',file);
       	    formData.append('title',title);
       	    formData.append('nickName',nickName);
       	
       		
       	 	$.ajax({
       	 		url:'/uploadContentImage',
       	 		data:formData,
       	 		dataType:'text',
       	 		processData:false,
       	 		contentType:false,
       	 		type:'POST',
       	 		success:function(data){
       	 		$('.summernote').summernote('editor.insertImage', "/displayFile?fileName="+data);

       	 		}
       	 		
       	 	}); 
    		
    	}
    	
    	
    	$(".card-body").on("dragenter dragover", function(event) {
			event.preventDefault();
		});

    	$(".modifyThumbNail").hide();
    	
    	//썸네일 
    	$(".card-body").on("drop",function(event){
    		event.preventDefault();
    		
    		var files=event.originalEvent.dataTransfer.files;
    		var str="";
    		var file=files[0];
    		
         	var title= $("#title").val();
        	var nickName= $("#writer").val();
    		var division="thumbNail";
        	
        	
    		var formData=new FormData();
    		
    		formData.append("file",file);
    		formData.append("title",title);
    		formData.append("nickName",nickName);
    		formData.append("division",division);
    	
    		$.ajax({
    			url:"/uploadThumbNail",
    			data:formData,
    			dataType:'text',
    			processData:false,
				contentType:false,
				type:"POST",
				success:function(data){
					alert(data);
					var str="";
					str+="<img class='card-img-top' src='/displayFile?fileName="+data+"'>";
					str+="<input type='hidden' name='thumbnail' value='"+data+"'>"
		
					$(".card-image").html(str);
					
					$(".thumbNailSpan").hide();
					$(".modifyThumbNail").show();
					
					$(".modifyThumbNail").attr("href",data);
				}
    			
    		});
    		
    	});
    	
    	//썸네일 변경하기
    	$(".modifyThumbNail").bind("click",function(event){
    		event.preventDefault();
    		var file=$(this).attr("href");
    		
    		$.ajax({
    			url:"/deleteThumbNail",
    			data:{file:file},
    			dataType:'text',
    			type:'post',
    			success:function(data){
    				if(data=='deleted'){
    				  alert("변경 되었습니다.");
    				 $(".thumbNailSpan").show();
  					$(".modifyThumbNail").hide();
  					$(".card-img-top").remove();
    				}
    				
    			}
    			
    			
    		});
    	});
    
}); 

</script>
</head>
<body>

<%@include file="../include/header.jsp" %>	   
<div class="readDiv">
	<div class="readDiv2">
	<form action="register" method="POST" class="registerForm" >
	  <input type="hidden" name="title" value="${pk.title }" id="title">                                                                               
		<!-- 등록할 글 타이틀 -->
		<div class="readTitle">
			<input type="text" name="board_title" id="board_title" placeholder="제목을 입력하세요."  >
		</div>
	
<div class="container">
  	<div class="row">
		<!-- 섬네일 -->
		<div class="col" >
     		<div class="card" style="width: 17rem;">
     			<div class="card-image">
     			</div>
     			
 			 	<div class="card-body" style="text-align: center;">
    		     	<span class="thumbNailSpan"><p class="card-text">썸네일의 등록할 사진을 <br> 드래그 해주세요.</p></span>
    		     	<a href='#' class='modifyThumbNail'>썸네일 변경하기</a>
  			   </div>
			</div>
   		</div>
		<!-- 유저 -->
		<div class="col-md-auto">
			<table>
				<tr>
						<td rowspan="2" ><img alt="" src="${login.user_image }" class="userImage"></td>
						<!-- 임시로 로그인시 세션 만들면 수정 필요! -->
						<td><input type="text" id="writer" name="writer" value="${login.nickName }" readonly="readonly"></td>
				</tr>
				<tr>
					<td><fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
			</table>
		</div>	
  </div>
</div>
		
		<!-- 컨텐츠 -->
			<div class="readContent">
				  <textarea name="content" id="content" class="summernote"></textarea>
				</div>
			
	</form>	
		<!-- 등록 및 목록 버튼 -->
			<div class="readContentButton" style="margin-bottom: 50px;">
				<button class="btn btn-dark btn-sm addBoard" >등록</button>
				<button class="btn btn-danger btn-sm cancel">취소</button>
			</div>
			
	</div>
</div>

 
 <%@include file="../include/footer.jsp" %>	 
</body>
</html>