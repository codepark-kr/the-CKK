<%@page import="user.tutor.model.vo.TutorResume"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%
	TutorResume tutorResume = (TutorResume)request.getAttribute("tutorResume");
	String scr =(String)request.getAttribute("scr");
	System.out.println(scr);
%>
​
<style>
​
</style>
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/tutor-resume.css" />
<div class="t-resume-wrapper">
<div class="res-inner">
	<p class="page-title-p">내 이력서</p>
	
	<form action="<%= request.getContextPath()%>/tutor/writerResume"
	 enctype="multipart/form-data"
	 name="tutorWriter"
	 method="post" 
	 id="tutorResumeFrm">
	<input type="hidden" id="user-id" name="userId" value="<%= memberLoggedIn.getUserId()%>" />
	<table width="500px" border="3px">
	 <tr>
		<th colspan="3" heigth="4" align="center" bgcolor="#f6faff" id=><strong>이력서</strong></th>
	 </tr>
	 <tr>
	     <td rowspan=2 height=140 width=130 align="center"> 
	     	<img id="img" 
	     		 src='<%=tutorResume != null ? (tutorResume.getProfileOrg() == null ? request.getContextPath() + "/images/DEFAULTPHOTO.PNG": request.getContextPath() + "/upload/tutor/" + tutorResume.getProfileRen()) : request.getContextPath() + "/images/DEFAULTPHOTO.PNG" %>'
	     		 width="120" height="120" alt="프로필 사진"/>
	    
	        <input class="file-input" type="file" name="pic2-input" id="pic2-input" style="opacity: 0; position: absolute; width: 0px;"/>
	     	<br />
	     	<label class="hidden-label" style="display:'none'"></label>
			<label class="file-label" for="changePic1" id="profile">사진 변경</label>
	     	<!-- <input type="file" name="profile" id="profile" / >-->
	     </td>
	     <td colspan=3><strong>이름:</strong> <%= memberLoggedIn.getUserName() %></td>
	     
    </tr>

     <tr>
    	 <td colspan=3><strong>주소:</strong> <%= memberLoggedIn.getAddress() %></td>
     </tr>

     
	<tr>
    	<td colspan=3 bgcolor="#f6faff"><strong>자격증</strong></td>    
    </tr>
    <tr>
    	<th>자격증 명</th>
    	<th>자격증 코드</th>
    	<th id="ar-btn">
    		<input id="certA-btn" type="button" value="+" onclick="addCert();" />
    		<input id="certR-btn" type="button" value="-" onclick="removeCert();" />
    	</th>
	</tr>
	<tr class="cert-tr">
		<td>
			<input type="text" id="cert-Name1" name="certName1" class="cerN" value='<%= tutorResume != null && tutorResume.getCertN1() != null ? tutorResume.getCertN1(): "" %>' />
		</td>
		<td>
			<input type="text" id="cert-Code1" name="certCode1" class="cerC" value='<%= tutorResume != null && tutorResume.getCertC1() != null ? tutorResume.getCertC1() : "" %>' />
		</td>
	</tr>
	<% if(tutorResume != null && tutorResume.getCertN2() != null && !tutorResume.getCertC2().isEmpty()){ %>
	<tr class="cert-tr">
		<td>
			<input type="text" id="cert-Name2" name="certName2" class="cerN" value='<%= tutorResume != null && tutorResume.getCertN2() != null ? tutorResume.getCertN2(): "" %>' />
		</td>
		<td>
			<input type="text" id="cert-Code2" name="certCode2" class="cerC" value='<%= tutorResume != null && tutorResume.getCertC2() != null ? tutorResume.getCertC2() : "" %>' />
		</td>
	</tr>	
	<% } %>
	<% if(tutorResume != null && tutorResume.getCertN3() != null && !tutorResume.getCertC3().isEmpty()){ %>
	<tr class="cert-tr">
		<td>
			<input type="text" id="cert-Name3" name="certName3" class="cerN" value='<%= tutorResume != null && tutorResume.getCertN3() != null ? tutorResume.getCertN3(): "" %>' />
		</td>
		<td>
			<input type="text" id="cert-Code3" name="certCode3" class="cerC" value='<%= tutorResume != null && tutorResume.getCertC3() != null ? tutorResume.getCertC3() : "" %>' />
		</td>
	</tr>	
	<% } %>
	<tr>
    <td colspan=3 bgcolor="#f6faff" id="award-table"><strong>수상 경력</strong></td>
    </tr>
    <tr>
    <td colspan=3><textarea cols=70 rows=10 id="award-record" name="awardRecord"><%=  tutorResume != null && tutorResume.getAwardRecord() != null ? tutorResume.getAwardRecord():"" %></textarea></td>
    </tr>
    <tr>
    <td colspan=3 bgcolor="#f6faff"><strong>경력</strong></td>
    </tr>
    <tr>
    <td colspan=3><textarea cols=70 rows=10 id="career-name" name="career"><%=  tutorResume != null && tutorResume.getCareer() != null ? tutorResume.getCareer():"" %></textarea></td>
    </tr> 
	</table>
	<% if((memberLoggedIn.getCommGrade().equals("US") || memberLoggedIn.getCommGrade().equals("AD") )) { %>
	<input type="submit" value="중간 저장"/>
	<% } %>
	<div class="tr-btns">
	<input type="button" value="이력서 제출" name="submitBtn" onclick="submitResume();"/>
	<input type="button" value="취소" onclick="history.go(-1);" />
	</div>
	</form>
	
​</div>
​</div>
<script>
	var sel_file;
	$(document).ready(function() {
		  $(".file-input").on("change", profileUpload);
	  });
	
	 
	
	 function profileUpload(e) {
	        var files = e.target.files;
	        var filesArr = Array.prototype.slice.call(files);
	        filesArr.forEach(function(f) {
				if(!f.type.match("image.*")) {
			    	alert("이미지만 올릴 수 있습니다.");
			    	return;
				}
			         
				sel_file = f;
				$img = $("#img");
				var reader = new FileReader();
				reader.onload = function(e) {
			    	$img.attr("src", e.target.result);
				}
				reader.readAsDataURL(f);
			});
	        
	        var oFile = $(this)[0].files;
	        if(oFile.length < 1){
	        	$("#img").attr("src", "<%=tutorResume != null ? (tutorResume.getProfileOrg() == null ? request.getContextPath() + "/images/DEFAULTPHOTO.PNG": request.getContextPath() + "/upload/tutor/" + tutorResume.getProfileRen()) : request.getContextPath() + "/images/DEFAULTPHOTO.PNG" %>");
	        }
	        
	  }
	 
		$("[name=tutorWriter]").submit(function(){
			
			 if(confirm("정말로 저장하시겠습니까?") == false){
				 return false;
			 }
			 
		});

		function submitResume(){
			var $tutorResumeFrm = $("#tutorResumeFrm");
			$tutorResumeFrm.attr("action", "<%=request.getContextPath()%>/tutor/submitResume");
			$tutorResumeFrm.attr("method", "post");
			//제출 전 유효성검사 필요	
			//1.프로필 첨부 여부 확인
				if(!$(".file-input").val()){
					alert("양식에 맞춰 이력서를 작성해 주세요.");	
					$("#profile").select();
					return false;
				}
			
			//2.자격증 코드 작성 확인
				console.log($("#cert-Code").val() );
				if($("#cert-Code").val() == "") {
					alert("양식에 맞춰 이력서를 작성해 주세요.");
					$("#cert-Code").select();	
					return false;
				}
			
			//3.자격증 명 작성 확인
				if($("#cert-Name").val() == "") {
					alert("양식에 맞춰 이력서를 작성해 주세요.");
					$("#cert-Name").select();	
					return false;
				}

			//4.수상경력 작성 확인
				if($("#award-record").val() == ""){
					alert("양식에 맞춰 이력서를 작성해 주세요.");
					$("#award-record").select();	
					return false;
				}
			
			//5.경력 작성 확인
				if($("#career-name").val() == ""){
					alert("양식에 맞춰 이력서를 작성해 주세요.");
					$("#career-name").select();	
					return false;
				}
			if(confirm("이력서를 제출하시겠습니까? 이력서 심사시 3~5일이 소요됩니다.")){
				$tutorResumeFrm.submit();
		    }
		};
		
function addInputBox(t){
/* 	var $certName = $(".cerN");
	var $certCode = $(".cerC");
	
	console.log($certName.eq(0).next());
	console.log($certCode.eq(0).next());
	
	$certName.eq(0).next().attr('type', 'text');
	$certCode.eq(0).next().attr('type', 'text');
	 */
	 console.log($(t).parent().siblings().children().find("[type=hidden]").attr('type', 'text'));
	 $(t).parent().siblings().children().find("[type=hidden]").attr('type', 'text');
}

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
		//사진도 본래사진으로 변경
		
	}
});

$(".file-label, .hidden-label").click(function(){
	$(this).siblings(".file-input").trigger("click");
});





	function addCert(){
		
		var cnt = 0;
		var num = 0;
		$.each($(".cert-tr"), function(i, certs){
			cnt++;
		});
		
		if(cnt == 0) num = 1;
		else if(cnt == 1) num = 2;
		else if(cnt == 2) num = 3;
		
		if(cnt < 3){
			var html = "<tr class='cert-tr'><td><input type='text' id='cert-Name"+num+"' name='certName"+num+"' class='cerN' /></td><td><input type='text' id='cert-Code"+num+"' name='certCode"+num+"' class='cerC' /></td></tr>";
			$("#certA-btn").parent().parent().after(html);
			$("#ar-btn").attr("rowspan", cnt+2);
		}
	};

	function removeCert(){
		var cnt = 0;
		$.each($(".cert-tr"), function(i, certs){
			cnt++;
		});
		
		if(cnt >1){
			$("#certA-btn").parent().parent().siblings("tr.cert-tr").last().remove();
			$("#ar-btn").attr("rowspan", cnt);
		}
	}
	
	$(function(){
		var cnt = 0;
		$.each($(".cert-tr"), function(i, certs){
			cnt++;
		});
		
		$("#ar-btn").attr("rowspan", cnt+1);
	});
</script>
​
		
<%@ include file="/WEB-INF/views/common/footer.jsp" %>    