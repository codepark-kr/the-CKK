
<%@page import="java.text.DecimalFormat"%>
<%@page import="point.model.vo.Point"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%
	List<Point> pointView = (List<Point>)request.getAttribute("pointView");
	List<Point> pointAll = (List<Point>)request.getAttribute("pointAll");
	List<User> uList = (List<User>)request.getAttribute("uList");
	int cnt = 1;
/* 	int resultSum = (int)(request.getAttribute("resultSum")); */
	User commission = (User)session.getAttribute("commission");

	/* (예빈) 포인트 액수에 DecimalFormat 대입 */
	DecimalFormat pointF = new DecimalFormat("###,###,### 포인트");
%>

 <link rel="stylesheet"href="<%=request.getContextPath()%>/css/point.css" />   
 <div class="pi-wrapper">
    <div class="point-notice">
    <!-- (예빈)유저 또는 튜터 계정인 경우 -->
    <%  if(!"AD".equals(memberLoggedIn.getCommGrade())) { %>
		<p class="page-title-p"><strong><%=memberLoggedIn.getUserId() %>님</strong>의 포인트</p>
    </div>
   <div id="ins-cont-wrapper">
	<br />
	<p class="page-small-p">내 포인트 기록</p>
	<div class="point-table">
<table>
				<tr id="tbl-head">
		
					<th id="pm-th-no">번호</th>
			<!-- (예빈) 사용자에게 노출하지 않아도 된다고 판단되어 우선 주석처리함. -->
					<!-- <th>입/출력</th> -->
					<th id="pm-th-cont">입/출력 내용</th>
					<th>입/출금 포인트</th>
					<th>입/출금 날짜</th>
					
				</tr>
				
				<%for(Point p : pointView) { %>
					<tr>
						<td><%= cnt++ %></td>
						<%-- <td><%= p.getPointIO() %></td> --%>
						<td><%= p.getPointContent() %></td>
						<td><%= p.getPointAmount()%></td>
						<td><%= p.getPointIODate() %></td>
					</tr>
				<%} %>
			<tfoot>
				<tr id="tbl-footer">
					<th colspan="10">총액</th>
				</tr>
				 <tr id="tbl-footer2">
				 	<td colspan="10"> <%= pointF.format(memberLoggedIn.getPointSum()) %></td>
				 </tr>
			</tfoot>
			</table>
	</div>
	</div>
	<br />
	<!-- 관리자 계정인 경우 -->
	<% } else { %>
	<p class="page-title-p"><strong>포인트 관리</strong></p>
    </div>
   <div id="ins-cont-wrapper">
	<br />
	<p class="page-small-p">회원 포인트 로그</p>
	<div class="point-table">
<table>
				<tr id="tbl-head">
		
					<th id="pm-th-no">번호</th>
			<!-- (예빈) 사용자에게 노출하지 않아도 된다고 판단되어 우선 주석처리함. -->
					<!-- <th>입/출력</th> -->
					<th>아이디</th>
					<!-- <th>내용</th> -->
					<th>포인트 총액</th>
					<th>일자</th>
				<!-- 	<th>선택</th> -->
				</tr>
				
				<%for(Point a : pointAll) { %>
					<tr>
						<td><%= cnt++ %></td>
						<%-- <td><%= p.getPointIO() %></td> --%>
						<td id="pm-th-id"><%= a.getUserId() %></td>
						<%-- <td id="pm-th-cont"><%= a.getPointContent() %></td> --%>
						<td><%= a.getPointAmount()%></td>
						<td><%= a.getPointIODate() %></td>
					<!-- 	<td><input type="checkbox" name="select-id" id="select-id" /></td> -->
					</tr>
				<%} %>
			</table>
	</div>
	</div>
	<br />
	<% } %>
<div id="ins-input-wrapper">
	<div class="point-btn">
	<% if("US".equals(memberLoggedIn.getCommGrade())) {%>
	<div class="point-deposit-User">	
			<div class="pointExc-notic">
			<p class="push"></p>
			<p class="page-small-p2">포인트 충전</p>
				<p>현재 <strong><%= pointF.format(memberLoggedIn.getPointSum()) %></strong>가 있습니다.</p>
			</div>
			<div class="pointExc-input1">
				<form action="<%= request.getContextPath() %>/point/pointDeposit" method="post" id="pointDep-frm" onsubmit="return gogo();">
				
					<div class="point-input1">
						<div class="point-input1-notice">
							<label for="pointAmountLabel" id="select-amount">충전할 포인트 액수를 선택해주세요.</label>
						</div>
						<div class="point-input1-index">
				<!-- (예빈)  각 클래스의 전반적인 가격대를 보아 충전 액수가 다소 적다고 생각되어 금액대를 수정하며, 
							배치 편의상 label과 radio의 위치를 바꿈. -->
						<div id="less-point">
							<label for="chargeVal1">10,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal1"  value="10000"/>
							<label for="chargeVal2">15,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal2"  value="15000"/>
							<label for="chargeVal3">20,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal3"  value="20000"/>
						</div>
						<div id="more-point">
							<label for="chargeVal4">30,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal4"  value="30000"/>
							<label for="chargeVal5">50,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal5"  value="50000"/>
							<label for="chargeVal6">100,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal6"  value="100000"/>
						</div>
						</div>
						<div class="point-input1-payAmt"></div>
						<p id="select-payment">결제 수단을 선택해주세요.</p>
					<div class="point-input2">
						<label for="munsang">문화 상품권</label>
						<input type="radio" name="payment" id="munsang" value="munsang"/>
						<label for="creditCard">신용카드</label>
						<input type="radio" name="payment" id="creditCard" value="creditCard"/>
						<label for="transfer">계좌 이체</label>
						<input type="radio" name="payment" id="transfer" value="transfer"/>
					</div>
					
					<div class="point-input3" id="showPayment">
						<!-- 결제 수단 입력될 div -->
					</div>
					<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
				</form>
			</div>
					<div class="point-input4">
						<input type="submit" value="충전하기" />
					</div>
		</div> <!-- div 끝 -->
					</div>
					
	<% } else if("T1".equals(memberLoggedIn.getCommGrade()) || 
				 "T2".equals(memberLoggedIn.getCommGrade()) || 
				 "T3".equals(memberLoggedIn.getCommGrade()) || 
				 "T4".equals(memberLoggedIn.getCommGrade())) { %>
		<div class="point-exchange-tutor">
			<!-- <div class="pointExc-notic"> -->
<!-- 		<p class="push"></p> -->
			<p class="page-small-p2">포인트 환전 / 충전</p>
				<p>현재 <strong><%= pointF.format(memberLoggedIn.getPointSum()) %></strong>가 있습니다.</p>
			</div>
			<div class="pointExc-frm">
				<form action="<%= request.getContextPath() %>/point/pointExchange" method="post" id="pointExc-frm">
				<div class="point-input1">
					<div class="point-input22">
						<label for="bankInc" id="select-no">계좌번호를 입력해 주세요.</label>
						<br />
						<select name="bankInc" id="bankInc" required>
							<option value="" selected disabled>은행을 선택해 주세요.</option>
							<option value="sinhan">신한은행</option>
							<option value="kb">국민은행</option>
							<option value="woori">우리은행</option>
							<option value="hana">하나은행</option>
							<option value="kko">카카오뱅크</option>
							<option value="tos">토스</option>
						</select>
						<input type="text" name="bankSerial" id="bankSerial" class='NumOnly' placeholder="- 제외숫자만 입력해주세요." style="width:280px" required/>
					</div>
						<label for="pointAmount">환전할 포인트를 입력해주세요.</label><br />
						<input type="text" name="pointAmount" id="pointAmount" class='NumOlny' placeholder="환전할 포인트를 입력해주세요." required />
						<input type="submit" value="환전하기" id="goExch"/>
					</div>
	
					<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
				</form>
			</div>
			
			
			<div class="point-deposit-tutor">
			<div class="pointExc-input1">
				<form action="<%= request.getContextPath() %>/point/pointDeposit" method="post" id="pointDep-frm" onsubmit="return gogo();">
				
					<div class="point-input1">
						<div class="point-input1-notice">
							<label for="pointAmountLabel" id="select-amount">충전할 포인트 액수를 선택해주세요.</label>
						</div>
						<div class="point-input1-index">
				<!-- (예빈)  각 클래스의 전반적인 가격대를 보아 충전 액수가 다소 적다고 생각되어 금액대를 수정하며, 
							배치 편의상 label과 radio의 위치를 바꿈. -->
						<div id="less-point">
							<label for="chargeVal1">10,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal1"  value="10000"/>
							<label for="chargeVal2">15,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal2"  value="15000"/>
							<label for="chargeVal3">20,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal3"  value="20000"/>
						</div>
						<div id="more-point">
							<label for="chargeVal4">30,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal4"  value="30000"/>
							<label for="chargeVal5">50,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal5"  value="50000"/>
							<label for="chargeVal6">100,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal6"  value="100000"/>
						</div>
						</div>
						<div class="point-input1-payAmt"></div>
						<p id="select-payment">결제 수단을 선택해주세요.</p>
					<div class="point-input2">
						<label for="munsang">문화 상품권</label>
						<input type="radio" name="payment" id="munsang" value="munsang"/>
						<label for="creditCard">신용카드</label>
						<input type="radio" name="payment" id="creditCard" value="creditCard"/>
						<label for="transfer">계좌 이체</label>
						<input type="radio" name="payment" id="transfer" value="transfer"/>
					</div>
					
					<div class="point-input3" id="showPayment">
						<!-- 결제 수단 입력될 div -->
					</div>
					<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
				</form>
			</div>
					<div class="point-input4">
						<input type="submit" value="충전하기" />
					</div>
		</div> <!-- div 끝 -->
			</div>
	 
		</div> <!-- point-exchange-tutor div 끝 -->
		</div>
		
		<!-- 유저, 튜터가 아닌 경우,. 관리자로 접근하는 경우 -->
	<% } else { %>
	
	<div class="go-point">
		<p class="page-small-p2">포인트 충전/차감</p>
				<p><%-- 현재 <strong><%= pointF.format(memberLoggedIn.getPointSum()) %></strong>가 있습니다. --%></p>
			</div>
			<div class="pointExc-frm">
				<form action="<%= request.getContextPath() %>/point/pointExchange" method="post" id="pointExc-frm">
				<div class="point-input1">
					<div class="point-input22">

					<label for="pointAmount" id="select-amount">포인트를 충전/차감할 회원을 검색해 주세요.</label>
					<div class="mm-search-wrapper">
<label>아이디로 검색</label>
<input type="text" id="searchM" />
<input type="button" value="검색" id="mm-search-btn" onclick="search();" />
<input type="button" value="초기화" id="mm-reset-btn" onclick="reset();"/>
<br />
<label for="pointContent">내용을 입력해주세요.</label>
<input type="text" name="pointContent" id="pointContent" required/>
</div>				
						<label for="pointAmount" id="select-amount">환전할 포인트 액수를 입력해주세요.</label><br />
						<input type="text" name="pointAmount" id="pointAmount" class='NumOlny' placeholder="환전할 포인트를 입력해주세요." required />
						<input type="submit" value="환전하기" id="goExch"/>
					</div>
	
					<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
				</form>
			</div>
			
			
			<div class="point-deposit-tutor">
			<div class="pointExc-input1">
				<form action="<%= request.getContextPath() %>/point/pointDeposit" method="post" id="pointDep-frm" onsubmit="return gogo();">
				
					<div class="point-input1">
						<div class="point-input1-notice">
							<label for="pointAmountLabel" id="select-amount">충전할 포인트 액수를 선택해주세요.</label>
						</div>
						<div class="point-input1-index">
				<!-- (예빈)  각 클래스의 전반적인 가격대를 보아 충전 액수가 다소 적다고 생각되어 금액대를 수정하며, 
							배치 편의상 label과 radio의 위치를 바꿈. -->
						<div id="less-point">
							<label for="chargeVal1">10,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal1"  value="10000"/>
							<label for="chargeVal2">15,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal2"  value="15000"/>
							<label for="chargeVal3">20,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal3"  value="20000"/>
						</div>
						<div id="more-point">
							<label for="chargeVal4">30,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal4"  value="30000"/>
							<label for="chargeVal5">50,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal5"  value="50000"/>
							<label for="chargeVal6">100,000 포인트</label>
							<input type="radio" name="pointAmount" id="chargeVal6"  value="100000"/>
						</div>
						</div>

					<div class="point-input3" id="showPayment">
						<!-- 결제 수단 입력될 div -->
					</div>
					<input type="hidden" name="userId" value="<%=memberLoggedIn.getUserId() %>" />
				</form>
			</div>
					<div class="point-input4">
		<input type="button" value="포인트 충전" onclick="location.href='<%=request.getContextPath()%>/point/pointDeposit'"/>
		<input type="button" value="포인트 차감" onclick="location.href='<%=request.getContextPath()%>/point/pointExchange'"/>						
					</div>
		</div> <!-- div 끝 -->
			</div>
	</div>
	<% } %>
	</div>
</div>
	<script>
	
	var $pointExcFrm = $("#pointExc-frm");
	
	//환전 script
	$pointExcFrm.submit(function(){
		
		var $pointAmount = $("#pointAmount");
		var $pointInput = parseInt($pointAmount.val());
		var result = $pointInput - ($pointInput * <%=commission.getCommission()%>);
		console.log(result);
		return confirm("환전될 금액은 " + result + "원 입니다. 환전하시겠습니까? \n환전 수수료"+(<%=commission.getCommission()%>*100)+"%");
	});
	
	$(document).ready(function(){
		$(".NumOnly").keypress(function(event){
			if (event.which && (event.which < 48 || event.which > 57)) {
				event.preventDefault();
			  }
		});
		
		var $payment = $("[name=payment]");
		var $pointDepFrm = $("#pointDep-frm");
	
		
		
		$("[name=payment]").click(function(){
			//console.log(this);
		/* (예빈) 미관상 - 기호 양 옆으로 &nbsp; 붙여줌. */
			if(this.value == 'munsang'){
				$("#showPayment").html("<p id='serial-insert'>일련번호를 입력해주세요.</p><br>"
										+"<input type='text' name='munNo1' class='NumOnly' required/>&nbsp;&#45;&nbsp;<input type='text' name='munNo2' class='NumOlny' required/>&nbsp;&#45;&nbsp;"
										+"<input type='text' name='munNo3' class='NumOnly' required/>&nbsp;&#45;&nbsp;<input type='text' name='munNo4' class='NumOlny' required/>");
			}
			else if(this.value == 'creditCard'){
				$("#showPayment").html("<label for='cardInc'>카드사 선택</label>"
					+"<select name='cardInc' id='cardInc'>"
						+"<option value='' selected disabled>카드사를 선택해 주세요.</option>"
			    		+"<option value='sinhan'>신한카드</option>"
			    		+"<option value='kb'>국민카드</option>"
			    		+"<option value='lotte'>롯데카드</option>"
			    		+"<option value='hahs'>하나카드</option>"
			    		+"<option value='samsung'>삼성카드</option>"
			    		+"<option value='hyundai'>현대카드</option>"
					+"</select><br>"
	
			+"<label for='serialCard'>일련번호</label>"
			+"<input type='text' class='NumOnly' name='serialCard1' required>&nbsp;&#45;&nbsp;"
			+"<input type='text' class='NumOnly' name='serialCard2' required>&nbsp;&#45;&nbsp;"
			+"<input type='text' class='NumOnly' name='serialCard3' required>&nbsp;&#45;&nbsp;"
			+"<input type='text' class='NumOnly' name='serialCard4' required><br>"
	
			+"<label for='expDate'>유효기간(월/년)</label>"
			+"<input type='text' class='NumOnly' name='expDate1'required>&nbsp;&#47;&nbsp;"
			+"<input type='text' class='NumOnly' name='expDate1'required>");
			}
			else if(this.value == 'transfer'){
				$("#showPayment").html("<label for='bankInc'>사용하시는 은행을 선택해주세요.</label><br>"
						+"<select name='bankInc' id='bankInc' required>"
							+"<option value='' selected disabled>은행을 선택해 주세요.</option>"
				    		+"<option value='sinhan'>신한은행</option>"
				    		+"<option value='kb'>국민은행</option>"
				    		+"<option value='woori'>우리은행</option>"
				    		+"<option value='hana'>하나은행</option>"
				    		+"<option value='kko'>카카오뱅크</option>"
				    		+"<option value='tos'>토스</option>"
						+"</select><br>");
			}
		});
		
		$("[name=pointAmount]").click(function(){
			var $pointAmt = $("[class=point-input1-payAmt]");
			const pointInput = parseInt($(this).val());
			const result = pointInput + (pointInput * 0.03);
			$pointAmt.html("[입금하실 금액은 " + result + "원 입니다. (수수료 3% 포함)]");
		});
		
	});
	
	function gogo(){
		//var $payment = $("[name=payment]");
		var checkedPA = $("[name=pointAmount]:checked").length;
		var checkedPP = $("[name=payment]:checked").length;
		console.log(checkedPP);
		var $pointAmount = 0;
		
		if(checkedPA <= 0) {
			alert("금액을 선택해 주세요.");
			return false;
		}
		console.log("???????????????/");
		 if(checkedPP <= 0){
			alert("충전수단을 선택해 주세요.");
			return false;
		} 
		
		$pointAmount = parseInt($("[name=pointAmount]:checked").val());
		var $pointInput = $pointAmount + ($pointAmount * 0.03);
		console.log($pointInput);
		if(confirm($pointAmount+"포인트가 충전됩니다. 진행하시겠습니까?") == false) {
			return false;
		}
	};
	</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>    
