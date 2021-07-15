<%@page import="point.model.vo.Point"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%
	List<Point> pointView = (List<Point>)request.getAttribute("pointView");
	User selectedUser = (User)request.getAttribute("selectedUser");
	int cnt = 1;
/* 	int resultSum = (int)(request.getAttribute("resultSum")); */
	User commission = (User)session.getAttribute("commission");

%>
    <style>
    table{
    	border: 1px solid red;
    	overflow: auto;
    }
    tr, th, td{
    	border: 1px solid black;
    }
    div.point-table{
    	overflow: auto;
    	height: 150px;
    }
    #tbl-head th{
    	position: sticky;
    	top : 0;
    	background-color: salmon;
    }
    #tbl-footer th{
    	position: sticky;
    	bottom : 27px;
    	background-color: salmon;
    }
    #tbl-footer2 td{
    	position: sticky;
    	bottom : 0;
    	background-color: snow;
    }
    
    </style>
    
    <div class="point-notice-view">
		<h1>CKK :: pointManagement</h1>
		<p><%=selectedUser.getUserId() %>님</p>
    </div>
	<br />
	<div class="point-table-view">
<table>
				<tr id="tbl-head">
		
					<th>번호</th>
					<th>입/출력</th>
					<th>입/출력 내용</th>
					<th>입/출금 포인트</th>
					<th>입/출금 날짜</th>
					
				</tr>
				
				<%for(Point p : pointView) { %>
					<tr>
						<td><%= cnt++ %></td>
						<td><%= p.getPointIO() %></td>
						<td><%= p.getPointContent() %></td>
						<td><%= p.getPointAmount()%></td>
						<td><%= p.getPointIODate() %></td>
					</tr>
				<%} %>
				
				<tr id="tbl-footer">
					<th colspan="10">총액</th>
				</tr>
				 <tr id="tbl-footer2">
				 	<td colspan="10"><%= selectedUser.getPointSum()%></td>
				 </tr>
			</table>
	</div>
	<br />
	<div class="point-btn-view">
		<div class="point-deposit-User-view">
			<h1>포인트 보너스</h1>
			<div class="pointExc-notic-view">
				<p>현재 포인트 : <%=selectedUser.getPointSum() %></p>
			</div>
			<div class="pointExc-input1-view">
				<form action="<%= request.getContextPath() %>/admin/deposit" method="post" id="pointDep-frm">
				
					<div class="point-input1-view">
						<div class="point-input1-notice-view">
							<label for="pointAmountLabel">충전할 포인트를 선택해주세요.</label>
						</div>
						<div class="point-input1-index-view">
							<input type="radio" name="pointAmount" id="chargeVal1"  value="5000"/>
							<label for="chargeVal1">5000포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal2"  value="10000"/>
							<label for="chargeVal2">10000포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal3"  value="15000"/>
							<label for="chargeVal3">15000포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal4"  value="20000"/>
							<label for="chargeVal4">20000포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal5"  value="30000"/>
							<label for="chargeVal5">30000포인트</label>
							<input type="text" name="pointAmount" id="chargeVal6" class="NumOnly"/>
							<label for="chargeVal6">충전량을 입력하세요</label>
						</div>
						<div class="point-input1-payAmt-view"></div>
					</div>
					
					<div class="point-input2-view">
						<p></p>
						<input type="text" name="pointContent" id="pointContent" required/>
						<label for="pointContent">내용을 입력해주세요.</label>
					</div>

					<div class="point-input4-view">
						<input type="submit" value="충전하기" />
					</div>
					<input type="hidden" name="userId" value="<%=selectedUser.getUserId() %>" />
				</form>
			</div>
		</div> <!-- div 끝 -->
		
		<div class="point-exchange-tutor-view">
		<h1>포인트 환불 </h1>
			<div class="pointExc-notic-view">
			<p>현재 포인트 : <%=selectedUser.getPointSum() %></p>
			</div>
			<div class="pointExc-frm-view">
				<form action="<%= request.getContextPath() %>/admin/viewPointLog" method="post" id="pointExc-frm">
					<div class="point-input1-view">
						<label for="pointAmount">차감할 포인트를 입력해주세요.</label>
						<input type="text" name="pointAmount" id="pointAmount" class='NumOlny' placeholder="환전할 포인트를 입력해주세요." required />
					</div>
					
					<div class="point-input2-view">
						<input type="text" name="pointContent" id="pointContent" required/>
						<label for="pointContent">내용을 입력해주세요.</label>
					</div>
					
					<div class="point-input4-view">
						<input type="submit" value="차감하기"  />
					</div>
					<input type="hidden" name="userId" value="<%=selectedUser.getUserId() %>" />
				</form>
			</div>
		</div> <!-- point-exchange-tutor div 끝 -->
	</div>
<script>



$(document).ready(function(){
	
	$(".NumOnly").keypress(function(event){
		if (event.which && (event.which < 48 || event.which > 57)) {
			event.preventDefault();
		  }
	});
	
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>    
