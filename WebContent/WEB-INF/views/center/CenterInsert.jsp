<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp"%> 

<script>
function centerValidate(){
	//내용 작성x 공백x 
	var $csTitle = $("[name=csTitle]");
	var $csContent = $("[name=csContent]");
	
	if(/^.+$/.test($csTitle.val()) == false){
		alert("제목을 입력하세요.");
		return false;
	}
	if(/^(.|\n)+$/.test($csContent.val()) == false){
		alert("내용을 입력하세요.");
		return false;
	}
	return true;	
}

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
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/center-insert.css" />
<div class="ci-wrapper">
<div id="user-frm">
<section id="cs-container">
<p class="page-title-p">게시글 작성</p>
<form action="<%=request.getContextPath() %>/center/insert" method="post" enctype="multipart/form-data">    
			<table id="tbl-center-view">
	<tr>
		<th>제목</th>
		<td><input type="text" name="csTitle" id="csTitle"required></td>	
	</tr>
	<tr>
		<th>작성자</th>
		<td>
		<input type="text" name="csWriter"id="csWriter" value="<%= memberLoggedIn.getUserId() %>" readonly>
		</td>	
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
		<label for="classPic1Org" id="ins-file-upload1">파일 찾아보기</label>
		<input type="file" name="classPic1Org" id="classPic1Org"/>
		</td>	
	</tr>
	<tr>
	<tr>
	<th colspan="2">
	<textarea rows="5" cols="40" name="csContent" placeholder="
	부득이 일정한 내용의 게시물은 게재가 제한될 수 있습니다.
	
	여러분의 다양한 생각이나 경험 또는 의견이 담긴 게시물은 
	최대한 게재가 유지되어 다른 이용자들에게 전파될 것입니다. 
	다만 여러분이 쿠킹킹을 보다 안전하게 이용할 수 있도록, 
	쿠킹킹에서 여러분과 타인의 권리가 서로 존중되고 보호받을 수 있도록, 
	그리고 쿠킹킹이 신뢰 있는 서비스로서 안정적으로 제공될 수 있도록 
	부득이 아래와 같은 경우 여러분의 게시물 게재가 제한될 수 있으므로, 
	이에 대한 확인 및 준수를 요청 드립니다."></textarea>
	</th>
	</tr>	
	<tr>
		<th colspan="2">
		<label for="secretYn" class="to-secret">비밀글로 설정합니다</label>
		<input type="checkbox" name="secretYn" id="secretYn"/>
		</th>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="글등록" id="cs-post"
				   onclick="return centerValidate();">
		</th>
	</tr>

</table>
</form>
</section>
</div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>