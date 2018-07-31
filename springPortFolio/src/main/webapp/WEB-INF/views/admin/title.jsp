<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>타이틀 관리</title>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<link href="../resources/main/css/admin.css?ver=2" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<script type="text/javascript">

var getAllTitle=function(){
	$.getJSON("/admin/allTitle",function(data){
		printData(data,$(".tbody"),$("#adminTitleTemplate"));
	});
}

var printData=function(arr,target,templateObject){
	
	var template=Handlebars.compile(templateObject.html());
	var html=template(arr);
	$(".templateTr").remove();
	target.append(html);
	
}


//데이터 삭제
function deleteTitle(title){
	 $.ajax({
		url:"/admin/deleteTitle",
		type:"delete",
		dataType:"text",
		data:JSON.stringify({title:title}),
		headers:{"Content-Type":"application/json", "X-HTTP-Method-Override":"DELETE"},
		success:function(data)
		{
			if(data=='success'){
				alert("삭제를 완료 하였습니다.");
				getAllTitle();
				
			}	
		},
		error:function(error){
			alert("삭제에 실패하였습니다.	");
		}
	  });
	}






$(document).ready(function(){
	
	getAllTitle();
/* 	
	$(".modifyTitleModal").on("show.bs.modal",function(){
		alert($(this).attr("test"));
	}); */
	
	
	
	//타이틀 추가
	$(".addTitleButton").on("click",function(event){
		
		
		var titleModal=$(".addTitleModal");
		var file=titleModal.find("#titleModalimage")[0].files[0];
	    var title=titleModal.find("#titleModalTitle").val();
	    
	    var formData=new FormData();
	    formData.append("title",title);
	    formData.append("file",file);
	    
	
	    $.ajax({
			url:"/admin/addTitle",
  			dataType:"text",
  			data:formData,
  			type:"post",
  			processData:false,
  			contentType:false,
  			success:function(data){
  			  if(data=='success'){
  				alert("성공적으로 추가 되었습니다.");
  				$("#addTitleModal").modal('hide');
  				getAllTitle();
  			  }
  			},
  			error:function(error){
  				alert("실패하였습니다.");
  			}
	    });
	});

	$(".modifyTitleModal_TitleButton2").hide();
	
		
	//수정 모달 닫을때 
	$('.modifyTitleModal').on('hidden.bs.modal', function (e) {
		$(this).find("input").attr("readonly","readonly");
		$(".modifyTitleModal_TitleButton2").hide();
		$(".modifyTitleModal_TitleButton1").show();
		
		$(".modifyTitleModal_ImgGo").hide();
		$(".modifyTitleModal_TitleButton4").hide();
		$(".modifyTitleModal_TitleButton3").show();
		
		$(".modifyTitleModal_ImgGo").val("");
		});

	//추가 모달 닫을때 2
	$('#addTitleModal').on('hidden.bs.modal', function (e) {
		$("#titleModalTitle").val("");
		$("#titleModalimage").val("");
		});

	//타이틀 변경 수정
	$(".modifyTitleModal_TitleButton1").on("click",function(){
		$(this).hide();
		$(".modifyTitleModal_Title").removeAttr("readonly");
		$(".modifyTitleModal_TitleButton2").show();
	
		
	});
	
	//타이틀 변경 ㄲㄱ
	$(".modifyTitleModal_TitleButton2").on("click",function(){
		var title=$(".modifyTitleModal_Title").val();
		var num=$(".modifyTitleModal_Num").val();
		var title_img=$(".modifyTitleModal_Img").val(); 
		
		$.ajax({
			url:"/admin/modifyTitle",
			data:JSON.stringify({title:title,num:num,title_img:title_img}),
			type:"put",
			headers:{"Content-Type":"application/json","X-HTTP-Method-Override":"POST"},
			dataType:"text",
			success:function(data){
				if(data=='success'){
					alert("타이틀을 변경 하였습니다.");
					$(".modifyTitleModal").modal("hide");
					getAllTitle();
				}
			},
			error:function(error){
				alert("변경에 문제가 있습니다.");
			}
		});
	});
	
	//이미지 변경 
	$(".modifyTitleModal_ImgGo").hide();
	$(".modifyTitleModal_TitleButton4").hide();
	
	$(".modifyTitleModal_TitleButton3").on("click",function(){
		$(this).hide();	
		$(".modifyTitleModal_ImgGo").show();
		$(".modifyTitleModal_TitleButton4").show();
	});
	
	//이미지 변경 ㄲ
	$(".modifyTitleModal_TitleButton4").on("click",function(){
		var file=$(".modifyTitleModal_ImgGo")[0].files[0];
		var num=$(".modifyTitleModal_Num").val();
		var title_img=$(".modifyTitleModal_Img").val();
		var title=$(".modifyTitleModal_Title").val();
		
		var formData=new FormData();
		formData.append("file",file);
		formData.append("num",num);
		formData.append("title_img",title_img);
		formData.append("title",title)
		
		$.ajax({
			url:"/admin/modifyTitleImg",
			type:"post",
			dataType:"text",
			data:formData,
			processData:false,
  			contentType:false,
  			success:function(data){
  				
  				if(data=='success'){
  					alert("이미지 변경에 성공하였습니다");
  					$(".modifyTitleModal").modal("hide");
  					getAllTitle();
  				}
  			},
  			error:function(error){
  				alert("이미지 변경에 실패");
  			}
		});
		
		
	});

	
	//템플릿 데이터 모달로 전달~~
 	$(".tbody").on("click",".templateTr .modifyModal",function(){
		var img=$(this).attr("title_img");
		var title=$(this).attr("title");
		var num=$(this).attr("num");
		
		
		$(".modifyTitleModal_Title").val(title);
		$(".modifyTitleModal_Num").val(num);
		$(".modifyTitleModal_Img").val(img);
		
		$(".modifyTitleModal").modal("show");
	}); 
	
});
</script>
</head>
<body>
<%@include file="../include/header.jsp" %>	

<div class="container-fluid" >
	<div class="row flex-xl-nowrap">
		<div class="col-12 col-md-3 col-xl-2 bd-sidebar adminSidebar">	
		   <nav class="nav flex-column">
  			 <a class="nav-link active" href="/admin/member">회원 관리</a>
  			 <a class="nav-link" href="/admin/title">타이틀 관리</a>
		  </nav>
		</div>
		
		<div class="col-12 col-md-9 col-xl-9 py-md-3 pl-md-5 bd-content" role="main" >
		<h1>타이틀 관리</h1>
			<table class="table">
  			<thead>
   				<tr>
     			 <th scope="col">이미지</th>
     			 <th scope="col">타이틀</th>
    			 <th scope="col">구독자 수</th>
    			 <th scope="col">게시판 수</th>
    			 <th scope="col" style="text-align: center;">관리
    			 <button type="button" class="btn btn-dark btn-sm addTitle" data-toggle="modal" data-target="#addTitleModal">추가</button>
    			 </th>
   				</tr>
  			</thead>
  			<tbody class="tbody">
  			
  			</tbody>
		 </table>
		</div>
	</div>
</div>


<!-- 타이틀 추가 Modal -->
<div class="modal fade" id="addTitleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Title 추가</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body addTitleModal">
      		<div class="form-group">
      		  <input type="file" class="btn btn-outline-dark" id="titleModalimage">
      		  <small id="emailHelp" class="form-text text-muted">Title 이미지를 추가 해주세요.</small>
      		</div>
        	  		
      		<div class="form-group">
             <label for="title">Title 명</label>
   		     <input type="text" class="form-control" id="titleModalTitle">
      		</div>	
      </div>
      <div class="modal-footer">
     	 <button type="button" class="btn btn-dark addTitleButton">확인</button>
      </div>
    </div>
  </div>
</div>

<!-- 타이틀 수정 modal -->
<div class="modal fade modifyTitleModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Title 수정</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<input type="hidden" class="modifyTitleModal_Num">
      	<input type="hidden" class="modifyTitleModal_Img">
      		<div class="form-inline">
      		  <label for="title" class="mr-sm-2">이미지 변경</label>
      			 <input type="file" class="btn btn-outline-dark modifyTitleModal_ImgGo" >
      			 <button class="btn btn-danger modifyTitleModal_TitleButton3 " type="submit">수정</button>
     			 <button class="btn btn-dark modifyTitleModal_TitleButton4 " type="submit">확인</button>
      		</div>
      		<label for="title">Title 변경</label>
      		<div class="form-inline">
      			<input class="form-control mr-sm-2 modifyTitleModal_Title" type="text" readonly="readonly" >
     			 <button class="btn btn-danger modifyTitleModal_TitleButton1 " type="submit">수정</button>
     			 <button class="btn btn-dark modifyTitleModal_TitleButton2 " type="submit">확인</button>
      		</div>
      </div>
    </div>
  </div>
</div>

<!-- 핸들러  -->
<script type="text/x-handlebars-template" id="adminTitleTemplate">
	{{#each .}}
   			    <tr class="templateTr">
     			 <td><img alt="이미지" src="/displayFile?fileName={{title_img }}" class="userImage" style="border:1px solid black;">
     			 </td>
      			 
      			 <td style="vertical-align: middle;">{{title }}
      			 </td>
      			 
      			 <td style="vertical-align: middle;">{{readUser_cnt }}명</td>
      			 <td style="vertical-align: middle;">{{board_cnt }}</td>
      			 <td style="vertical-align: middle; text-align: center;">
					<button type="button" class="btn btn-primary btn-sm modifyModal" title_img="{{title_img}}" title="{{title}}" num="{{num}}" }">수정</button>
      			 	<button type="button" class="btn btn-danger btn-sm deleteTitle" onclick="deleteTitle('{{title}}');">삭제</button>
      			 </td>
    			</tr>
   {{/each}}
</script>
<%@include file="../include/footer.jsp" %>	
</body>
</html>