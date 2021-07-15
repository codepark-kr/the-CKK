<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.model.vo.Schedule"%>
<%@page import="board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/darkHeader.jsp"%>
<%
	
	Map<String, Object> map = (Map<String, Object>)request.getAttribute("map");
	List<Board> blist = (List<Board>)map.get("blist");
	List<Board> tlist = (List<Board>)map.get("tlist");
	List<Schedule> slist = (List<Schedule>)map.get("slist");
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	
	
%>
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/schedule.css" />
<script src="<%=request.getContextPath()%>/js/jquery-3.5.1.js"></script>
<div class="calendar-outer">
<div class="calendar-wrapper">
<div id="calendar"></div>
<div class="cal-sec-wrapper">
<section class="calendar-sec">
	<!-- 마감된 수업과 현재 진행 중인 수업  -->
	<div id="div-class" class="super-class">
	<p class="sche-title">내 클래스 정보</p>
		<div class="month-record">
		<span class="span-record">스케줄 조회를 희망하는 기간을 선택해 주세요.</span>
		</div>
			<input type="month" name="check-month" id="chk-month" />
			<br />
			<div class="record" id="recordId"></div>
			<div class="co-record" id="co-recordId"></div>			
	</div>
</section>
</div>
</div>
</div>
<script>
		
		
<%-- 	    $('input[type="month"]').change(function(){
	      	// 값 확인용
	    	/*  alert(this.value) */
	        var inputDate = new Date(this.value);
		        
	        
	      	  $.ajax({
		        	 url : '<%= request.getContextPath()%>/member/recordschedule',
					 type : 'get',
					 data : {   
						 	"userId" : '<%= memberLoggedIn.getUserId()%>',  
						    "year" : inputDate.getFullYear(),
						    "month" : inputDate.getMonth()+1
						           },
					 dataType:"json",
					 success : function(data) {
			
						 var html = "<select name='selectRecord' id='recordId'><option selected disabled>강의 선택</option>";
						 	$.each(data, function(i, board){
								html += "<option class='month-record' id='year-record' value="+ board["classNo"] +">";	
								html += board["title"];
								html += "</option>";
							});	
						 
						 html += "</select>";
						 $("#recordId").html(html);
						 $("#recordId").css("color", "red");
		 
		
					 },
					 error : function(xhr, textStatus, err){
			
						 alert("에러입니다");
						 console.log(xhr, textStatus, err);
					 }
				  });  
	      	  
	        });  --%>
	        
<%-- 	        <% if(memberLoggedIn.getCommGrade().equals("US")){ %> --%>
	        
$('input[type="month"]').change(function(){
    // 값 확인용
    /*  alert(this.value) */
    var inputDate = new Date(this.value);
      $.ajax({
             url : '<%= request.getContextPath()%>/member/recordschedule',
             type : 'get',
             data : {   "userId": '<%= memberLoggedIn.getUserId() %>',
            	 		"userComm" : '<%= memberLoggedIn.getCommGrade() %>',
                        "year" : inputDate.getFullYear(),
                        "month" : inputDate.getMonth()+1
                    },
             dataType:"json",
             success : function(data) {
    	console.log(data);
                 var html = "<select name='selectRecord' id='recordId'><option selected disabled>강의 선택</option>";
                    $.each(data, function(i, list){
						$.each(list, function(j, board){
                            html += "<option class='month-record' id='year-record' value="+ board["classNo"] +">";
                            html += board["title"];
                            html += "</option>";
						});
                    });
                 html += "</select>";
                 $("#recordId").html(html);
                 $("#recordId").css("color", "red");
             },
             error : function(xhr, textStatus, err){
                 alert("에러입니다");
                 console.log(xhr, textStatus, err);
             }
          });
    });

	        

		
				$(function(){
					$("#recordId").change(function(){
						//값 확인용
						console.log($(this).find("option:selected").val());
						var classNo = $(this).find("option:selected").val()
						
					 $.ajax({
							url : '<%= request.getContextPath()%>/member/coworkschedule',
							 type : 'get',
							 data : {
								 		"classNo" : classNo
				        	 		},
							 dataType:"json",
							 success : function(data) {
								 console.log(data);
								var no = 1;
								var content = data[1]["tutorName"];
								
							/* (예빈)배치 편의/순서상 타이틀은 따로 뺀 후 튜터 이름과 수업 일자를 p 태그 하나에 넣고 wrapper:div를 추가합니다. */
								var html = "<p class='tutor-day'>"+data[0]["day"]+"일, "+data[1]["tutorName"]+" 튜터님과 수업 </p>";
								/* 	html += "<p class='class-date'>수업 일자 : "+data[0]["day"]+"일 </p>"; */
								  	html += "<div class='sch-cont'><p class='sch-content'>"+data[1]["classContent"]+ "</p></div><hr id='sch-hr'><div class='mates-wrapper'>";
									html +=  "<h2 class='co-h2'>클래스 메이트 목록</h2>";
									html += "<table class='classTable' id='co-table'>";
									html += "<tr class='tr-phone'><th class='th-name'>이름</th><th class='th-phone'>연락처</th></tr></div>"
								 	$.each(data, function(i, board){
										html += '<tr class='+"co-tr" + i +'>';
										html += "<td>"+ board["userName"] +"</td>" + "<td>"+ board["phone"] +"</td>";
								  		html += "</tr>";	 
									});	
								  html += "</table>";
								 $("#co-recordId").html(html);
						
				
							 },
							 error : function(xhr, textStatus, err){
					
								 alert("에러입니다");
								 console.log(xhr, textStatus, err);
							 }
						}); 
					}); 
					
				});

</script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.5.1/moment.min.js"
	integrity="sha512-r8lfwD+xE5L0FjDLicb4ZiF32sJqCAOnLN0nxQq5VW0t0nFEEiNIvTZ/I9Su4ulGpDCfYjXsSFZsT9FpILp2+A=="
	crossorigin="anonymous"></script>
<script>
$(function(){
    !function() {
	
	  var today = moment();
	
	  function Calendar(selector, events) {
	    this.el = document.querySelector(selector);
	    this.events = events;
	    this.current = moment().date(1);
	    this.events.forEach(function(ev) {
	    ev.date = moment(ev.date);
	    });
	    this.draw();
	    var current = document.querySelector('.today');
	    if(current) {
	      var self = this;
	      window.setTimeout(function() {
	        self.openDay(current);
	      }, 500);
	    }
	    
	  }
	
	  Calendar.prototype.draw = function() {
	    //Create Header
	    this.drawHeader();
	
	    //Draw Month
	    this.drawMonth();
	
	    this.drawLegend();
	  }
	
	  Calendar.prototype.drawHeader = function() {
	    var self = this;
	    if(!this.header) {
	      //Create the header elements
	      this.header = createElement('div', 'header');
	      this.header.className = 'header';
	
	      this.title = createElement('h1');
	
	      var right = createElement('div', 'right');
	      right.addEventListener('click', function() { self.nextMonth(); });
	
	      var left = createElement('div', 'left');
	      left.addEventListener('click', function() { self.prevMonth(); });
	
	      //Append the Elements
	      this.header.appendChild(this.title); 
	      this.header.appendChild(right);
	      this.header.appendChild(left);
	      this.el.appendChild(this.header);
	    }
	
	    this.title.innerHTML = this.current.format('MMMM YYYY');
	  }
	
	  Calendar.prototype.drawMonth = function() {
	    var self = this;
	    
	    
	    if(this.month) {
	      this.oldMonth = this.month;
	      this.oldMonth.className = 'month out ' + (self.next ? 'next' : 'prev');
	      this.oldMonth.addEventListener('webkitAnimationEnd', function() {
	        self.oldMonth.parentNode.removeChild(self.oldMonth);
	        self.month = createElement('div', 'month');
	        self.backFill();
	        self.currentMonth();
	        self.fowardFill();
	        self.el.appendChild(self.month);
	        window.setTimeout(function() {
	          self.month.className = 'month in ' + (self.next ? 'next' : 'prev');
	        }, 16);
	      });
	    } else {
	        this.month = createElement('div', 'month');
	        this.el.appendChild(this.month);
	        this.backFill();
	        this.currentMonth();
	        this.fowardFill();
	        this.month.className = 'month new';
	    }
	  }
	
	  Calendar.prototype.backFill = function() {
	    var clone = this.current.clone();
	    var dayOfWeek = clone.day();
	
	    if(!dayOfWeek) { return; }
	
	    clone.subtract('days', dayOfWeek+1);
	
	    for(var i = dayOfWeek; i > 0 ; i--) {
	      this.drawDay(clone.add('days', 1));
	    }
	  }
	
	  Calendar.prototype.fowardFill = function() {
	    var clone = this.current.clone().add('months', 1).subtract('days', 1);
	    var dayOfWeek = clone.day();
	
	    if(dayOfWeek === 6) { return; }
	
	    for(var i = dayOfWeek; i < 6 ; i++) {
	      this.drawDay(clone.add('days', 1));
	    }
	  }
	
	  Calendar.prototype.currentMonth = function() {
	    var clone = this.current.clone();
	
	    while(clone.month() === this.current.month()) {
	      this.drawDay(clone);
	      clone.add('days', 1);
	    }
	  }
	
	  Calendar.prototype.getWeek = function(day) {
	    if(!this.week || day.day() === 0) {
	      this.week = createElement('div', 'week');
	      this.month.appendChild(this.week);
	      console.log(this.week);
	    }
	  }
	
	  Calendar.prototype.drawDay = function(day) {
	    var self = this;
	    this.getWeek(day);
	
	    //Outer Day
	    var outer = createElement('div', this.getDayClass(day));
	    outer.addEventListener('click', function() {
	      self.openDay(this);
	    });
	
	    //Day Name
	    var name = createElement('div', 'day-name', day.format('ddd'));
	
	    //Day Number
	    var number = createElement('div', 'day-number', day.format('DD'));
	
	
	    //Events
	    var events = createElement('div', 'day-events');
	    this.drawEvents(day, events);
	
	    outer.appendChild(name);
	    outer.appendChild(number);
	    outer.appendChild(events);
	    this.week.appendChild(outer);
	  }
	
	  Calendar.prototype.drawEvents = function(day, element) {
	    if(day.month() === this.current.month()) {
	      var todaysEvents = this.events.reduce(function(memo, ev) {
	        if(ev.date.isSame(day, 'day')) {
	          memo.push(ev);
	        }
	        return memo;
	      }, []);
	
	      todaysEvents.forEach(function(ev) {
	        var evSpan = createElement('span', ev.color);
	        element.appendChild(evSpan);
	      });
	    }
	  }
	
	  Calendar.prototype.getDayClass = function(day) {
	    classes = ['day'];
	    if(day.month() !== this.current.month()) {
	      classes.push('other');
	    } else if (today.isSame(day, 'day')) {
	      classes.push('today');
	    }
	    return classes.join(' ');
	  }
	
	  Calendar.prototype.openDay = function(el) {
	    var details, arrow;
	    var dayNumber = +el.querySelectorAll('.day-number')[0].innerText || +el.querySelectorAll('.day-number')[0].textContent;
	    var day = this.current.clone().date(dayNumber);
	
	    var currentOpened = document.querySelector('.details');
	
	   /* 해당 라인(row), 즉 이번 주 라인에 이벤트가 존재한다면 detail-box를 연다. */
	    if(currentOpened && currentOpened.parentNode === el.parentNode) {
	      details = currentOpened;
	      arrow = document.querySelector('.arrow');
	    } else {
	   /* 이벤트가 다른 week-라인에 존재한다면  detail-box를 닫는다. */
      //currentOpened && currentOpened.parentNode.removeChild(currentOpened);
      if(currentOpened) {
        currentOpened.addEventListener('webkitAnimationEnd', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('oanimationend', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('msAnimationEnd', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('animationend', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.className = 'details out';
      }

	      //디테일 컨테이너-요소를 생성한다. 즉 회색 박스에 내용이 표기되는 그 부분
	      details = createElement('div', 'details in');
	
	      //arrow, 디테일-컨테이너가 가리킬 세모꼴의 화살표가 위치할 포지션을 설정한다. '하루치-박스'의 정 중앙에 오도록 설정할 것.
	      var arrow = createElement('div', 'arrow');
	
	      //Create the event wrapper
	      details.appendChild(arrow);
	      el.parentNode.appendChild(details);
	    }
		
	    var todaysEvents = this.events.reduce(function(memo, ev) {
	      if(ev.date.isSame(day, 'day')) {
	        memo.push(ev);
	      }
	      return memo;
	    }, []);
	
	    this.renderEvents(todaysEvents, details);
	
	    /* 현재 시점에서 한 칸(즉 하루분)의 정 중앙에 가까운 위치는 27px로, 각 칸의 크기를 늘리면 이 수치도 변경하여야 한다. em으로 수정함. */
	    arrow.style.left = el.offsetLeft - el.parentNode.offsetLeft + 2.7 + 'em';
/* 	    arrow.style.left = el.offsetLeft - el.parentNode.offsetLeft + 27 + 'px'; */
	  }
	
	  Calendar.prototype.renderEvents = function(events, ele) {
	    //Remove any events in the current details element
	    var currentWrapper = ele.querySelector('.events');
	    var wrapper = createElement('div', 'events in' + (currentWrapper ? ' new' : ''));
	
	    events.forEach(function(ev) {
	      var div = createElement('div', 'event');
	      var square = createElement('div', 'event-category ' + ev.color);
	      var span = createElement('span', 'click-span', ev.eventName);
		
	      div.appendChild(square);
	      div.appendChild(span);
	      wrapper.appendChild(div);
	     	
	 
	    });
	    
	
	    if(!events.length) {
	      var div = createElement('div', 'event empty');
	      var span = createElement('span', '', '수강 클래스가 없습니다.');
	
	      div.appendChild(span);
	      wrapper.appendChild(div);
	    }
	
	    if(currentWrapper) {
	      currentWrapper.className = 'events out';
	      currentWrapper.addEventListener('webkitAnimationEnd', function() {
	        currentWrapper.parentNode.removeChild(currentWrapper);
	        ele.appendChild(wrapper);
	      });
	      currentWrapper.addEventListener('oanimationend', function() {
	        currentWrapper.parentNode.removeChild(currentWrapper);
	        ele.appendChild(wrapper);
	      });
	      currentWrapper.addEventListener('msAnimationEnd', function() {
	        currentWrapper.parentNode.removeChild(currentWrapper);
	        ele.appendChild(wrapper);
	      });
	      currentWrapper.addEventListener('animationend', function() {
	        currentWrapper.parentNode.removeChild(currentWrapper);
	        ele.appendChild(wrapper);
	      });
	    } else {
	      ele.appendChild(wrapper);
	    }
	  }
	
	  Calendar.prototype.drawLegend = function() {
	    var legend = createElement('div', 'legend');
	    var calendars = this.events.map(function(e) {
	      return e.calendar + '|' + e.color;
	    }).reduce(function(memo, e) {
	      if(memo.indexOf(e) === -1) {
	        memo.push(e);
	      }
	      return memo;
	    }, []).forEach(function(e) {
	      var parts = e.split('|');
	      var entry = createElement('span', 'entry ' +  parts[1], parts[0]);
	      legend.appendChild(entry);
	    });
	    legend.addEventListener('click', function() { self.nextMonth(); });
	    this.el.appendChild(legend);
	  }
	
	  Calendar.prototype.nextMonth = function() {
	    this.current.add('months', 1);
	    this.next = true;
	    this.draw();
	  }
	
	  Calendar.prototype.prevMonth = function() {
	    this.current.subtract('months', 1);
	    this.next = false;
	    this.draw();
	  }
	
	  window.Calendar = Calendar;
	
	 /* 요소 생성 : 태그명, 클래스명, 내부 데이터(텍스트) : 위에서 선언한 데이터들을 바탕으로 이를 묶어서 대입한다. */
	  function createElement(tagName, className, innerText) {
	    var ele = document.createElement(tagName);
	    if(className) {
	      ele.className = className;
	    }
	    if(innerText) {
	      ele.innderText = ele.textContent = innerText;
	    }
	    return ele;
	  }
	  }();
	
	  !function() {
	  	  var data = [

	 /* 각 카테고리 설정 및 데이터 대입 파트 */
<% if(memberLoggedIn.getCommGrade().equals("US")){ %>
	  		  
  		<% 
		int i = 1;
  		for(Board b : blist) {
  		%>	
  		/* (예빈) 이 아래로 붉은 줄이 쳐질 때가 있으나 라인 흔들어주면 되고, 정상 작동 되는 것을 확인함. */
  			    { eventName: '<%= b.getTitle()%> : <%= b.getStartTime()%>시 ~ <%= b.getEndTime() %>시 <%= b.getTutorName() %> 튜터 ',
  					/* (예빈)legend에 올라가는 각 카테고리 정보는 이 곳으로 와야 함. */
  			    	calendar : '<%= b.getCategory().equals("MD") ? "Main Dish(본 요리) 분야" : b.getCategory().equals("DS") 
  			    			? "Dessert(디저트) 분야" : b.getCategory().equals("DR") ? "Drinks(음료) 분야" : "Liquors(주류) 분야" %>',
  					color : '<%= b.getCategory().equals("MD") ? "orange" : b.getCategory().equals("DS") ? "blue" : b.getCategory().equals("DR") ? "yellow" : "green" %>',
  					date : '<%= sd.format(b.getClassDate())%>'
  		}<%= i == blist.size() ? "" : "," %>  
  			    
  		<%
  			i++;
  		} 
  		%>
<% }else if(!(memberLoggedIn.getCommGrade().equals("US") && memberLoggedIn.getCommGrade().equals("AD")) ){ %>
		
		<%for(Board t : tlist){ %>
				{   eventName: '<%= t.getTitle()%> : <%= t.getStartTime()%>시 ~ <%= t.getEndTime() %>시',
					calendar : 'My Class',
					color : 'white',
					date : '<%= sd.format(t.getClassDate())%>'
				},
		<%
	  	}
		int i = 1;
		for(Board b : blist) {
		%>
			    {   eventName: '<%= b.getTitle()%> : <%= b.getStartTime()%>시 ~ <%= b.getEndTime() %>시  <%= b.getTutorName() %> 튜터',
					calendar : '<%= b.getCategory().equals("MD") ? "본 요리 분야" : b.getCategory().equals("DS") ? "제과/제빵 분야" : b.getCategory().equals("DR") ? "음료 분야" : "주류 분야" %>',
					color : '<%= b.getCategory().equals("MD") ? "orange" : b.getCategory().equals("DS") ? "blue" : b.getCategory().equals("DR") ? "yellow" : "green" %>',
					date : '<%= sd.format(b.getClassDate())%>'
				}<%= i == blist.size() ? "" : "," %>  
		<%
			i++;
		} 
	}
		%>
  	];

 	 console.log(data);


	var calendar = new Calendar('#calendar', data);
	
	}();
});
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>

