<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
<script src="/resources/main/js/jsbn.js"></script>
<script src="/resources/main/js/prng4.js"></script>
<script src="/resources/main/js/rng.js"></script>
<script src="/resources/main/js/rsa.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<link href="/resources/main/css/main.css?ver=33" rel="stylesheet"
	type="text/css" />

<script type="text/javascript">
$(document).ready(function() 
	{
		$(".nickNameCheck").hide();
		$(".userImageUpdate").hide();
		$(".imageCheck").hide();
		$("#modifyButton").hide();

		var str = new Array();
		var titleCount;
		var tempStartNum = 0;
		
		$.ajax({
		    url : "/title/getTitle",
			type : "get",
			dateType : "JSON",
			success : function(data) 
				{
				$.each(data,function(key, value) 
				 {
				str[key] = "<li><a href='/board/list?title="+ data[key].title+ "' class='topMenu'>"+ data[key].title+ "</a></li>";
				
				titleCount = key;
				
				});
				getTitle(0);
				}

			});

						function getTitle(startNum) 
						{
							var endNum = startNum + 9;
							var title = "<ul>";

							var calc = ((parseInt((titleCount) / 9)) + 1) * 9;

							if (calc < endNum) {
								endNum = calc;
								startNum = endNum - 9;
								tempStartNum = startNum;
							}

							if (startNum <= 0) {
								startNum = 0;
								endNum = 9;
								tempStartNum = 0;
							}
							for (var i = startNum; i < endNum; i++) {
								if (str[i] != null) {
									title += str[i];
								}
							}
							title += "</ul>";
							$(".titleUl").append(title);

						}

						$(".button-previous").on("click", function() 
						{
							$(".menubar ul").remove();
							tempStartNum -= 9;
							getTitle(tempStartNum);

						});

						$(".button-next").on("click", function() {
							$(".menubar ul").remove();
							tempStartNum += 9;
							getTitle(tempStartNum);
						});

						//로그인 & 아이디 찾기 & 비밀번호 찾기
						$(".loginButton").on("click",function() 
								{
											//비밀번호 찾기
								var findUpw_Email = $("#findUpw_Email").val();
											var findUpw_Name = $("#findUpw_Name").val();
											var findUpw_Phone = $("#findUpw_Phone").val();
											
											//로그인
											var loginEmail = $("#loginEmail").val();
											var loginUpw = $("#loginUpw").val();

											//rsa 암호화 생성
											var rsa = new RSAKey();
											rsa.setPublic('${modulus}','${exponent}');

											//로그인 하기
											if (division == "로그인") {
												loginEmail = rsa.encrypt(loginEmail);
												loginUpw = rsa.encrypt(loginUpw);

												$.ajax({
															url : "/user/login",
															type : "post",
															dataType : "text",
															data : {email : loginEmail,upw : loginUpw},
															success : function(data) {
																if (data == 'success') 
																{
																	alert("로그인 되었습니다.");
																	location.reload();
																}
															},
															error : function(error) 
															{
																alert(error);
															}

														});
											}
											
											//이메일 찾기
											var findEmail_Name = $("#findEmail_Name").val();
											var findEmail_Phone = $("#findEmail_Phone").val();

											if (division == "이메일") {
												$.ajax({
															url : "/user/findEmail",
															type : "post",
															dataType : "JSON",
															data : {name : findEmail_Name,phone : findEmail_Phone},
															success : function(data) {
																
																if (data.result == 'success') {
																	alert("이메일 찾기를 완료 하였습니다.");
																	alert(data.email);
																	var str = "<div class='form-group'><label for='exampleInputEmail1'>이메일</label>";
																	str += "<input type='email' class='form-control' value='"+data.email+"' readonly='readonly' id='exampleInputEmail1' aria-describedby='emailHelp'>";
																	str += "</div>";
																	$(".findEmailForm").html(str);
																
																} else {
																	alert("등록된 정보가 없습니다.");
																}
															},
															error : function(error) {
																alert("다시 시도해 주세요.");
															}

														});

											}

											if (division == "패스워드") {

												$.ajax({
															url : "/user/findUpw",
															type : "post",
															data : {
																email : findUpw_Email,
																name : findUpw_Name,
																phone : findUpw_Phone
															},
															success : function(data) {
																if (data == 'success') 
																{
																	alert("해당 메일로 임시 비밀번호를 보내드렸습니다.");
																	$("#exampleModal").modal("hide");
																} else 
																{
																	alert("입력하신 정보와 일치하는 정보가 없습니다.");
																}
															},
															error : function(error) 
															{
																alert("입력하신 정보와 일치하는 정보가 없습니다.");
															}

														});
											}

										});
						
						//모달 정보 지우기 
						$('#exampleModal').on('hidden.bs.modal', function (e) {
							$(this).find("input").val("");
							
						});
						
						//회원정보 수정
						$("#modifyUserInfo").on("click", function() {
							$("#userNickName").removeAttr("readonly");
							$("#userPhone").removeAttr("readonly");
							$("#userUpw").removeAttr("readonly");

							$("#userNickName").focus();
							$(".nickNameCheck").show();

							$("#modifyUserInfo").hide();
							$("#modifyButton").show();
						});

						var nickCheck = false;
						var phoneCheck = false;
						var upwCheck = false;

						$("#modifyButton").on("click",function() {
									//nick
									if (!nickCheck) {
										alert("닉네임 중복 체크를 해주세요");
									}

									//upw
									var upw = $("#userUpw").val();

									if (!check2(upw,/^[a-zA-z]+[a-zA-z0-9]{5}$/g," 영문+숫자를 조합해서 6자리 이상을 입력해주세요.")) {
										upwCheck = false;
									} else {
										upwCheck = true;
									}

									//phone
									var phone = $("#userPhone").val();

									if (!check2(phone, /^\d{3}-\d{3,4}-\d{4}$/," - 을 포함하여 올바른 형태로 입력해주세요.")) {
										phoneCheck = false;
									} else {
										phoneCheck = true;
									}

									alert(nickCheck + " : " + phoneCheck+ " : " + upwCheck);

									if (nickCheck == true && phoneCheck == true&& upwCheck == true) {
										$(".modifyUserForm").submit();
									}

								});

						//중복체크
						$(".nickNameCheck").on(
								"click",
								function() {
									var nickName = $("#userNickName").val();

									if (!check2(nickName,/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z0-9]{2}$/i," 특수 문자 및 숫자 미포함 2자리 이상")) {
										nickCheck = false;
										return;
									}

									$.ajax({
										type : "POST",
										url : "/user/nickNamecheck",
										data : {nickName : nickName},
										success : function(data) {
											if (data == "yes") 
											{
												alert("사용가능한 닉네임 입니다.");
												nickCheck = true;
											} else {
												alert("중복되는 아이디 입니다.");
											}
										},
										error : function() {
											alert("문제가 있음.");
										}
									});
								});

						//정규식 
						function check2(val, chk, msg) {

							if (chk.test(val)) {
								return true;
							}
							alert(msg);

							return false;

						}

						//유저 이미지 변경
						$(".userImage").on("click", function() {
							$(".userImageUpdate").show();
							$(".imageCheck").show();
						});

						
						$(".imageCheck").on("click", function() {

							var email = $("#userEmail").val();
							var upw = $("#userUpw").val();
							var file = $(".userImageUpdate")[0].files[0];
							var nickName = $("#userNickName").val();

							var formData = new FormData();
							formData.append("file", file);
							formData.append("email", email);
							formData.append("upw", upw);
							formData.append("nickName", nickName);
							formData.append("division", "userImage");

							$.ajax({
								url : "/uploadUserImage",
								dataType : "text",
								data : formData,
								type : "post",
								processData : false,
								contentType : false,
								success : function(data) {
									alert("성공적으로 변경 되었습니다.");
								}
							});

						});

						//로그인 모달을 제외한 모달 숨기기
						$(".findEmailForm").hide();
						$(".findUpwForm").hide();
						//로그인 a태그 숨기기
						$(".findLogin").hide();

						//구분자
						var division = "로그인";

						//로그인 & 아이디 찾기 & 비밀번호 찾기 모달 
						$(".findUserInfo").on("click", 'a', function(event) {
							event.preventDefault();

							var title = $(this).text();
							//타이틀 바꾸기
							$(".modal-title").html(title);

							//전체 모달 숨기기
							$(".findEmailForm").hide();
							$(".findUpwForm").hide();
							$(".loginForm").hide();

							//a태그 타겟 값(연결된 모달값) 가져오기
							var target = $(this).attr("data-target");
							$(target).show();

							//각 모달에 입력된 값 태그 옮길 시 내용 지우기 
							$(target).find("input").val("");

							//a태그값 전체 보이기
							$(".findLogin").show();
							$(".findEmail").show();
							$(".findUpw").show();

							//선택된 a태그 값만 안보이기
							$(this).hide();

							//구분자
							division = $(this).attr("division");
						});

						$(".userLogout").on("click", function(event) {

							event.preventDefault();

							$.ajax({
								url : "/user/logout",
								dataType : "text",
								type : "get",
								success : function(data) {
									if (data == 'success') {
										alert("로그아웃 처리 되었습니다.");
										location.reload();
									}
								}
							});

						});

				$("#search").on("click",function() 
				{
				   var searchInput = $(".searchInput").val();
				   self.location = "/board/list?search="+ searchInput+"&page=1&perPageNum=20";
				   var temp = "<img alt='recentReadImage' src='../resources/main/img/BoardTitle/rocket.png' class='userImage'><span style='font-weight: bold; padding-left: 10px;'>검색 결과</span>"
				});

});
</script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #800080;"> 
	  <a class="navbar-brand" href="/"><img class="logo" src="/resources/main/img/logoImage.png"></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
 <ul class="navbar-nav mr-auto topbarUl">

	<c:if test="${empty login}">
	   <li class="nav-item"><a class="nav-link" href="#" data-toggle="modal" data-target="#exampleModal" style="color: white;">Login</a></li>
	   <li class="nav-item"><a class="nav-link" href="/user/joinUser" style="color: white;">Join</a></li>
	</c:if>

   <c:if test="${!empty login}">
	 <li class="nav-item"><a class="navbar-brand" href="#" data-toggle="modal" data-target="#userInfo"> 
	  <img src="${login.user_image}" width="38" height="38" alt="" class="user_image">&nbsp;&nbsp;<span class="loginNickName" style="color: white; font-size: medium;">${login.nickName }</span>
		 </a> 
	  <c:if test="${login.adminCheck eq 'Y'.charAt(0) }">
		 <a href="/admin/member" class="boardSet" style="color: white">관리</a>
	  </c:if> 
	     
	     <a href="#" class="userLogout" style="color: white;">Logout</a></li>
	</c:if>
</ul>
	</div>
	</nav>
	
	
	<!--/최상단 header-->
	<div class="topHeader">
		<div class="topHeaderMiddle">
			<div class="topHeaderTitle">
				<img style="width: 80px;" src="/resources/main/img/logoImage.png">
				<span>PORTFOLIO</span>
			</div>
			<div class="topHeaderTitle2">
				<input class="searchInput" type="search" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-warning my-2 my-sm-0" id="search" type="submit">Search</button>
			</div>
		</div>
	</div>

	<!--menu bar-->
	<div class="menubar">
		<a class="button-previous"><img alt=""
			src="/resources/main/img/left.png"></a>

		<div class="titleUl" style="text-align: center;"></div>

		<a class="button-next"><img alt=""
			src="/resources/main/img/right.png"></a>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">로그인</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<!-- 로그인 폼 -->
				<div class="modal-body loginForm">
					<div class="form-group">
						<label for="loginEmail">이메일</label> <input type="email"
							class="form-control" id="loginEmail" aria-describedby="emailHelp"
							placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="loginUpw">비밀번호</label> <input type="password"
							class="form-control" id="loginUpw" placeholder="Password">
					</div>
				</div>

				<!-- 아이디찾기 폼 -->
				<div class="modal-body findEmailForm">
					<div class="form-group">
						<label for="findEmail_Name">이름</label> 
						<input type="text" class="form-control" id="findEmail_Name" aria-describedby="emailHelp" placeholder="Enter Name">
					</div>
					<div class="form-group">
						<label for="fineEmail_Phone">핸드폰 번호</label> <input type="phone" class="form-control" id="findEmail_Phone" placeholder="Ex)010-1234-1234">
					</div>
				</div>

				<!-- 패스워드 찾기 폼 -->
				<div class="modal-body findUpwForm">
					<div class="form-group">
						<label for="findUpw_Email">이메일</label> 
						<input type="email" class="form-control" id="findUpw_Email" aria-describedby="emailHelp" placeholder="Enter email"> 
					</div>

					<div class="form-group">
						<label for="findUpw_Name">이름</label> 
						<input type="text" class="form-control" " id="findUpw_Name" aria-describedby="emailHelp" placeholder="Enter Name">
					</div>

					<div class="form-group">
						<label for="findUpw_Phone">핸드폰 번호</label> 
						<input type="phone" class="form-control" id="findUpw_Phone" placeholder="Ex)010-1234-1234">
					</div>
				</div>


				<div class="modal-footer">
					<div class="findUserInfo">
						<a href="#" class="findLogin" data-target=".loginForm" division="로그인">로그인</a> 
						<a href="#" class="findEmail"data-target=".findEmailForm" division="이메일">이메일 찾기</a> 
						<a href="#" class="findUpw" data-target=".findUpwForm" division="패스워드">비밀번호 찾기</a>
					</div>

					<!--loginButton  -->
					<button type="submit" class="btn btn-success loginButton">확인</button>
				</div>

			</div>
		</div>
	</div>



	<!-- Modal -->
	<div class="modal fade" id="userInfo" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">회원 정보</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<img alt="" src="${login.user_image }" class="userImage"> &nbsp;&nbsp;&nbsp; 
						<input type="file" class="btn btn-outline-dark btn-sm userImageUpdate">
						<button type="button" class="btn btn-outline-dark imageCheck">수정</button>
						<small id="emailHelp" class="form-text text-muted">이미지를 변경 하시려면 이미지를 클릭 해주세요.</small>
					</div>

					<form action="/user/updateUserInfo" method="post"
						class="modifyUserForm">

						<div class="form-group">
							<label for="exampleInputEmail1">이메일</label> 
							<input type="email" class="form-control" id="userEmail" name="email" value="${login.email }" readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">닉네임</label>
							<button type="button" class="btn btn-outline-dark btn-sm nickNameCheck">중복확인</button>
							<input type="text" class="form-control" id="userNickName" name="nickName" value="${login.nickName }" readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">비밀번호</label> <input type="password" class="form-control" id="userUpw" name="upw" value="${login.upw }" readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">이름</label> 
							<input type="text" class="form-control" id="userName" name="name" value="${login.name }" readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">핸드폰</label> <input type="text" class="form-control" id="userPhone" name="phone" value="${login.phone }" readonly="readonly">
						</div>
					</form>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="modifyUserInfo">수정하기</button>
					<button type="button" class="btn btn-primary" id="modifyButton">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
	
	
	