<%@page import="center.model.vo.CenterComment"%>
<%@page import="center.model.vo.Center"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%@page import="java.util.List"%> 

<%
 	Center b = (Center)request.getAttribute("center");
  	List<CenterComment> commentList = (List<CenterComment>)request.getAttribute("commentList");
 %> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin-view.css" />

<div class="cs-view-wrapper">
<section id="cs-container">
	<p class="cs-title-p">고객센터</p>
	<table id="cs-center-view">
		<tr>
			<th>글번호</th>
			<td><%=b.getCsNo()%></td>
		</tr>	
		<tr>
			<th>제목</th>
			<td><%=b.getCsTitle()%></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%=b.getUserId()%></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
			<%
				if(b.getCsFileOrg() != null){
			%>
				<a href="javascript:fileDownload('<%= b.getCsFileOrg() %>','<%= b.getCsFileRen() %>');">
					<img alt="첨부파일" src="<%= request.getContextPath() %>/images/file.png" width=16px>
					<%= b.getCsFileOrg() %>
				</a>
			<%
				}
			%>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><%=b.getCsContent()%></td>
		</tr>
						
	</table>
	
	<div class="cs-reply-wrapper">
<%
	if(commentList != null && commentList.size() != 0){
%>
<div class="cs-comment-list-wrapper">
	<table>
	<!-- (예빈) 관리자 아이디:th와 admin:td는 흐름상 다소 어색하므로 
		   아이디가 admin인 경우 "관리자"로 대체하는 것으로 변경하고, 본문 영역에 placeholder를 추가한다. -->
		<tr>
			<th>아이디</th>
			<th>내용</th>
			<th>작성일</th>
		</tr>
<%
	for(CenterComment bc : commentList){
%>

		<tr>
			<td id="cv-admin-id"><%= bc.getAdminId().equals("admin") ? "관리자" : "" %></td>
			<td id="cv-admin-cont"><%= bc.getCsaContent() %></td>
			<td id="cv-admin-date"><%= bc.getCsaDate() %></td>
		</tr>

<%
		}
%>
	</table>
</div>
<%
	}else if(memberLoggedIn != null && memberLoggedIn.getCommGrade().equals("AD")){
%>	
	<div class="cs-admin-insert">
		<form action="<%= request.getContextPath() %>/center/comment/insert" method="POST">
			<input type="hidden" name="adminId" value="<%= memberLoggedIn.getUserId() %>" />
			<input type="hidden" name="csNo" value="<%= b.getCsNo() %>" />
			<textarea name="csaContent" cols="30" rows="10" placeholder= "답변 내용을 작성해 주세요."></textarea>
			<input type="submit" value="답변 등록" id="cs-admin-submit"/>
		</form>
	</div>
<%
	} else {
%>
	<div class="cv-span">
		<span>아직 관리자의 답변이 작성되지 않았습니다.</span>
		</div>
<%
	}
%>
	
</div>	
<!-- (예빈) 뒤로가기 버튼 추가 -->
	<input type="button" value="뒤로 가기" id="goBack" onclick="history.go(-1);"/>
</section>
</div>


<script>
function fileDownload(oName, rName){
	
	oName = encodeURIComponent(oName);
	
	location.href = "<%= request.getContextPath() %>/center/fileDownload?oName="+oName+"&rName="+rName;
}
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>


