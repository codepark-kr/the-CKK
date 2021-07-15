<%@page import="user.vo.CommissionGrade"%>
<%@page import="user.member.model.service.UserService"%>
<%@page import="user.member.controller.*"%>
<%@page import="user.vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- (예빈)기존의 header와 구조상 완전히 동일하나 색상을 블랙 테마로 변경한 특수 헤더. 스케줄 게시판에만 사용된다. -->
    <%
	User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
    String adminGrade = UserService.USER_ADMIN;

	//쿠키확인 : 요청과 함께 전송된 쿠키확인
	boolean saveId = false;
	String userId = "";
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
		for(Cookie c : cookies){
			String key = c.getName();
			String value = c.getValue();
			if("saveId".equals(key)){
				saveId = true;
				userId = value;
			}
		}
	}	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/dark-header.css" />
 <script src="<%= request.getContextPath() %>/js/jquery-3.5.1.js"></script>
    <script>
        $(function(){
	$("#user-login-form").submit(function(){
		var $userId = $("#input-login-id");
		var $password = $("#input-login-password");
		
		if(/^.{4,}$/.test($userId.val()) == false){
			alert("유효한 아이디를 입력하세요.");
			return false;
		    }
		if(/^.{4,}$/.test($password.val()) == false){
			alert("유효한 패스워드를 입력하세요.");			
			return false;
		    }
		return true;
	        });
        });
        
        // 로그인 드롭다운 
        function mypage(){
            $("#mypage-login").slideToggle();    
            $("#mypage-loggedIn").slideToggle();    
        }
        // 비밀번호 변경 시 js 효과
/*         function lostPw(){
            $("#mypage-login").slideUp();
            $("#mypage-lostpw").slideDown('300', 'linear');
        }; */
        // 비밀번호 변경 시 정보 체크
        function infoChk(){
            var INFO = 1;
            if(INFO = 1){
                alert("비밀번호 변경이 완료되었습니다!");
                 $("#mypage-lostpw").slideUp();
                $("#mypage-lostpw").css("display", "none.");
                $("#mypage-login").slideDown(); 

            }else{
                alert("입력하신 정보가 고객님의 회원 정보와 일치하지 않습니다.\n다시 확인해 주세요.");
            }
        };

        $(function(){
        	$("#logout").click(function(){
        		$("#mypage-login").slideToggle();
        		$("#mypage-loggedIn").slideToggle();
        		var result = confirm("로그아웃 하시겠습니까?");
            	if(result){
            		location.href = "<%= request.getContextPath() %>/user/logout";
            		alert("성공적으로 로그아웃 되었습니다!");
            	}
        	});
        });
        function adminLogout(){
        	var result = confirm("로그아웃 하시겠습니까?");
        	if(result){
        		location.href = "<%= request.getContextPath() %>/user/logout";
        		alert("성공적으로 로그아웃 되었습니다!");
        	}
        };
        //비밀번호 재설정
        function lostPw(){
        	location.href = "<%=request.getContextPath()%>/user/checkInfo";
        	
        };
        
        //리뷰팝업
	        function writeReview(){
        	var url = "<%=request.getContextPath()%>/user/writeReview";
        	var title = "WriteReviewPopup";
        	var status = "left=366px, top=100px, width=700px, height=450px";
        	
        	open(url, title, status);
        }; 
        
        function msg(){
        	var url = "<%= request.getContextPath() %>/user/message?userId=<%= memberLoggedIn != null ? memberLoggedIn.getUserId() : "" %>";
        	var title = "My Message";
        	var status = "left= 366px, top=100px, width=700px, height=450;";
        	open(url, title, status);
        };
    </script>
    <style>
	  @font-face {
        font-family: 'IBMPlexSansKR-Regular';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Regular.woff') format('woff');
        font-weight: normal;
        font-style: normal;
        }  
        html, body{
        font-family: 'IBMPlexSansKR-Regular';
        }
    </style>
</head>
<body>
<!-- 회원이 현재 로그인하지 않은 경우, 즉 비회원인 경우 -->
<% if (memberLoggedIn == null){ %>	
    <div class="wrapper-user-header">
        <div class="user-header">
            <div class="user-navbar">
                    <div class="navbar-user-menu">
                <a href="<%= request.getContextPath()%>"><button id="logo-btn" class="logo-btn">cooking-king</button></a>
            <button class="user-navbar1" onclick="location.href='<%= request.getContextPath() %>/board/list'">클래스</button>
            <button class="user-navbar2" onclick="location.href='<%=request.getContextPath()%>/member/schedule'">내 스케줄</button>
            <button class="user-navbar4" id="mypage-btn" onclick="mypage();">로그인</button>
            <button class="user-navbar5" onclick="">고객센터</button>
                    </div>
            </div>
       <div class="login-wrapper">
            <div class="mypage-login" id="mypage-login">
                <p id="welcome-back" class="welcome-back">WELCOME!</p>
                <form class="user-login-form" id="user-login-form"
                action="<%= request.getContextPath() %>/user/login"
					  method="POST">
                    <input type="text" class="input-login-id" id="input-login-id" name="userId"
                        placeholder="아이디를 입력하세요." value="<%= saveId ? userId : ""%>" required><br>
                    <input type="password" class="input-login-password" id="input-login-password" name="password"
                        placeholder="비밀번호를 입력하세요." required><br>
                        <a href="#" id="lost-pw" class="lost-pw-question" onclick="lostPw();">비밀번호를 잊으셨나요?</a>
                        <br />
                        <a href="<%= request.getContextPath()%>/user/enroll" 
                        id="register-q" class="register-question">또는 회원가입을 원하시나요?</a>
                        <br />
                    <input type="submit" value="로그인" id="login-btn" class="login-btn" onclick="login();" required><br>
                </form>
           </div>
     </div>
</div>
<!-- 여기까지 확인 완료 -->
  <% } else if (memberLoggedIn != null && !memberLoggedIn.getCommGrade().equals(adminGrade)){ %>
      <div class="wrapper-user-header">
        <div class="user-header">
            <div class="user-navbar">
                    <div class="navbar-user-menu">
                <a href="<%= request.getContextPath()%>"><button id="logo-btn" class="logo-btn">cooking-king</button></a>
            <button class="user-navbar1" onclick="location.href='<%= request.getContextPath() %>/board/list'">클래스</button>
            <button class="user-navbar2" onclick="location.href='<%=request.getContextPath()%>/member/schedule?userId=<%=memberLoggedIn.getUserId()%>'">내 스케줄</button>
            <button class="user-navbar4" id="mypage-btn" onclick="mypage();">마이페이지</button>
            <button class="user-navbar5" onclick="location.href='<%= request.getContextPath()%>/center/list'">고객센터</button>
                    </div>
            </div>
      <div class="mypage-loggedIn" id="mypage-loggedIn">
   		<div class="hello" id="hello">
   			<p class="hello-p"><%= memberLoggedIn.getUserName() %>님, 안녕하세요.</p>
   		</div>
        <div class="mypage" id="mypage">
            <button class="mypages" id="myinfo" onclick="location.href='<%= request.getContextPath()%>/user/view?userId=<%=memberLoggedIn.getUserId()%>'">내 정보 수정</button><br />
            <button class="mypages" id="point-charge" onclick="location.href='<%=request.getContextPath()%>/point/pointManagement'">포인트 관리</button><br />
            <button class="mypages" id="write-resume" onclick="location.href='<%=request.getContextPath()%>/tutor/writerResume?userId=<%=memberLoggedIn.getUserId()%>'">이력서 관리</button><br />
            <button class="mypages" id="write-review" onclick="writeReview();">리뷰 작성</button><br />
            <button class="mypages" id="get-msg" onclick="msg();">메세지</button><br />
            <button class="mypages" id="logout" onclick="location.href='<%=request.getContextPath()%>/member/logout'">로그아웃</button>
        </div>
    </div>    
        <% } else if (memberLoggedIn != null && memberLoggedIn.getCommGrade().equals(adminGrade)){ %>
    	    <div class="wrapper-admin-header">
       			 <div class="admin-header">
          			  <div class="admin-navbar">
                    <div class="navbar-admin-menu">
                <a href="<%= request.getContextPath()%>"><button id="logo-btn-a">cooking-king</button></a>
            <button class="admin-navbar0" id="admin-logout" onclick="adminLogout();">로그아웃</button>
            <button class="admin-navbar1" onclick="location.href='<%= request.getContextPath() %>/board/list'">클래스 관리</button>
            <button class="admin-navbar2" onclick="location.href='<%=request.getContextPath()%>/admin/page'">회원 관리</button>
            <button class="admin-navbar4" onclick="location.href='<%= request.getContextPath()%>/admin/analysis'">현황 조회</button>
            <button class="admin-navbar5" onclick="location.href='<%= request.getContextPath()%>/center/list'">고객센터</button>
                    </div>
            </div>
       			 <div class="helloAdmin" id="helloAdmin">
   					<p class="helloAdmin-p" onclick="adminLogout();">
   					<%= memberLoggedIn.getUserName() %>님, 안녕하세요. </p>
   				 </div>
        </div>
    </div>
    	<% } %>
    	</div>
    	</div>
    	</div>
</body>
</html>