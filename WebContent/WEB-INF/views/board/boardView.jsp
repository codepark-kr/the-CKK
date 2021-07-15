<%@page import="java.text.DecimalFormat"%>
<%@page import="user.tutor.model.vo.TutorResume"%>
<%@page import="board.model.vo.Board"%>
<%@page import="board.model.vo.Schedule"%>
<%@page import="user.tutor.model.vo.Review"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>  
<style>

</style>

<%
	Map<String, Object> map = (Map<String, Object>)request.getAttribute("map");
	List<Review> reviewList = (List<Review>)map.get("reviewList");
	List<Schedule> scheduleList = (List<Schedule>)map.get("scheduleList");
	Board board = (Board)map.get("board");
	TutorResume tr = (TutorResume)map.get("tutorResume");
	User user = (User)map.get("user");
	int cntApplied = 0;
	List<String> userApplied = new ArrayList<>();
	if(scheduleList != null){
		cntApplied = scheduleList.size();
		for(Schedule s : scheduleList){
			userApplied.add(s.getUserId());
		}
	}
	
	Calendar cal = Calendar.getInstance();
	int now = cal.get(Calendar.YEAR);
	cal.setTime(user.getBirthday());
	int age = now - cal.get(Calendar.YEAR);
	
	int sum = 0;
	for(Review review : reviewList){
		sum += review.getReviewScore();
	}
	double grade = (double)sum/reviewList.size();
	
	/* 예빈: 클래스 리스트 - 포인트 액수에 DecimalFormat 대입 */
	DecimalFormat pointF = new DecimalFormat("###,###,### 포인트");
%>
<script>
$(function(){
	
	$("#enrolment-btn").click(function(){
		
		if(<%= memberLoggedIn == null %>){
			alert("로그인 후 이용해주세요.");
			return;
		}
<%
	if(memberLoggedIn != null){
%>
		if(confirm('수강신청 하시겠습니까?') == false) return;
		
		if(<%= memberLoggedIn.getPointSum() < board.getPrice() %>){
			alert("포인트가 부족합니다. 충전 후 이용해주세요.");
			return;
		}
		
		location.href = '<%= request.getContextPath() %>/board/enrolment?classNo=<%= board.getClassNo() %>&userId=<%= memberLoggedIn.getUserId() %>';

	});
	
	$("#cancel-btn").click(function(){
		if(confirm('수강신청을 취소하시겠습니까?') == false) return;
		location.href = '<%= request.getContextPath() %>/board/cancel?classNo=<%= board.getClassNo() %>&userId=<%= memberLoggedIn.getUserId() %>';
<% } %>	
	});

});
</script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board-view.css" />
	<div class="view-div">
		<section id="board-sec">
			<div id="board-title">
				<p class="class-list-title"><%= board.getTitle() %></p>
				<p class="class-list-tutor-name"><%= user.getUserName() %> 튜터</p>
			</div>
	<!-- (예빈) 2번째, 3번째 이미지가 존재하지 않는다고 하더라도 해당 섹션의 wrapper-div는 남겨두고, 값이 존재하는 경우 정보를 삽입하는 것으로 변경한다. 
			       단, 해당 섹션에 대입될 이미지가 존재하지 않는 경우 background-color : 기본 컬러에서 명도가 다소 낮은 컬러로 변경하도록 분기처리 할 것. 
			       이는 컬러 리팩토링 이후에 변경 사항을 반영할 예정. -->
	<div id="img-class-wrapps">
		<div class="img-class-wrapper">
			<div id="cla-li-pics">
				<img src="<%= request.getContextPath() %>/upload/food/<%= board.getClassPic1Ren() %>" alt="" class="cla-li-sec"/>
			</div>
		</div>
			<div class="img-class-wrapper2">
<% 	if(board.getClassPic2Ren() != null && !"".equals(board.getClassPic2Ren())){ %>
		<img src="<%= request.getContextPath() %>/upload/food/<%= board.getClassPic2Ren() %>" alt="" class="cla-li-sec"/>
<% } %> 
			</div>
			<div class="img-class-wrapper3">
<% if(board.getClassPic3Ren() != null && !"".equals(board.getClassPic3Ren())){ %>
		<img src="<%= request.getContextPath() %>/upload/food/<%= board.getClassPic3Ren() %>" alt="" class="cla-li-sec" />
<% 	} %>
			</div>	
	</div>
<!-- 			<p class="bv-t-info-title">수업 정보</p> -->
		
				<!-- (예빈) css 편의상 이 부분은 테이블 아닌 별도의 div로 변경하여 처리한다. -->				
				<!-- (예빈) pre 태그를 사용할 것을 감안하고 게시글을 작성한 후  배치해야 옳을 것이나,
						샘플 데이터의 현재 상황상 사용하지 않는 것이 낫다고 판단되어 우선 주석 처리하고 그에 맞게 배치 함.
						상황 봐서 pre 태그 추가하여 배치하는 방법도 고려해 볼 것. 2020.08.20. -->
				<div class="bv-context">
					<div class="bv-context-inner">
						<p id="bv-context-p">
			<!-- 			<pre> -->
						<%= board.getClassContent() %>
						</p>
			<!-- 			</pre> -->
					</div>
				</div>
		</section>
<%-- 				<tr>
					<th>가격</th>
					<td class="bv-t-price"><%= board.getPrice() %>포인트</td>
<% if(memberLoggedIn != null) {%>
					<td colspan=2><%= board.getApplyExpireYn().equals("Y") ? "<span>신청이 마감되었습니다!</span>" : userApplied.contains(memberLoggedIn.getUserId()) ? "<input type='button' id='cancel-btn' value='신청취소' />" : "<input type='button' id='enrolment-btn' value='신청하기' />" %></td>
<% } else { %>
					<td colspan=2><input type='button' id='enrolment-btn' value='신청하기' /></td>
<% } %>
				</tr> --%>
	<div id="bv-resume-wrapper">	
		<section id="resume-sec">
					<table id="board-table">
				<tr>
					<th colspan=4 class="all-table-title" id="class-tbl-th">수강 정보</th>
				</tr>
				<tr>
					<th>지원 마감일</th>
					<td class="bv-t-apply"><%= board.getLastApplyDate() %></td>
					<th>인원</th>
					<td class="bv-t-capacity">현재 <%= cntApplied %>명 / 최대 <%= board.getCapacity() %>명</td>
				</tr>
				<tr>
					<th>수업 날짜</th>
					<td class="bv-t-date"><%= board.getClassDate() %></td>
					<th>위치</th>
					<td class="bv-t-address"><%= board.getClassAddress() %></td>
				</tr>
				<tr>
					<th>시작 시간</th>
					<td class="bv-t-start"><%= board.getStartTime() %>시</td>
					<th>종료 시간</th>
					<td class="bv-t-end"><%= board.getEndTime() %>시</td>
				</tr>
			</table>	
			<img src="<%= request.getContextPath() %>/upload/tutor/<%= tr.getProfileRen() %>" alt="" />			
			<table id="bv-resume-table">
				<tr>
					<th colspan=5 class="all-table-title"><%= user.getUserName() %> 튜터 정보</th>
				</tr>
				<tr>
					<th>등급</th>
					<td><%= user.getCommGrade() %></td>
					<th>평점</th>
					<td><%= String.format("%.1f", grade) %></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><%= user.getUserName() %></td>
					<th>아이디</th>
					<td><%= user.getUserId() %></td>
					<%-- <th>성별</th>
					<td><%= "M".equals(user.getGender()) ? "남자" : "여자" %></td>
					<th>나이</th>
					<td><%= age %></td> --%>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><%= user.getPhone() %></td>
					<th>이메일</th>
					<td><%= user.getEmail() %></td>
					<%-- <th>가입일</th>
					<td><%= user.getEnrollDate() %></td> --%>
				</tr>
<% 	if(tr.getAwardRecord() != null && !"".equals(tr.getAwardRecord())){ %>
				<tr>
					<th>수상내역</th>
					<td colspan=3><%= tr.getAwardRecord() %></td>
				</tr>
<% 
	} 
	if(tr.getCareer() != null && !"".equals(tr.getCareer())){
%>
				<tr>
					<th>경력</th>
					<td colspan=3><%= tr.getCareer() %></td>
				</tr>
<%
	}
	if(tr.getCertN1() != null && !"".equals(tr.getCertN1())
	&& tr.getCertC1() != null && !"".equals(tr.getCertC1())){ 
%>
				<tr>
					<th colspan=2>자격증 명</th>
					<th colspan=2>자격증 코드</th>
				</tr>
				<tr>
					<td colspan=2><%= tr.getCertN1() %></td>
					<td colspan=2><%= tr.getCertC1() %></td>
				</tr>
<%
	} 

	if(tr.getCertN2() != null && !"".equals(tr.getCertN2())
	&& tr.getCertC2() != null && !"".equals(tr.getCertC2())){ 
%>
				<tr>
					<td colspan=2><%= tr.getCertN2() %></td>
					<td colspan=2><%= tr.getCertC2() %></td>
				</tr>
<%
	}

	if(tr.getCertN3() != null && !"".equals(tr.getCertN3())
	&& tr.getCertC3() != null && !"".equals(tr.getCertC3())){ 
%>				
				<tr>
					<td colspan=2><%= tr.getCertN3() %></td>
					<td colspan=2><%= tr.getCertC3() %></td>
				</tr>
<% } %>				
			</table>
		</section>
		
		<section id="review-sec">
<%
	if(reviewList != null && !reviewList.isEmpty()){ 
%>
			<table id="review-tbl">
				<thead>
				<tr>
					<th colspan=5 class="all-table-title">리뷰</th>
				</tr>
					<tr class="sticky-review-hd">
						<th class="review-th-no">클래스 번호</th>
						<th class="review-th-id">학생 아이디</th>
						<th class="review-th-cont">후기</th>
						<th class="review-th-score">평점</th>
						<th class="review-th-date">작성일</th>
					</tr>
				</thead>
				<tbody>
<%
		for(Review r : reviewList){
%>
					<tr>
						<td><%= r.getClassNo() %></td>
						<td><%= r.getUserId() %></td>
						<td><%= r.getReviewContent() %></td>
						<td><%= r.getReviewScore() %></td>
						<td><%= r.getReviewDate() %></td>
					</tr>		
<%		} %>
				</tbody>
			</table>
<%	} else { %>
				<div id="noReview">아직 등록된 후기가 없습니다.</div>
<% 	} %>
		</section>
<% if(memberLoggedIn != null) {%>
					<div class="bv-what-is-this">
					<%= board.getApplyExpireYn().equals("Y") ? "<span>신청이 마감되었습니다!</span>" : userApplied.contains(memberLoggedIn.getUserId()) ? 
							"<input type='button' id='cancel-btn' value='신청취소' />" : "<input type='button' id='enrollment-btn' value='신청하기' /> " %></div>
					<div class="bv-apply-btn">
						<p class="bv-price"><%= pointF.format(board.getPrice()) %>
						</p>
<% } else { %>
					<div class="bv-apply-btn">
						<p class="bv-price"><%= pointF.format(board.getPrice()) %>
						</p>
						<input type='button' id='enrollment-btn' value='신청하기' />
					</div>
				</div>
<% } %>
	</div>
<% 
	if(memberLoggedIn != null && !"".equals(memberLoggedIn.getUserId())
	&& (memberLoggedIn.getUserId().equals(user.getUserId()) 
   	|| "AD".equals(memberLoggedIn.getCommGrade()))) { 
		if(cntApplied == 0){
%>
   	<input class="board-btn" id="update" type="button" value="게시글 수정" />
<% 
		}
%>
   	<input class="board-btn" id="delete-admin-btn" type="button" value="게시글 삭제" />

  </div> 	
<script>
$(function(){
	$(".board-btn").click(function(){

		if(<%= memberLoggedIn.getUserId().equals(user.getUserId()) %>
		&& (<%= cntApplied %> > 0)){
			if(confirm("수강신청한 인원이 있습니다.\n수수료 " + "<%= board.getPrice()*0.05 %>" + "포인트의 수수료가 부가됩니다.") == false) return;
			var comm = <%= board.getPrice()*0.05 %>;
			if(comm > <%= user.getPointSum() %>){
				alert("포인트가 부족합니다. 충전 후 진행해주세요.");
				return;
			}
		}
		if(confirm("정말 "+ $(this).val() + " 하시겠습니까?") == false) return;
		location.href = "<%= request.getContextPath() %>/board/" + ($(this).val() == "수정" ? "update?classNo=" : "delete?classNo=") + "<%= board.getClassNo() %>";
	});	
});

</script>
<% } %>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>