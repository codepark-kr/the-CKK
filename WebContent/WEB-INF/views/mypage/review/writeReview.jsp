<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Map<String, Object> map = (Map<String, Object>)request.getAttribute("map");
	String userId = (String)request.getAttribute("userId");
	List<Board> userClass = (List<Board>)map.get("list");
	List<Integer> clsNoTemp = new ArrayList<>();

%>    
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/msg-review.css" />
<%-- <link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin.css" /> --%>
<script src="<%=request.getContextPath() %>/js/jquery-3.5.1.js"></script>

<body>
<div id="msg-div">
	<p class="page-title-p">리뷰 작성</p>
	<div class="msg-inner">		
	<div class="wrapper-review-table">
		<table id="msg-tbl">
			<tr id="review-tr-head">
				<th width="80">수강일</th>
				<th width="300">강좌명</th>
				<th width="120">선생님</th>
				<th width="80">리뷰 작성</th>
			</tr>
			<%if(userClass != null && !userClass.isEmpty()) {%>
				<!-- 가져 온 데이터가 뿌려질 자리 -->
				<%
				int flag = 0;
				for(Board b : userClass) { 
				
 				if((boolean)map.get(String.valueOf(b.getClassNo()))) { 
 	
 					flag++;
	
 					} 
 				}
				if(flag == userClass.size()){
 				%>
 				<tr>
 					<td colspan=5>
						<span>모든 리뷰를 작성하셨습니다.</span>
					</td>
				</tr>
 				<%
				}
 				else if(flag != userClass.size()){
					for(Board b : userClass) { 
				%>
	 				<% if(!(boolean)map.get(String.valueOf(b.getClassNo()))) {%>
	 						
	 					<!-- <tr>
	 					<td colspan=5>
							<span>더 이상 작성할 리뷰가 없습니다.</span>
						</td>
						</tr> -->
						<tr>
						<td><%=b.getClassDate() %></td>
						<td><%=b.getTitle() %></td>
						<td><%=b.getTutorName() %>(<%=b.getTutorId() %>)</td>
					<td>
							<input type="button" value="리뷰 작성" id="writReviewBtn" onclick="writeReview(<%= b.getClassNo() %>, '<%= b.getTutorId() %>');"/>
							
						</td>
					</tr>
					<% } %>
			<% } %>
			<% } %>
 			<% }else {%>
			
			<!-- 데이터가 없을 때  -->
			<tr>
			<td colspan= "5" align=center>수강하신 강의가 없습니다.</td>
			</tr>
				<% } %>
 		</table>
	</div>
	</div>
		<input type="button" value="닫기" onclick="self.close();"/>
</div>
<script>
function writeReview(clsNo, tutorId){
	
	var html ="<tr>"
		 + "<td colspan='4'>"
		 + "<form action='<%=request.getContextPath()%>/user/writeReview' method='POST'>"
		 + "<textarea cols='60' rows='3' name='reviewContent' required></textarea>"
		 + "<label for='reviewScore'>별점 선택</label>"
		 + "<select name='reviewScore' id='reviewScore' required>"
		 + "<option value='' selected disabled>별점을 선택해 주세요</option>"
		 + "<option value='1' seleced>★</option>"
		 + "<option value='2'>★★</option>"
		 + "<option value='3'>★★★</option>"
		 + "<option value='4'>★★★★</option>"
		 + "<option value='5'>★★★★★</option>"
		 + "</select>"
		 + "<input type='hidden' name='userId' value='<%= userId %>' />"
		 + "<input type='hidden' name='tutorId' value='"+tutorId+"' />"
		 + "<input type='hidden' name='classNo' value='"+clsNo+"'/>"
		 + "<input type='submit' value='리뷰 작성' onclick='return gogogo();'/>"
		 + "</form>"
		 + "</td>"
		 + "</tr>";
		
		var clickBtn = $(event.target);
		var $tr = $(html);
		var $trFromBtn = clickBtn.parent().parent();
		console.log(clickBtn); /* 클릭한 버튼의 Tr을 가져옴 */
		console.log($trFromBtn);
		//$tr.insertAfter($trFromBtn);
		$tr.insertAfter($trFromBtn);
	
		$('#writReviewBtn').attr("onclick", "");
		
		function gogogo(){
			
			if(<%=userId != null%>) return true;
			else return false;
		}
}

<%-- $("#writReviewBtn").bind('click', writeReview);

function writeReview(event){
	var classNo;
	var tutorId;
	<% for(Board b : userClass) { %>
		<% if(clsNoTemp.indexOf(b.getClassNo()) != -1) {%>
			classNo = <%=b.getClassNo()%>;
			tutorId = <%=b.getTutorId()%>;
		<% } %>
	<% } %>	
	var html ="<tr>"
		 + "<td colspan='4'>"
		 + "<form action='<%=request.getContextPath()%>/user/writeReview' method='POST'>"
		 + "<textarea cols='60' rows='3' name='reviewContent' required></textarea>"
		 + "<label for='reviewScore'>별점 선택</label>"
		 + "<select name='reviewScore' id='reviewScore'>"
		 + "<option value='1' seleced>★</option>"
		 + "<option value='2'>★★</option>"
		 + "<option value='3'>★★★</option>"
		 + "<option value='4'>★★★★</option>"
		 + "<option value='5'>★★★★★</option>"
		 + "</select>"
		 + "<input type='hidden' name='userId' value='<%= userId %>' />"
		 + "<input type='hidden' name='tutorId' value='"+classNo+"' />"
		 + "<input type='hidden' name='classNo' value='"+tutorId+"'/>"
		 + "<input type='submit' value='리뷰 작성'/>"
		 + "</form>"
		 + "</td>"
		 + "</tr>";
		
		var clickBtn = $(event.target);
		var $tr = $(html);
		var $trFromBtn = clickBtn.parent().parent();
		console.log(clickBtn); /* 클릭한 버튼의 Tr을 가져옴 */
		console.log($trFromBtn);
		//$tr.insertAfter($trFromBtn);
		$tr.insertAfter($trFromBtn);
		
		$('#writReviewBtn').unbind('click');
}; --%>
</script>
</body>
