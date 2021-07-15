<%@page import="user.tutor.model.vo.TutorResume"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%
	TutorResume tutorResume = (TutorResume)request.getAttribute("tutorResume");
	User user = (User)request.getAttribute("user");
	//String scr =(String)request.getAttribute("scr");
	//System.out.println(scr);
%>
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/tutor-resume.css" />
<div class="t-resume-wrapper">
<div class="res-inner">
	<p class="page-title-p">제출된 이력서</p>
	<form action="<%= request.getContextPath()%>/tutor/writerResume"
	 enctype="multipart/form-data"
	 name="tutorWriter"
	 method="post" 
	 id="tutorResumeFrm">
	 
	<input type="hidden" id="user-id" name="userId" value="<%= user.getUserId()%>" />
	<table width="500px" border="3px">
	 <tr>
		<th colspan="3" heigth="4" align="center" bgcolor="#f6faff" id=><strong>이력서</strong></th>
	 </tr>
	 <tr>
	     <td colspan="2" rowspan=3 height=140 align="center"> 
	     	<img id="img" 
	     		 src="<%=tutorResume.getProfileOrg() == null ? request.getContextPath() + "/images/DEFAULTPHOTO.PNG": request.getContextPath() + "/upload/tutor/" + tutorResume.getProfileRen()%>"
	     		  width= "120" height="120" alt="프로필 사진"/>
	     </td>
     </tr>
     
     <tr>
    	 <td colspan=2><strong>이름: </strong><%= user.getUserName() %></td>
     </tr>
     <tr>
     	<td colspan=2><strong>주소: </strong><%= user.getAddress() %></td>
     </tr>
     
	<tr>
    <td colspan=3 bgcolor="#f6faff"><strong>자격증</strong></td>    
    </tr>
    
    <tr>
    	<td colspan="2">
    	  <label for="certName"><strong>자격증 명</strong></label>
  		  <div>
  		 	 <input type="text" id="cert-Name" name="certName" class="cerN" value='<%= tutorResume != null && tutorResume.getCertN1() != null ? tutorResume.getCertN1(): "" %>' />
  		 	 <%if(tutorResume.getCertN2() == null) {%>
  		 	 <input type="hidden" id="cert-Name2" name="certName2" class="cerN" value=''/>
  		 	 <% } else { %>
  		 	 <input type="text" id="cert-Name2" name="certName2" class="cerN" value='<%= tutorResume.getCertN2() %>'/>
  		 	 <% } if(tutorResume.getCertN3() == null) {%> 	 
  		 	 <input type="hidden" id="cert-Name3" name="certName3" class="cerN" value=''/>
  		 	 <% } else { %>
  		 	 <input type="text" id="cert-Name3" name="certName3" class="cerN" value='<%= tutorResume.getCertN3() %>'/>
  		 	 <% } %>
  		  </div>
    	</td>
   	
    	<td colspan="2">
  		  <label for="cert-Code"><strong>자격증 코드</strong></label>
    	  <div>
  		    <input type="text" id="cert-Code" name="certCode" class="cerC" value='<%= tutorResume != null && tutorResume.getCertC1() != null ? tutorResume.getCertC1() : "" %>' />
  		     <%if(tutorResume.getCertC2() == null) {%>
  		 	 <input type="hidden" id="cert-Code2" name="cerCode2" class="cerC" value=''/>
  		 	 <% } else { %>
  		 	 <input type="text" id="cert-Code2" name="certCode2" class="cerC" value='<%= tutorResume.getCertC2() %>'/>
  		 	 <% } if(tutorResume.getCertC3() == null) {%> 	 
  		 	 <input type="hidden" id="cert-Code3" name="certCode3" class="cerC" value=''/>
  		 	 <% } else { %>
  		 	 <input type="text" id="cert-Code3" name="certCode3" class="cerC" value='<%= tutorResume.getCertC3() %>'/>
  		 	 <% } %>
    	  </div>
    	</td>
	</tr>
	
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
	<div class="tr-btns">
	<% if((memberLoggedIn.getCommGrade().equals("US") || memberLoggedIn.getCommGrade().equals("P") )) { %>
	<input type="button" value="이력서 수정하기" onclick="location.href='<%=request.getContextPath()%>/tutor/writerResume?userId=<%=user.getUserId()%>'">
	<input type="button" value="뒤로 가기" onclick="history.go(-1);"/>
	<% } else if((memberLoggedIn.getCommGrade().equals("AD"))) {%>
	<input type="button" value="이력서 승인" id="acceptResume" />
	<input type="button" value="이력서 거부" id="refuseResume" />
	<input type="button" value="뒤로 가기" onclick="history.go(-1);"/>
	<% }%>
	</div>
	</form>
</div>
</div>

<script>

	$("#acceptResume").click(function(){
		
		if(confirm("이력서를 승인하시겠습니까?")){
			location.href="<%= request.getContextPath() %>/admin/filterResume?resumeYNP=Y&tutorId=<%= user.getUserId() %>";
		}
		
	});
	
	
	$("#refuseResume").click(function(){
		
		if(confirm("이력서를 거부하시겠습니까?")){
			location.href="<%= request.getContextPath() %>/admin/filterResume?resumeYNP=N&tutorId=<%= user.getUserId() %>";
		}
	
	});
	


</script>

		
<%@ include file="/WEB-INF/views/common/footer.jsp" %>    