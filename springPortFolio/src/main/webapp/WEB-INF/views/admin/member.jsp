<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>멤버 관리</title>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<link href="../resources/main/css/admin.css?ver=3" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<script type="text/javascript">
 $(document).ready(function(){
	 $(".deleteMember").on("click",function(){
		 var email=$(this).attr("userEmail");
		var removeDiv=$(this).parent("td").parent("tr");
 	 	 $.ajax({
			 type:"delete",
			 url:"/admin/deleteUser",
			 headers:{"Content-Type":"application/json", "X-HTTP-Method-Override":"DELETE"},
			 dataType:"text",
			 data:JSON.stringify({email:email}),
			 success:function(data){
				if(data=='success'){
					alert("삭제가 완료 되었습니다.");
					removeDiv.remove();
				}	 
			 },
			 error:function(data){
				 alert("삭제를 실패 하였습니다.");
			 }
		 });  
	 });
	 
 });
</script>
</head>
<body>
<%@include file="../include/header.jsp" %>	

<div class="container-fluid" >
	<div class="row flex-xl-nowrap" >
		<div class="col-12 col-md-3 col-xl-2 bd-sidebar adminSidebar">	
		   <nav class="nav flex-column">
  			 <a class="nav-link active" href="/admin/member">회원 관리</a>
  			 <a class="nav-link" href="/admin/title">타이틀 관리</a>
		  </nav>
		</div>
		
		<div class="col-12 col-md-9 col-xl-9 py-md-3 pl-md-5 bd-content" role="main" >
		<h1>회원 관리</h1>
			<table class="table">
  			<thead class="thead-dark">
   				<tr>
     			 <th scope="col">이미지</th>
     			 <th scope="col">이메일</th>
    			 <th scope="col">닉네임</th>
    			 <th scope="col">포인트</th>
    			 <th scope="col">추방</th>
   				</tr>
  			</thead>
  			<tbody>
  			<c:forEach items="${allUser }" var="user">
   			    <tr>
     			 <td><img alt="" src="${user.user_image }" class="userImage" style="border:1px solid black;"></td>
      			 <td style="vertical-align: middle;">${user.email }</td>
      			 <td style="vertical-align: middle;">${user.nickName }</td>
      			 <td style="vertical-align: middle;">${user.point} 점</td>
      			 <td style="vertical-align: middle;"><button type="button" class="btn btn-danger btn-sm deleteMember" userEmail=${user.email }>추방</button></td>
    			</tr>
		   </c:forEach>    		
  			</tbody>
		 </table>
		</div>
	</div>
</div>


<%@include file="../include/footer.jsp" %>	
</body>
</html>