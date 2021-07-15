
<%@page import="user.vo.Message"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Message> mlist = (List<Message>)request.getAttribute("mlist");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Message</title>
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/msg-review.css" />
<script src="<%= request.getContextPath() %>/js/jquery-3.5.1.js"></script>
<script>

	function readMsg(msgNo, tr){
		//ajax 읽음 처리
		$.ajax({
			url : "<%= request.getContextPath() %>/message/update?msgNo=" + msgNo,
			method: "GET",
			success: function(){
				//$(this) 배경 읽음처리된 tr의 배경색과 맞춤
				//$(this)의 next .content-tr display initial
				$(tr).css("background-color", "lightgray").next(".content-tr").slideToggle().find("td").attr("colspan",3);
				
			}
			//error
		});
		
	}

</script>
</head>
<body>
	<div id="msg-div">
<p class="page-title-p">내가 받은 메세지</p>
<div class="msg-inner">
<%
	if(mlist != null && mlist.size() != 0){
%>
		<table id="msg-tbl">
			<tr>
				<th id="msg-no">번호</th>
				<th id="msg-title">제목</th>
				<th id="msg-date">수신일</th>
			</tr>
<%
		int i = mlist.size();
		for(Message m : mlist){
%>
			<tr onclick="readMsg(<%= m.getMsgNo() %>, this);" style="background-color: <%= m.getMsgReadYn().equals("Y") ? "gray" : "white" %>;">
				<td><%= i %></td>
				<td><%= m.getMsgTitle() %></td>
				<td><%= m.getMsgDate() %></td>
			</tr>
			<tr class="content-tr" style="display: none; position: relative; background-color: white;">
				<td>
				<%= m.getMsgContent() %>
				</td>
			</tr>
<%
			i--;
		}
%>
		</table>
<%
	} else {
%>
		<span>받으신 메시지가 없습니다.</span>
<%
	}
%>
</div>
	<input type="button" value="닫기" onclick="self.close();"/>
	</div>
</body>
</html>