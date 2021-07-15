<%@page import="center.model.vo.Center"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>    
<%
    	List<Center> list = (List<Center>)request.getAttribute("list");
    %>
<script>

	function gotoDetail(secretYn, writerId, csNo){
		
		if(secretYn == "Y"){
			if(<%=memberLoggedIn == null%>) {
				alert("본인만 확인 가능한 글입니다.");
				return;
			}
<%if(memberLoggedIn != null){%>
			var userId = '<%=memberLoggedIn.getUserId()%>';
			var userComm = '<%=memberLoggedIn.getCommGrade()%>';
<%}%>
			if(userId != writerId && userComm != "AD") {
				alert("본인만 확인 가능한 글입니다.");
				return;
			}
		}
		
		location.href="<%=request.getContextPath()%>/center/view?csNo=" + csNo;
	}
	
	$(function(){
		
		$("#myCenter").change(function(){
			var flag = $(this).prop("checked");
			if(flag == true){
				$.each($(".userId-td"), function(idx, item){
					if($(item).text() != '<%= memberLoggedIn.getUserId() %>' && $(item).text() != 'admin'){
						$(item).parent().slideUp();
					}
					
				});
			}
			else {
				$(".userId-td").parent().slideDown();
			}
		});
				
	});
</script>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin.css" />
<div class="cs-wrapper">
<!-- (예빈) 아래의 노란 줄은 div와 section의 위치가 꼬여서 그런 것이고 작동에는 문제 없으니 무시하셔도 됩니다. -->
<section id="cs-container">
	<p class="cs-title-p">고객센터</p>
<%
	if(memberLoggedIn != null){
%>
	<div class="mm-search-wrapper">
	<input type="button" value="글 작성하기" id="btn-add"
			onclick="location.href='<%=request.getContextPath()%>/center/insert';" />
	<input type="checkbox" name="myCenter" id="myCenter"/>
	<label for="myCenter" id="myCenter">내가 작성한 글만 보기</label>
	</div>
<%
	}
%>
	
<div class="cs-tbl-wrapper">
	<table id ="cs-center-list">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성날짜</th>
			<th>첨부파일</th>
		</tr>
<%
	if(list == null || list.isEmpty()){
%>
		<tr>
			<th colspan="6">조회된 문의 사항이 없습니다</th>
		</tr>
<%
	} else {
		for(Center b : list){
%>	
	<!-- (예빈) 관리자가 작성한 글은 모든 td의 배경색을 회색으로 처리, 
			      추가로 비밀글로 작성된 글은 글 제목을 회색으로 표시하도록 변경한다. -->
		<tr>
		 	<td
		 	<% if(b.getUserId().equals("admin")) { %>
			style='background: #e8ecf1'
			<% } %>
			
		 	> <%= b.getUserId().equals("admin") ? "공지" : b.getCsNo() %> </td>
		 	
			<td 
			<% if(b.getUserId().equals("admin")) { %>
			style='background: #e8ecf1'
			<% } else if (b.getCsSecretYN().equals("Y")){%>
			style='color: gray'
			<% } %>
			
			onclick="gotoDetail('<%= b.getCsSecretYN() %>', '<%= b.getUserId() %>', <%= b.getCsNo() %>);">
				<%= b.getCsSecretYN().equals("Y") ? "--- 비밀 설정된 글 입니다. ---" : b.getCsTitle() %>
			</td>
			
			<td 
			<% if(b.getUserId().equals("admin")) { %>
			style='background: #e8ecf1'
			<% } %>
			
			class="userId-td"><%= b.getUserId() %></td>
			<td
			<% if(b.getUserId().equals("admin")) { %>
			style='background: #e8ecf1'
			<% } %>
			
			><%= b.getCsWriteDate() %></td>
			
			<td
			<% if(b.getUserId().equals("admin")) { %>
			style='background: #e8ecf1'
			<% } %>
			>
				<% if (b.getCsFileOrg() != null){ %>
				<img src="<%= request.getContextPath() %>/images/file.png" alt="첨부파일" width="16px"/>
				<% } %>
		 	</td>	
		</tr>
<%
		}
}
%>
		 	
	</table>
</div>		
</div>
		<div id="cs-pageBar">
			<div id='pageBar'>
				<%= request.getAttribute("pageBar") %>
			</div>
</section>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
