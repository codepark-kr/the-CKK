<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/userView.css" />
<title>CKK :: updatePwd :: TEMP</title>
<style>


</style>
</head>

<body>
	<div class="wrapper-user-updatePassword">
		<form id="pw-frm" action="<%= request.getContextPath() %>/user/updatePassword" method="post">
		<div class="user-notice1">
			<p class="pw-update-title">비밀번호 변경</p>
			<p class="pw-update">안전한 비밀번호로 내정보를 보호하세요.</p>
				<p class="pw-up">다른아이디/사이트에서 사용한적이 없는 비밀번호, <br /> 
				이전에 사용한 적이 없는 비밀번호가 안전합니다.</p>
		</div>
				<div class="user-div1">
					<label for="password">현재 비밀번호</label>
					<input type="password" name="password" id="password" required />
				</div>
				<br />
				<div class="user-div2">
					<label for="new_password">새 비밀번호</label>
					<input type="password" name="new_password" id="new_password" required />
				</div>
				<br />
				<div class="user-div3">
					<label for="chk_password">새 비밀번호 확인</label>
					<input type="password" name="chk_password" id="chk_password" required  />
				</div>
				<br />
				<input type="submit" value="변경하기" id="pw-change" onclick="return updateP();"/>
				<input type="button" value="돌아가기" id="pw-return" onclick=""/>
				<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId()%>" />
		</form>
	</div>
<script>
function updateP(){
	/*제출하기 전 실행 되어야될 것
		1. 비밀번호 유효성 검사
		2. 새 비밀번호 == 새 비밀번호 확인 일치여부 
	*/
	console.log("hello");
	var newPwd = document.querySelector("[name=new_password]").value.trim();
	var newPwdChk = document.querySelector("[name=chk_password]").value.trim();
	console.log(newPwd, newPwdChk);
	
	//새 비밀번호 유효성 검사
	if((/.{8,15}/ && /\w/ig && /[!@$%^&*()_+}{":?><|~"}]/).test(newPwd) == false){
		alert("비밀번호가 유효하지 않습니다. \n 비밀번호는 숫자, 영문자, 특수문자 조합 8자 이상 15자 이하로 작성해주십시오.");
		document.querySelector("[name=new_password]").select();
		return false;
	}
	
	//비밀번호 일치 여부 체크 
	if(newPwd != newPwdChk){
		alert("비밀번호가 일치하지 않습니다.");
		document.querySelector("[name=chk_password]").select();
		return false;
	}
	
	return true;
	
};
</script>
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>