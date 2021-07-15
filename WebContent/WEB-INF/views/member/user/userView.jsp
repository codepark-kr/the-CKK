<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
User u = (User)session.getAttribute("memberLoggedIn");
%>    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/userView.css" />
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<body>
<div class="wrapper-user-view">
		<div class="user-notice1">
            <form id="user-frm">
                <p class="notice-p">회원 정보 수정</p>
                    <div class="modi-inner">
			<div class="user" id="user-input1">
				<label for="userId" id="register-id">아이디 : </label>
				<input type="text" name="userId" id="userId" value="<%= u.getUserId() %>" readonly/>
			</div>
			<br />
			<div class="user" id="user-input2">
				<label for="userName">이름 : </label>	
				<input type="text" name="userName" id="userName" value="<%= u.getUserName() %>" required/>
			</div>
			<br />
			<div class="user" id="user-input3">
				<label for="gender">성별 : </label>
				<select name="gender" id="gender">
					<option value="M" <%= "M".equals(u.getGender()) ? "selected" : "" %>>남</option>
					<option value="F" <%= "F".equals(u.getGender()) ? "selected" : "" %>>여</option>
				</select>
			</div>
			<br />
			<div class="user" id="user-input4">
				<label for="phone">연락처 : </label>
				<input type="tel" name="phone" id="phone" value="<%= u.getPhone() %>" required/>
			</div>
			<br />
			<div class="user" id="user-input5">
				<label for="birthday">생일 : </label>
				<input type="date" name="birthday" id="birthday" value="<%= u.getBirthday() %>" required/>
			</div>
			<br />
			<div class="user" id="user-input6">
				<label for="email">이메일 : </label>
				<input type="email" name="email" id="email" value="<%= u.getEmail() %>" required/>
			</div>
			<br />
			<div class="user" id="user-input6">
				<label for="address">주소 : </label>
				<input type="text" name="address" id="address" value="<%= u.getAddress() %>"/>
			</div>
			<br />
        </div>
        <div class="modi-inner-btn">
			<input type="button" class="modi-btn1" value="정보 수정" onclick="updateUser();" />
			<input type="button" class="modi-btn2" value="회원 탈퇴" onclick="deletUser();" />
            <input type="button" class="modi-btn3" value="비밀번호 수정" onclick="updatePassword();"/>
        </div>
		</form>
    </div>
</div>

	<!-- 회원탈퇴용폼 : 아이디 제출용 -->
	<form id="deleteUserFrm" action="<%=request.getContextPath()%>/user/delete" method="post">
	<input type="hidden" name="userId" value="<%=u.getUserId() %>" /></form>

<script>

//회원 정보 수정
function updateUser(){
	//제출 전에 유효성 검사 필요합니다. - 시작
		//2. 이메일 유효성 검사 -> /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]*.[a-zA-z]{2,3})$/i
		var email = document.getElementById("email");
		if((/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]*.[a-zA-z]{2,3})$/i).test(email.value) == false){
			alert("유효한 이메일 주소를 입력해주세요!");
			return false;
			email.select();
		}
		//3. 휴대폰 번호 유효성 검사 -> /^01(?:0|1|[6-9])([0-9]{3}|[0-9]{4})([0-9]{4})$/
		var phone = document.getElementById("phone");
		if((/^01(?:0|1|[6-9])([0-9]{3}|[0-9]{4})([0-9]{4})$/).test(phone.value) == false) {
			alert("유효한 핸드폰 번호를 입력해주세요!");
			return false;
			phone.select();
		}
		//4. 주소 유효성 검사
		var address = document.getElementById("address");
		if((/[가-힣]{1,}/).test(address.value) == false){
			alert("유효한 주소를 입력해 주세요!");
			return false;
			address.select();
		}
	//유효성 검사 - 끝
	var userFrm = document.getElementById("user-Frm");
	//console.log(userFrm);
	userFrm.setAttribute("action", "<%= request.getContextPath() %>/user/update");
	userFrm.setAttribute("method", "POST");
	userFrm.submit();
	
};

//회원 삭제
function deletUser(){
	//삭제 여부 확인
	if(confirm("정말로 탈퇴하시겠습니까?")){
		var delFrm = document.getElementById("deleteUserFrm");
		delFrm.submit();
		
	}
};

//비밀번호 변경 -> get방식으로 진행
//버튼 클릭 시 세션에 로그인 되어있는 Id값을 가지고 비밀번호 변경 페이지로 우회 후 진행
function updatePassword(){
	
	location.href = "<%= request.getContextPath() %>/user/updatePassword?userId=<%=u.getUserId()%>";
	
};

</script>	
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>