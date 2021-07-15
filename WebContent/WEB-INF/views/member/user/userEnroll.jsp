<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/userView.css" />
		 <form action="<%=request.getContextPath()%>/user/enroll" method="post" name="userJoinFrm" class="userJoinFrm">
		 <div class="user-notice1">
			<p class="pw-update-title">회원 가입</p>
			<p class="pw-update">첫 방문을 진심으로 환영합니다!</p>
				<p class="pw-up">
				클래스 수강 또는 개설을 원하신다면 회원가입을 진행해 주세요.
				<br />입력하신 비밀번호는 운영자 또한 알 수 없도록 암호화됩니다.</p>
		</div>
		<div class="enroll" id="enroll">
		 	<input type="text" id="Id" name="Id" placeholder="아이디를 입력해주세요"/>
		 	<label for="Id" class="h-regi" id="f-id">아이디 : </label>
		 	<br />
		 	<input type="password" name="pw" id="userPassword" placeholder="비밀번호를 입력해주세요"/>	
		 	<label for="pw" class="h-regi">비밀번호 : </label>	
		 	<br />	
		 	<input type="password" name="userPasswordCheck" id="userPasswordCheck"placeholder="비밀번호를 다시 입력해주세요." />
		 	<label for="password" class="h-regi">비밀번호 확인 : </label>	
		 	
		 	<br />
		 	<input type="text" name="userName" id="userName" placeholder="이름을 입력하세요" />
		 	<label for="userName" class="h-regi">이름 : </label>	
		 
		 	<br />
		 	<select name="gender" id="gender">
		 		<option value="M">남자</option>
		 		<option value="F">여자</option>
		 	</select>
		 	<label for="gender" class="h-regi">성별 : </label>
		 	<br />
		 	<input type="date" name="birthday" id="birthday" placeholder="생일 " required />
		 	<label for="birthday" class="h-regi">생일 : </label>
		 	<br />
		 	<input type="email" placeholder="abc@xyz.com" name="email" id="email" />		 	
		 	<label for="email" class="h-regi">이메일 : </label>
		 	
		 	<br />
		 	<input type="text" name="address" id="address" /> 	
		 	<label for="address" class="h-regi">주소 : </label>
		 	<br />
		 	<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" />
		 	<label for="phone" class="h-regi">연락처 : </label>
			 <div class="fontee">
		 <div class="check_font" id="id_check"></div>
		 <div class="check_font" id="password_check"></div>
		 <div class="check_font" id="name_check"></div>
		 <div class="check_font" id="email_check"></div>
		 <div class="check_font" id="phone_check"></div>
		 </div>
		 <div class="regi-btn-wrapper">
		 	<input type="submit" value="회원 가입" id="register-btn1"/>
		 	<input type="reset" value="초기화" id="register-btn2" />
		 	<input type="button" id="revoke" value="뒤로가기" onclick="location.href='<%=request.getContextPath()%>'" >
		 </div>
		 </div>
		 </form>
		 
		 
	<script type="text/javascript">
	/* ajax flag  */
		var userIdcheck = false;
		var phoneCheck = false;
		var emailCheck = false;
	
			
	   /*핸드폰 실시간 중복검사  */
	   var $phone =   $("#phone").blur(function(){
		 var phone = $('#phone').val();
		
		 console.log(phone)
		
		 $.ajax({
			 url : '<%= request.getContextPath()%>/user/userPhoneCheck',
			 type : 'get',
			 data : {"checkPhone" : phone},
			 dataType:"text",
			 success : function(data) {
		
				 console.log("1 = 중복 / 0 = 중복 x"+data);
				 if(data == "true" && phone != "") {
					 $("#phone_check").text("이미 사용중인 전화 번호 입니다.");
					 $("#phone_check").css("color", "red");
					 
					 
				 }else if(data == "false" && phone != ""){
					 $("#phone_check").text("전화번호 사용이 가능합니다.");
					 $("#phone_check").css("color", "blue");
					 phoneCheck = true;
				 }
			 },
			 error : function(xhr, textStatus, err){
				 /* console.log("1 = 중복 / 0 = 중복 x"+data) ; */
				 alert("에러입니다")
				 console.log(xhr, textStatus, err);
			 }
			 });
		 });
	   
	   /*이메일 실시간 중복검사  */
	   $("#email").blur(function(){
		 var email = $('#email').val();
		
		 console.log(email)
		
		 $.ajax({
			 url : '<%= request.getContextPath()%>/user/userEmailCheck',
			 type : 'get',
			 data : {"checkEmail" : email},
			 dataType:"text",
			 success : function(data) {
		
				 console.log("1 = 중복 / 0 = 중복 x"+data);
				 if(data == "true" && email != "") {
					 $("#email_check").text("이메일이 중복되었습니다.");
					 $("#email_check").css("color", "red");
					 
					 
				 }else if(data == "false" && email != ""){
					 $("#email_check").text("이메일 사용이 가능합니다.");
					 $("#email_check").css("color", "blue");
					 emailCheck = true;
				 }
			 },
			 error : function(xhr, textStatus, err){
				 /* console.log("1 = 중복 / 0 = 중복 x"+data) ; */
				 alert("에러입니다")
				 console.log(xhr, textStatus, err);
			 }
			 });
		 });
			
   
	   /*아이디 실시간 중복검사  */
	 $("#Id").blur(function(){
		 var user_id = $('#Id').val();
		
		 console.log(user_id)
		
		 $.ajax({
			 url : '<%= request.getContextPath()%>/user/userIdCheck',
			 type : 'get',
			 data : {"checkId" : user_id},
			 dataType:"text",
			 success : function(data) {
		
				 console.log("1 = 중복 / 0 = 중복 x"+data);
				 if(data == "true" && user_id != "" ) {
					 $("#id_check").text("아이디가 이미 존재 합니다.");
					 $("#id_check").css("color", "red");
					 
					 
				 }else if(data == "false" && user_id != ""){
					 $("#id_check").text("아이디 사용이 가능합니다.");
					 $("#id_check").css("color", "blue");
					 
					 userIdCheck = true;
				 }
			 },
			 error : function(xhr, textStatus, err){
				 /* console.log("1 = 중복 / 0 = 중복 x"+data) ; */
				 alert("에러입니다")
				 console.log(xhr, textStatus, err);
			 }
			 });
			
			 
		 });
	   var nameCheck = false;
	   
	 	$('#userName').keyup(function () {

	        if (/^[가-힣]{2,5}$/.test($('#userName').val())) {
	            $('#name_check').html('올바른 이름입니다.');
	            $('#name_check').css('color', 'blue');
	            nameCheck = true;

	        } else {
	            $('#name_check').html('한글 2~5글자 입력해주세요');
	            $('#name_check').css('color', 'red');
	            
	            nameCheck = false;
	        }
	 	});
			
		/* 회원가입 submit  */
		$("[class=userJoinFrm]").submit(function(){
			var $userId = $("#userId");
			var $password = $("#password");
			var $birthday = $("#birthday");
			
			if(nameCheck == false){
				alert("양식에 맞게 기입해주세요");
				console.log(nameCheck)
				return false;
			}
			if((phoneCheck == false || emailCheck == false || userIdCheck == false)){
	
				alert("양식에 맞게 기입해주세요");
				return false;
			}
	
			
			return true;
		});
	
		var pcheck = false;
		var ppcheck = false;
	
		 //비밀번호
	    $('#userPasswordCheck').keyup(function () {
	        var pwdExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z]).*$/
/* 	        var pwdExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/ */
	        console.log(pwdExp.test($('#userPassword').val()));
	        if (pwdExp.test($('#userPassword').val())) {
	            $('#password_check').html('올바른 비밀번호 형식입니다.');
	            $('#password_check').css('color', 'blue');
	            pcheck = true;
// 	            $('#userPasswordCheck').keyup(function () {

	    	        if ($('#userPassword').val() != $('#userPasswordCheck').val()) {
	    	            $('#password_check').html('비밀번호 일치하지 않습니다.');
	    	            $('#password_check').css('color', 'red');
	    	            // $('#userPwd').focus();
	    	            ppcheck = false;
	    	        } else {
	    	            $('#password_check').html('비밀번호 일치합니다');
	    	            $('#password_check').css('color', 'blue');
	    	            ppcheck = true;
	    	        }

// 	    	    });
	        } else {
	            $('#password_check').html('영문자, 숫자를 하나 이상 입력하세요.');
	            $('#password_check').css('color', 'red');
	            pcheck = false;
	        }

	    });

	
	</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>

