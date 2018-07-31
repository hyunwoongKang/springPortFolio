<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<link href="/resources/main/css/user.css?ver=7" rel="stylesheet"
	type="text/css" />
<!--자바 스크립트-->
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		var nickNameTemp = "";
		//이메일 중복 확인

		//닉네임 중복 확인 
		$(".duplication_nick").on("click",function(event) 
		{
			//nickname
			var nickName = $("#nickName").val();

			if (!check2('nickName',/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z0-9]{2}$/i,"특수 문자 및 숫자 미포함 2자리 이상")) 
			{
				return false;
			}
			    event.preventDefault();

			$.ajax({
				type : "POST",
				url : "/user/nickNamecheck",
				data : {nickName : nickName},
				success : function(data) 
				{
					if (data == "yes") 
					{
						alert("사용가능한 닉네임 입니다.");
						nickname_check = true;
						nickNameTemp = $("#nickName").val();
					} else
					{
						alert("중복되는 아이디 입니다.");
					}
				},
				error : function() 
				{
						alert("문제가 있음.");
				}

				    });

			});

			//회원가입 이메일 인증번호
			var email_key = "";
			var email_check = false;
			var nickname_check = false;
			$("#email_key_div").hide();

			//이메일 발송번호 확인버튼
			$(".email_key_button").on("click", function(event) 
			{
			    event.preventDefault();

				if($("#email_key").val() == email_key)
				{
					alert("인증이 완료 되었습니다.");
					$("#email_key_div").hide();
					$("#email").attr("readonly", "readonly");
					email_check = true;
				} 
				else
				{
					alert("인증번호를 확인 해주세요.")
				}
			});

			//이메일 인증 
			$(".email_check_button").on("click",function(event)
			  {

				if(!check2('email',/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i,"올바른 이메일 형식에 맞게 입력해주세요."))
				{
					return false;
				}
				event.preventDefault();
				var email = $("#email").val();

				$.ajax({
					type : "POST",
					url : "/user/emailcheck",
					data : {email : email},
					success : function(data)
					{
						if (data == "yes") 
						{
							sendMail(email);
						} else 
						{
							alert("이미 가입된 이메일 입니다.");
						}
					}

					});

			});

			//email 보내기
			function sendMail(email) 
			{
				$.ajax({
				type : "POST",
				url : "/mail/emailSender",
				data : {email : email},
				dataType : "JSON",
				success : function(data) 
				{
				alert("인증 번호가 발송되었습니다.");
				email_key = data.number;
				$("#email_key_div").show();

				},
				error : function() 
				{
					alert("인증 번호 발송이 실패 하였습니다.");
				}
			  });
			}

			//회원가입 폼 체크 
			$("#joinForm").submit(function() {
					//정규식
					//null check
					var checkList = new Array('email','upw', 'reupw', 'nickName','name', 'phone','InterstList');
					var endN = checkList.length;

				 	for (var i = 0; i < endN; i++) 
					{
					   if (!check1(checkList[i])) 
					    {
							return false;
						}
					 }
					 
						
					//email 
					if(!check2('email',/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i,"올바른 이메일 형식에 맞게 입력해주세요."))
					{
						return false;
					}

					//이메일인증 확인 버튼
					if(!email_check) 
					{
						alert("이메일 인증 확인을 해주세요.");
						return false;
					}

					//passowrd
					if (!check2('upw',/^[a-zA-z]+[a-zA-z0-9]{5}$/g," 영문+숫자를 조합해서 6자리 이상을 입력해주세요.")) 
					{
						return false;
					}

					//password2
					if (!check2('reupw',/^[a-zA-z]+[a-zA-z0-9]{5}$/g," 영문+숫자를 조합해서 6자리 이상을 입력해주세요.")) 
					{
						return false;
					}

					if ($("#upw").val() != $("#reupw").val()) 
					{
						$(".reupw1").html("");
						$(".reupw1").html(" 비밀번호을 다시 확인해주세요.");
						$("#reupw").focus();
						return false;
					} 
					else
					{
						$(".reupw1").html("");
					}

					//nickname
					if (!check2('nickName',/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z0-9]{2}$/i," 2자리 이상")) 
					{
						return false;
					}

					//중복 확인 후 닉네임 변경 시
					if (nickNameTemp != $("#nickName").val()) 
					{
						nickname_check = false;
					}

					//닉네임 중복 확인버튼
					if (!nickname_check)
					{
						alert("닉네임 중복 확인을 해주세요.");
						return false;
					}

					//name
					if (!check2('name',/[-_\.0-9]?[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z]{2}$/i," 특수 문자 및 숫자 미포함 2자리 이상")) 
					{
						return false;
					}
					//phone
					if (!check2('phone',/^\d{3}-\d{3,4}-\d{4}$/," - 을 포함하여 올바른 형태로 입력해주세요.")) 
					{
						return false;
					}

					});

					//정규식 
					function check2(name, chk, msg) {
						var id = $("#" + name).val();
						var span = $("." + name + 1);

						if (chk.test(id)) 
						{
							span.html("");
							return true;
						}
						
						span.html(msg);
						$("#" + name).focus();
						return false;

						}

						//회웍가입 폼 체크 아무것도 없을때 체크
						function check1(name) {
							var id = "#" + name;
							var span = "." + name;

							if ($(id).val().length == 0 || $(id).val() == '' || (name == "InterstList" && $(id+ '[type="checkbox"]:checked').length == 0)) 
							{

								$(span).css("color", "red");
								$(span + 1).html("- 문자를 입력해 주세요.");
								if (name == "InterstList") 
								{
									$(span + 1).html("- 하나 이상 체크해주세요..");
									return false;
								}
								$(id).focus();
								return false;

							} 
							else 
							{
								$(span).css("color", "black");
								$(span + 1).html("");
								return true;
							}

						}

					});
</script>

</head>
<body>
	<%@include file="../include/header.jsp"%>



	<div class="joinUser">
		<div class="joinUser2">

			<form action="/user/joinUser" method="post" id="joinForm">

				<div class="joinForm" id="joinForm_top">
					<h1>회원가입</h1>
				</div>
				<br>

			 	<div class="joinForm">
					<span class="email">이메일</span> <br> 
					<input type="text" id="email" placeholder="ex)asdbcv@naver.com" name="email">
					<button class="btn btn-outline-primary btn-sm email_check_button">인증번호</button>
					<span class="email1"></span><br>
					<div id="email_key_div">
						<input type="text" id="email_key" placeholder="대소문자 구분해 주세요.">
						<button class="btn btn-outline-primary btn-sm email_key_button">확인</button>
					</div>
				</div> 
				
				<div class="joinForm">
					<span class="upw">비밀번호</span><br> 
					<input type="password" id="upw" name="upw" placeholder="하나 이상의 영문 숫자를 조합해주세요.">
					<span class="upw1"></span>
				</div>

				<div class="joinForm">
					<span class="reupw">비밀번호 확인</span> <br> 
					<input type="password" id="reupw" placeholder="하나 이상의 영문 숫자를 조합해주세요.">
					<span class="reupw1"></span>
				</div>

				<div class="joinForm">
					<span class="nickName">닉네임</span> <br> 
					<input type="text" id="nickName" name="nickName" placeholder="닉네임을 입력해주세요.">
					<button type="button" class="btn btn-outline-primary btn-sm duplication_nick">중복확인</button>
					<span class="nickName1"></span>
				</div>


				<div class="joinForm">
					<span class="name">이름</span> <br>
					 <input type="text" name="name" id="name" placeholder="이름을 입력해주세요.">
					 <span class="name1"></span>
				</div>



				<div class="joinForm">
					<span class="phone">핸드폰</span><br> 
					<input type="text" id="phone" placeholder="ex)010-1234-2429" name="phone">
					<span class="phone1"></span>
				</div>

				<div class="joinForm">
					<span class="InterstList">관심분야</span> <span class="InterstList1"></span>
					<div class="joinForm_radio">&nbsp;&nbsp;
						<c:forEach var="title" items="${title }">
							<div class="form-check form-check-inline" style="width: 100px;">
 							 <label class="form-check-label">
    						<input class="form-check-input" type="checkbox" id=InterstList name=interestList value="${title.title }"> ${title.title }
  							</label>
							</div>
						</c:forEach>
					</div>
				</div>

				<div class="" id="joinFormBottom">
					<button type="submit" class="btn btn-primary btn-sm">확인</button>
					<button type="button" class="btn btn-secondary btn-sm">취소</button>
				</div>
			</form>
		</div>
	</div>

	<%@include file="../include/footer.jsp"%>
</body>
</html>