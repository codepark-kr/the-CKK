<%@page import="user.vo.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--         <%@ include file="/WEB-INF/views/common/userHeader.jsp" %> --%>
<%
	List<User> uList = (List<User>)request.getAttribute("uList");
%>    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/memberManagement.css" />
<div class="ul-wrapper">
<p class="page-title-p">전체 회원 목록</p>

<div class="mm-search-wrapper">
<span id="mm-search-title">아이디로 검색</span>
<input type="text" id="searchM" />
<input type="button" value="검색" id="mm-search-btn" onclick="search();" />
<input type="button" value="초기화" onclick="reset();"/>
</div>
<div class="mm-tbl-wrapper">
<table class="mm-tbl">
	<tr class="mm-thead">
		<th>아이디</th>
		<th>등급</th>
		<th>회원명</th>
		<th>성별</th>
		<th>생년월일</th>
		<th>전화번호</th>
		<th>이메일</th>
		<th>추방</th>
	</tr>

<%
	if(uList != null && !uList.isEmpty()){
		for(User u : uList){	
%>

	<tr>
		<td class="userId-td"><%= u.getUserId() %></td>
		<td><%= u.getCommGrade() %></td>
		<td><%= u.getUserName() %></td>
		<td><%= u.getGender() %></td>
		<td><%= u.getBirthday() %></td>
		<td><%= u.getPhone() %></td>
		<td><%= u.getEmail() %></td>
		<td><input type="button" value="추방하기" onclick="location.href = '<%= request.getContextPath() %>/user/delete?userId=<%= u.getUserId() %>'" /></td>
	</tr>

<%
		}
	}
%>
</table>
</div>
</div>
<script>
function search(){
	
	var userId = $("#searchM").val();
	$.each($(".userId-td"), function(i, elem){
		if($(elem).text() == userId) $(elem).parent().slideDown();
		else $(elem).parent().slideUp();
	});
};

function reset(){
	$.each($(".userId-td"), function(i, elem){
		$(elem).parent().slideDown();
	});
	
	$("#searchM").val("");
};
</script>