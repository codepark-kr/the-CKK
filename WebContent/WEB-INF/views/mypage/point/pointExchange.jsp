<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<h1>CKK :: pointExchange</h1>
	
	
	<div class="pointExc-notic">
		<p>현재 포인트 : <%=memberLoggedIn.getPointSum() %></p>
	</div>
	<br />
	<div class="pointExc-input1">
		<form action="<%= request.getContextPath() %>/point/pointExchange" method="post" id="pointExc-frm">
			<input type="text" name="pointAmount" id="pointAmount" placeholder="환전할 포인트를 입력해주세요." required />
			<input type="button" value="+1000원" id="plusBtn1" onclick="add(1000);"/>
			<input type="submit" value="환전하기" />
			<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
		</form>
	</div>

<script>
function add(num){
	
	var pointAmt = document.getElementById("pointAmount");
	
	pointAmt.text += parseInt(pointAmt.value) + num;
	
	
}

</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>    