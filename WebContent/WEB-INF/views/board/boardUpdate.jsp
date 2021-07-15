<%@page import="board.model.vo.Board"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>  
<%
	Calendar today = Calendar.getInstance();
	today.setTime(new Date());
	Calendar lastApply = Calendar.getInstance();
	lastApply.setTime(new Date());
	lastApply.add(Calendar.MONTH, 1);
	
	Calendar classDateS = Calendar.getInstance();
	classDateS.setTime(new Date());
	classDateS.add(Calendar.MONTH, 1);
	classDateS.add(Calendar.DATE, 3);
	
	Calendar classDateE = Calendar.getInstance();
	classDateE.setTime(new Date());
	classDateE.add(Calendar.MONTH, 1);
	classDateE.add(Calendar.DATE, 3);
	classDateE.add(Calendar.MONTH, 1);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Board b = (Board)request.getAttribute("board");
%>
<style>
	table, th, td{
	 border: 1px solid #000;
	 border-collapse: collapse;
	}
	.file-label:hover, .hidden-label:hover{
		color : blue;
		text-decoration: underline;
		font-weight: bold;
	}
</style>
<script>
$(function(){
	$(".numberInput").keypress(function(event){
		if (event.which && (event.which < 48 || event.which > 57)) {
			event.preventDefault();
		  }
	});
	
	$(".file-input").change(function(){
		//파일을 선택한 경우
		if($(this).val() != ""){
			var file = $(this).val();
			var idx = file.lastIndexOf("\\");
			var fileName = file.substr(idx+1);
			
			$(this).siblings(".file-label").css("display", "none");
			$(this).siblings(".hidden-label").css("display", "initial").text(fileName);
		}
		//파일 선택을 취소한 경우
		else{
			$(this).siblings(".file-label").css("display", "initial");
			$(this).siblings(".hidden-label").css("display", "none").text("");
		}
	});
	
	$(".file-label, .hidden-label").click(function(){
		$(this).siblings(".file-input").trigger("click");
	});
	
});
function check(){
	var regExp = /^.{1,14}$/;
	if(regExp.test($("#title").val()) == false){
		alert("제목은 공백포함 14글자 이하로 설정 가능합니다.");
		$("#title").select();
		
		return false;
	}
	return true;
};
</script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-view.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-list.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-insert.css" />
<form action="<%= request.getContextPath() %>/board/update" method="POST" enctype="multipart/form-data" onsubmit="return check();">

	<label for="title">제목</label>
	<input type="text" name="title" id="title" required value="<%= b.getTitle() %>"/>
	<select name="category" id="category" required>
		<option value="" disabled>카테고리</option>
		<option value="MD" <%= b.getCategory().equals("MD") ? "selected" : "" %>>Main Dish</option>
		<option value="DS" <%= b.getCategory().equals("DS") ? "selected" : "" %>>Dessert</option>
		<option value="DR" <%= b.getCategory().equals("DR") ? "selected" : "" %>>Drink</option>
		<option value="AL" <%= b.getCategory().equals("AL") ? "selected" : "" %>>Alcohol</option>
	</select>
	<select name="classLocation" id="classLocation" required>
		<option value="" disabled>수업지역</option>
		<option value="NORTH" <%= b.getClassLocation().equals("NORTH") ? "selected" : "" %>>강북구</option>
		<option value="SOUTH" <%= b.getClassLocation().equals("SOUTH") ? "selected" : "" %>>강남구</option>
		<option value="EAST" <%= b.getClassLocation().equals("EAST") ? "selected" : "" %>>강동구</option>
		<option value="WEST" <%= b.getClassLocation().equals("WEST") ? "selected" : "" %>>강서구</option>
	</select>
	<label for="classAddress">주소</label>
	<input type="text" name="classAddress" id="classAddress" required value="<%= b.getClassAddress() %>"/>
	<label for="lastApplyDate">지원 마감일</label> <!-- 게시글 등록 날짜로부터 한달 -->
	<input type="date" name="lastApplyDate" id="lastApplyDate" min="<%= sdf.format(today.getTime()) %>" max="<%= sdf.format(lastApply.getTime()) %>" required value="<%= b.getLastApplyDate() %>"/>
	<label for="capacity">인원</label>
	<input type="text" name="capacity" id="capacity" class="numberInput" required value="<%= b.getCapacity() %>"/>
	<label for="price">가격</label>
	<input type="text" name="price" id="price" class="numberInput" required value="<%= b.getPrice() %>"/>
	<label for="classDate">수업일</label> <!-- 지원 마감일 최대기한 3일 이후 부터 한달 -->
	<input type="date" name="classDate" id="classDate" min="<%= sdf.format(classDateS.getTime()) %>" max="<%= sdf.format(classDateE.getTime()) %>" required value="<%= b.getClassDate() %>"/>
	<select name="startTime" id="startTime" required>
		<option value="" selected disabled>시작시간</option>
	<% for(int i = 0; i < 24; i++){ %>
		<option value="<%= i %>" <%= b.getStartTime() == i ? "selected" : "" %>><%= i %>시</option>
	<% } %>
	</select>
	<select name="endTime" id="endTime" required>
		<option value="" selected disabled>종료시간</option>
	<% for(int i = 0; i < 24; i++){ %>
		<option value="<%= i %>" <%= b.getEndTime() == i ? "selected" : "" %>><%= i %>시</option>
	<% } %>
	</select>
	<label for="classContent">내용</label>
	<textarea name="classContent" id="classContent" cols="30" rows="10" required><%= b.getClassContent() %></textarea>
	<table>
		<tr>
			<th>사진</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<tr>
			<td>
				<span><%= b.getClassPic1Org() %></span>
			</td>
			<td>
				<input class="file-input" type="file" name="pic1-input" id="pic1-input" style="opacity: 0; position: absolute; width: 0px;"/>
				<label class="hidden-label" style="display: none;"></label>
				<label class="file-label" for="changePic1">파일 변경</label>
			</td>
			<td>
				<label>삭제 불가</label>
			</td>
		</tr>
		<tr>
			<td>
				<span><%= b.getClassPic2Org() != null ? b.getClassPic2Org() : "업로드한 파일이 없습니다." %></span>
			</td>
			<td>
				<input class="file-input" type="file" name="pic2-input" id="pic2-input" style="opacity: 0; position: absolute; width: 0px;"/>
				<label class="hidden-label" style="display: none;"></label>
				<label class="file-label" for="changePic2"><%= b.getClassPic2Org() != null ? "파일 변경" : "파일 업로드" %></label>
			</td>
			<td>
				<label for="del-pic2">삭제하기</label>
				<input type="checkbox" name="del-pic2" id="del-pic2" <%= b.getClassPic2Org() != null ? "" : "disabled" %>/>
			</td>
		</tr>
		<tr>
			<td>
				<span><%= b.getClassPic3Org() != null ? b.getClassPic3Org() : "업로드한 파일이 없습니다." %></span>
			</td>
			<td>
				<input class="file-input" type="file" name="pic3-input" id="pic3-input" style="opacity: 0; position: absolute; width: 0px;"/>
				<label class="hidden-label" style="display: none;"></label>
				<label class="file-label" for="changePic2"><%= b.getClassPic3Org() != null ? "파일 변경" : "파일 업로드" %></label>
			</td>
			<td>
				<label for="del-pic3">삭제하기</label>
				<input type="checkbox" name="del-pic3" id="del-pic3" <%= b.getClassPic3Org() != null ? "" : "disabled" %>/>
			</td>
		</tr>
	</table>
	<input type="hidden" name="classNo" value="<%= b.getClassNo() %>" />
	<input type="submit" value="수정하기" />
	<input type="button" value="취소" onclick="history.go(-1);" />
</form>



<%@ include file="/WEB-INF/views/common/footer.jsp" %>