<%@page import="user.vo.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--         <%@ include file="/WEB-INF/views/common/userHeader.jsp" %> --%>
<%
	List<User> tList = (List<User>)request.getAttribute("tList");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/memberManagement.css" />
<div class="gu-wrapper">
<p class="page-title-p">이력서 심사 대기 중인 회원 목록</p>
<div class="gu-inner-wrapper">
<table class="mm-tbl">
	<tr class="mm-thead">
		<th>회원아이디</th>
		<th>회원명</th>
		<th>성별</th>
		<th>생년월일</th>
		<th>전화번호</th>
		<th>이메일</th>
		<th>이력서</th>
	</tr>
<%
	if(tList != null && !tList.isEmpty()){
		for(User u : tList){	
%>
	<tr>
		<td><%= u.getUserName() %></td>
		<td><%= u.getUserId() %></td>
		<td><%= u.getGender() %></td>
		<td><%= u.getBirthday() %></td>
		<td><%= u.getPhone() %></td>
		<td><%= u.getEmail() %></td>
		<td><input type="button" value="이력서보기" onclick="location.href='<%= request.getContextPath() %>/admin/check/resume?userId=<%= u.getUserId() %>'" /></td>
	</tr>

<%
		}
	}
%>
</table>
</div>
</div>