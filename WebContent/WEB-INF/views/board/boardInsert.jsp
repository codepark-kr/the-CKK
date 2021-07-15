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
%>
<script>
$(function(){
	$(".numberInput").keypress(function(event){
		if (event.which && (event.which < 48 || event.which > 57)) {
			event.preventDefault();
		  }
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

/* (예빈)input[type="file"]관련 처리 */
$(document).ready(function(){

	  $('#classPic1Org').change(function(){
	    $('#ins-file-upload1').html($(this).val().replace('C:\\fakepath\\', ''));
	  });
	  $('#classPic2Org').change(function(){
	    $('#ins-file-upload2').html($(this).val().replace('C:\\fakepath\\', ''));
	  });
	  $('#classPic3Org').change(function(){
	    $('#ins-file-upload3').html($(this).val().replace('C:\\fakepath\\', ''));
	  });
});
</script>
<style>
</style>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-view.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-list.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-insert.css" />

<!-- css 배치 편의를 위한 최상위 wrapper div -->
<div class="view-div">
<form action="<%= request.getContextPath() %>/board/insert" method="POST" enctype="multipart/form-data" onsubmit="return check();">
<!-- (예빈) css 편의 및 보다 원활한 요소 배치를 위해 각 폼의 section/div를  나눠 재배치한다.
 -->
<section id="ins-cont-wrapper">
<div class="page-title">
	<p class="page-title-p">클래스 개설</p>
</div>
	<label for="ins-title">제목</label>
	<input type="text" name="ins-title" id="ins-title" required  placeholder="클래스 제목을 입력해 주세요. (공백 포함 14자 이내)" />
<div class="ins-cont">
<!-- 게시글 작성 약관 출처: 네이버 ㅎ -->
	<textarea name="classContent" id="classContent" cols="30" rows="10" 
	placeholder="
	부득이 일정한 내용의 게시물은 게재가 제한될 수 있습니다.
	
	여러분의 다양한 생각이나 경험 또는 의견이 담긴 게시물은 
	최대한 게재가 유지되어 다른 이용자들에게 전파될 것입니다. 
	다만 여러분이 쿠킹킹을 보다 안전하게 이용할 수 있도록, 
	쿠킹킹에서 여러분과 타인의 권리가 서로 존중되고 보호받을 수 있도록, 
	그리고 쿠킹킹이 신뢰 있는 서비스로서 안정적으로 제공될 수 있도록 
	부득이 아래와 같은 경우 여러분의 게시물 게재가 제한될 수 있으므로, 
	이에 대한 확인 및 준수를 요청 드립니다."

	required></textarea>
</div>
</section>

<section id="ins-input-wrapper">
<div class="ins-input-inner">
<p id="ins-input-title">클래스 상세 정보를 설정해 주세요.</p>
<section id="ins-input-in-inner">
<div class="ins-info1">
	<label for="price">가격</label>
	<input type="text" name="price" id="price" class="numberInput"  placeholder="학생 1인당 수강료" required/>
	
	<label for="classDate">일시</label> <!-- 지원 마감일 최대기한 3일 이후 부터 한달 -->
	<input type="date" name="classDate" id="classDate" min="<%= sdf.format(classDateS.getTime()) %>" max="<%= sdf.format(classDateE.getTime()) %>" required/>
</div>
<div class="ins-info2">
	<label for="startTime">시작</label>
	<select name="startTime" id="startTime" required>
		<option value="" selected disabled>~시 부터</option>
	<% for(int i = 0; i < 24; i++){ %>
		<option value="<%= i %>"><%= i %>시</option>
	<% } %>
	</select>
	<label for="endTime">종료</label>
	<select name="endTime" id="endTime" required>
		<option value="" selected disabled>~시 까지</option>
	<% for(int i = 0; i < 24; i++){ %>
		<option value="<%= i %>"><%= i %>시</option>
	<% } %>
	</select>
</div>

<div class="ins-info3">
<label for="category">분야</label>
	<select name="category" id="category" required>
		<option value="" selected disabled>카테고리</option>
		<option value="MD">Main Dish</option>
		<option value="DS">Dessert</option>
		<option value="DR">Drink</option>
		<option value="AL">Liquor</option>
	</select>
	<label for="classLocation">지역</label>
	<select name="classLocation" id="classLocation" required>
		<option value="" selected disabled>수업지역</option>
		<option value="NORTH">강북구</option>
		<option value="SOUTH">강남구</option>
		<option value="EAST">강동구</option>
		<option value="WEST">강서구</option>
	</select>
</div>

<div class="ins-info4">
	<label for="classAddress">주소</label>
	<input type="text" name="classAddress" id="classAddress" placeholder="상세 주소를 작성해 주세요." required/>
	<br />
	<label for="capacity">인원</label>
	<input type="text" name="capacity" id="capacity" class="numberInput"  placeholder="수업 정원" required/>
	<label for="lastApplyDate">지원 마감일</label> <!-- 게시글 등록 날짜로부터 한달 -->
	<input type="date" name="lastApplyDate" id="lastApplyDate" min="<%= sdf.format(today.getTime()) %>" max="<%= sdf.format(lastApply.getTime()) %>" required/>
	<br />

</div>
</section>
<div class="ins-info5">
<div class="upload-field">

<input type="file" name="classPic1Org" id="classPic1Org" required/>
<label for="classPic1Org" id="ins-file-upload1">클래스 대표 사진을 선택해 주세요. (필수)</label>


<input type="file" name="classPic2Org" id="classPic2Org"/>
<label for="classPic2Org" id="ins-file-upload2">클래스 대표 사진을 선택해 주세요.</label>


<input type="file" name="classPic3Org" id="classPic3Org"/>
<label for="classPic3Org" id="ins-file-upload3">클래스 대표 사진을 선택해 주세요.</label>
</div>

</div>
<div class="ins-apply">
<input type="hidden" name="tutorId" value="<%= memberLoggedIn.getUserId() %>" />
	<input type="submit" value="클래스 등록하기" id="ins-submit"/>
</div>
</div>
</section>
</form>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>