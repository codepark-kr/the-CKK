<%@page import="java.text.DecimalFormat"%>
<%@page import="board.model.vo.Board"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>  
<%
	List<Board> list = (List<Board>)request.getAttribute("list");
	List<String> cList = new ArrayList<>();
	List<String> lList = new ArrayList<>();	
	String[] categoryArr = (String[])request.getParameterValues("category");
	if(categoryArr != null && categoryArr.length != 0){
		for(String s : categoryArr){
			cList.add(s);
		}
	}
	String[] locationArr = (String[])request.getParameterValues("location");
	if(locationArr != null && locationArr.length != 0){
		for(String s : locationArr){
			lList.add(s);
		}
	}
	String keywordType = (String)request.getParameter("keywordType");
	String keyword = (String)request.getParameter("keyword");
	
	String lowPriceS = request.getParameter("lowPrice");
	String highPriceS = request.getParameter("highPrice");
	int lowPrice = lowPriceS != null && !lowPriceS.isEmpty() ? Integer.parseInt(lowPriceS) : 0;
	int highPrice = highPriceS != null && !highPriceS.isEmpty() ? Integer.parseInt(highPriceS) : 0;
	
	String classDate = request.getParameter("classDate");
	
	Calendar cal = Calendar.getInstance();
	int now = cal.get(Calendar.YEAR);
	if(memberLoggedIn != null){
		cal.setTime(memberLoggedIn.getBirthday());
	}
	/* (예빈) 클래스 리스트 - 포인트 액수에 DecimalFormat 대입 */
	DecimalFormat pointF = new DecimalFormat("###,###,### 포인트");
%>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-list.css" />

<script>
	$(function(){
/* 		$(".search").click(function(){
			$(this).next("section").slideToggle();
		}); */

		/* css 편의상 무식하게 바꿔부럿어요. 준혁시 미안해요 ㅎ; */
		$("#class-s-btn1").click(function(){
			$("#search-category").slideToggle();
		});
		$("#class-s-btn2").click(function(){
			$("#search-location").slideToggle();
		});
		$("#class-s-btn3").click(function(){
			$("#search-price").slideToggle();
		});
		$("#class-s-btn4").click(function(){
			$("#search-date").slideToggle();
		});
		
		$(".priceInput").keypress(function(event){
			if (event.which && (event.which < 48 || event.which > 57)) {
				event.preventDefault();
			  }
		});	
	});
	
	function dateReset(){
		$("[name=classDate]").val("");
	};
	
	
	function gotoView(classNo, category){
		
		if(category == "AL"){
			
			var msg;
			msg = "미성년자는 이용하실 수 없습니다.";
			
			if(<%= memberLoggedIn == null %>){
				msg += "\n로그인 후 이용해주세요.";
				alert(msg);
				return;
			}
			else if(<%= now - cal.get(Calendar.YEAR) %> <= 19){
				alert(msg);
				return;
			}
		}
		
		location.href='<%= request.getContextPath() %>/board/view?classNo=' + classNo;
	};
	
	function openClass(){
		//로그인한 사람의 이력서 여부 확인 
		//Y = 진행 / N = 이력서를 먼저 등록해주세요 / P = 아직 이력서 심사중입니다.
		if(<%= memberLoggedIn == null %>){
			alert("로그인 후 이용 가능한 기능 입니다.");
			return;
		} 
		else if(<%= memberLoggedIn != null && "N".equals(memberLoggedIn.getResumeYNP()) %>) {
			alert("이력서를 먼저 등록해주세요.");
			return;
		}
		else if(<%= memberLoggedIn != null && "P".equals(memberLoggedIn.getResumeYNP()) %>) {
			alert("아직 이력서 심사가 진행중입니다.");
			return
		}
		
		location.href = "<%= request.getContextPath() %>/board/insert";
	};
</script>
<div class="class-list-wrapper">
<p class="class-list-title" onclick="location.href='<%= request.getContextPath() %>/board/list'">
	<!-- (예빈) 클래스 관리 : 관리자 권한을 가진 로그인 사용자에게만 클래스 삭제 버튼을 노출한다.
		   단, 클래스 list가 아닌 클래스 view단에서 처리하는 것으로 한다. (관리자라 하더라도 글을 삭제하기 전에 게시글 내용을 확인해야 하므로) -->
<%= memberLoggedIn != null && memberLoggedIn.getCommGrade().equals(adminGrade) ? "클래스 관리" : "클래스 리스트" %>
</p>
	<form class="class-list-form" action="<%= request.getContextPath() %>/board/list"
		  method="get">
		<div id="board-search">
			<div class="search" id="class-search-buttons">
				<input type="button" value="카테고리 선택" class="class-search-btn" id="class-s-btn1"/>
				<input type="button" value="지역 선택" class="class-search-btn" id="class-s-btn2"/>
				<input type="button" value="가격대 선택" class="class-search-btn" id="class-s-btn3"/>
				<input type="button" value="수업일 선택" class="class-search-btn" id="class-s-btn4"/>
			</div>
			<section class="search-sec" id="search-category">
				<input type="checkbox" name="category" id="MD" value="MD" <%= cList.contains("MD") ? "checked" : "" %>/>
				<label for="MD">본 요리 (Main Dish)</label>
				<br />
				<input type="checkbox" name="category" id="DS" value="DS" <%= cList.contains("DS") ? "checked" : "" %>/>
				<label for="DS">제과/제빵 (Dessert)</label>
				<br />
				<input type="checkbox" name="category" id="DR" value="DR" <%= cList.contains("DR") ? "checked" : "" %>/>
				<label for="DR">음료 (Drinks)</label>
				<br />
				<input type="checkbox" name="category" id="AL" value="AL" <%= cList.contains("AL") ? "checked" : "" %>/>
				<label for="AL">주류 (Liquors)</label>
			</section>
			
			<section class="search-sec" id="search-location">
				<input type="checkbox" name="location" id="SOUTH" value="SOUTH" <%= lList.contains("SOUTH") ? "checked" : "" %>/>
				<label for="SOUTH">강남구</label>
				<br />
				<input type="checkbox" name="location" id="NORTH" value="NORTH" <%= lList.contains("NORTH") ? "checked" : "" %>/>
				<label for="NORTH">강북구</label>
				<br />
				<input type="checkbox" name="location" id="EAST" value="EAST" <%= lList.contains("EAST") ? "checked" : "" %>/>
				<label for="EAST">강동구</label>
				<br />
				<input type="checkbox" name="location" id="WEST" value="WEST" <%= lList.contains("WEST") ? "checked" : "" %>/>
				<label for="WEST">강서구</label>
			</section>

			<section class="search-sec" id="search-price">
				<input type="text" class="priceInput" name="lowPrice" id="low" style="text-align: center;" placeholder="최소가격(생략가능)" value="<%= lowPrice != 0 ? lowPrice : "" %>"/>
					<p class="cla-won">원</p>
					<p class="from-to">~</p>
				<input type="text" class="priceInput" name="highPrice" id="high" style="text-align: center;" placeholder="최대가격(생략가능)" value="<%= highPrice != 0 ? highPrice : "" %>"/>
					<p class="cla-won">원</p>
			</section>

			<section class="search-sec" id="search-date">
				<input type="date" name="classDate" value="<%= classDate != null && !"".equals(classDate) ? classDate : "" %>"/>
					<br />
				<input type="button" value="초기화" onclick="dateReset();" class="cla-reset-btn"/>
			</section>
			
			<div class="keyword-sec" id="keyword-sec">
				<select name="keywordType" id="keywordType">
					<option value="title" <%= keywordType != null && !keywordType.isEmpty() && keywordType.equals("title") ? "selected" : "" %>>제목</option>
					<option value="class_content" <%= keywordType != null && !keywordType.isEmpty() && keywordType.equals("class_content") ? "selected" : "" %>>내용</option>
					<option value="user_name" <%= keywordType != null && !keywordType.isEmpty() && keywordType.equals("user_name") ? "selected" : "" %>>튜터 이름</option>
				</select>
				<input type="text" name="keyword" id="keyword" value="<%= keyword != null && !keyword.isEmpty() ? keyword : "" %>" placeholder="키워드를 입력해 주세요."/>
			</div>
		</div>
		<input type="submit" value="검색하기" class="execute-search"/>
		<div class="open-pls">
		<input type="button" value="클래스 개설하기" onclick="openClass();" class="open-class-btn" />
		</div>
	</form>
	
	<div id="board-class-list">
</div>
		<div class="board-outer-class">
<% 
	if(list != null && !list.isEmpty()){ 
		for(Board b : list){
%> 	
 <div class="img-class-wrapper"> 
		<!-- (예빈) 신청이 마감된 경우: 해당 섹션을 100% 흑백  + 105% 컨트라스트 처리하며, 수업료 항목을  "신청이 마감되었습니다!":black으로 대체한다. 
			   단, 신청이 마감된 경우 선언될 inline-style 속성은 복수가 될 것이고 (background-image, filter) 
			   그렇지 않은 경우 단수로 처리(background-image, NO FILTER)해야 하므로  문법에 맞추기 위해 else절 또한 함께 사용한다. 
			   문법에 맞게 작성한 경우 filter 선언문에 노란 줄이 쳐지는 것이 정상임. -->
			<section class="cla-li-sec" 
					 style='background-image: url("<%= request.getContextPath() %>/upload/food/<%= b.getClassPic1Ren() %>")
					 <% if(b.getApplyExpireYn().equals("N")) { %>' 
					 <% } else { %>; filter: grayscale(100%) contrast(105%)' <% } %>
					 onclick="gotoView(<%= b.getClassNo() %>, '<%= b.getCategory() %>');" id="class-li-pics">
					 
					 <div class="class-list-info">
				<p class="cla-li-title"><%= b.getTitle() %></p>
				<p class="cla-li-tutor"> &nbsp;<%= b.getTutorName() %>&nbsp;튜터&nbsp;</p>
				<p class="cla-li-price" <% if(b.getApplyExpireYn().equals("Y")) { %> style='color: black; font-size: 0.8; letter-spacing: 1px'<%} %>>
				<%= b.getApplyExpireYn().equals("N") ? pointF.format(b.getPrice()) : "신청이 마감되었습니다!" %></p>
				</div>
			</section>
 	</div> 
<% 
		}
	} else {
%>

	참여가능한 클래스가 존재 하지 않습니다.

<% 	} %>
		</div>
	</div>
	
	<div id="board-pageBar">
		<p id="pagebar"><%= request.getAttribute("pageBar") %></p>
	</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
