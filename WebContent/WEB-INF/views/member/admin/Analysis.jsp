<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<% 
List<Integer> list = (List<Integer>)request.getAttribute("list"); 
List<String> pAmount = (List<String>)request.getAttribute("pAmount");
List<Integer> pCnt = (List<Integer>)request.getAttribute("pCnt");
/*  주: 	주간 데이터의 경우 DecimalFormat 적용하여 String 형태로 리턴된다.
		이 아래부터 받아오는 리스트 데이터들은 js의 chart.js 캔버스에 대입될 주간 데이터이며, 
		6일 전 데이터 ~ 당일 데이터가 index[0]~[6]에 분포되어 있다. @yb */
List<Integer> rW = (List<Integer>)request.getAttribute("rWeekly");
List<Integer> mWT = (List<Integer>)request.getAttribute("mWeeklyT");
List<Integer> mWU = (List<Integer>)request.getAttribute("mWeeklyU");

SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 ");
Date date = new Date();
sdf.format(date);
%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/analysis.css" />
<body>

<div class="analy-outer">
<div class="analysis-wrapper">
<div class="index-bg">

<!-- 여기서부터 매출 현황 -->
<div class="a2-outer-wrapper">
  <div class="analy2-wrapper">
  <div class="card2">
      <!-- Custom information -->
      <div class="about2">
        <h3>매출 현황</h3>
        <p class="lead2">최근 7일간 발생한 매출 현황입니다.</p>
      </div>
      
      <!-- Canvas for Chart.js -->
      <canvas id="canvas2"></canvas>
      
      <!-- Custom Axis -->
      <div class="axis2">
        <div class="tick2">
          <span class="day-name2">6일 전</span>
          <span class="value2 value--this2"><%= rW.get(2)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">5일 전</span>
          <span class="value2 value--this2"><%= rW.get(3)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">4일 전</span>
          <span class="value2 value--this2"><%= rW.get(4)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">3일 전</span>
          <span class="value2 value--this2"><%= rW.get(5)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">2일 전</span>
          <span class="value2 value--this2"><%= rW.get(6)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">1일 전</span>
          <span class="value2 value--this2"><%= rW.get(7)/10000 %>만원</span>
        </div>
        <div class="tick2">
          <span class="day-name2">오늘</span>
          <span class="value2 value--this2"><%= rW.get(8)/10000 %>만원</span>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="anal2-cont-wrapper">
  <div class="analy2-cont2">
    <p><%= sdf.format(date)%> 기준,</p>
    총 매출은 <strong><%= pAmount.get(0) %></strong>이며<br>
    그 중 충전 수익은
    <strong><%= pAmount.get(1) %></strong>,<br>
    환전 수익은 <strong><%= pAmount.get(2) %></strong>입니다.<br>
    총 포인트 입출 건수는 <strong><%= pCnt.get(0) %>번</strong>,<br>
    그 중 입금 횟수는 <strong><%= pCnt.get(1) %>번</strong>,<br>
    출금 횟수는 <strong><%= pCnt.get(2) %>번</strong>입니다.
    <div class="go-point-wrapper">
    <button class="go-point">포인트 관리 화면으로 이동</button>
    </div>
  </div>
</div>
                <!-- 여기까지 매출 현황 -->

      <!-- 여기서부터 멤버 현황 -->
    <div class="a1-outer-wrapper">
    <div class="analy1-wrapper">
    <div class="card1">
        <!-- Custom information -->
        <div class="about1">
          <h3>모든 신규 회원</h3>
          <p class="lead1">최근 7일간 신규 가입한 회원의 수입니다.</p>
          <div class="legends1">
            <div class="info1">
              <span class="legend1 legend--this1"></span>
              <p class="legend--this-p">튜터</p>
            </div>
            <div class="info1">
              <span class="legend1 legend--prev1"></span>
              <p class="legend--prev-p1">유저</p>
            </div>
          </div>
        </div>
        
        <!-- Canvas for Chart.js -->
        <canvas id="canvas1"></canvas>
        
        <!-- Custom Axis -->
        <div class="axis1">
          <div class="tick1">
              <!-- 여기에도 마찬가지로 request.getParameter()로 각 값을 대입할 것. -->
            6일 전
            <span class="value1 value--this1"><%= mWT.get(0) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(0) %>명</span>
          </div>
          <div class="tick1">
            5일 전
            <span class="value1 value--this1"><%= mWT.get(1) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(1) %>명</span>
          </div>
          <div class="tick1">
            4일 전
            <span class="value1 value--this1"><%= mWT.get(2) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(2) %>명</span>
          </div>
          <div class="tick1">
            3일 전
            <span class="value1 value--this1"><%= mWT.get(3) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(3) %>명</span>
          </div>
          <div class="tick1">
            2일 전
            <span class="value1 value--this1"><%= mWT.get(4) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(4) %>명</span>
          </div>
          <div class="tick1">
            1일 전
            <span class="value1 value--this1"><%= mWT.get(5) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(5) %>명</span>
          </div>
          <div class="tick1">
            	오늘
            <span class="value1 value--this1"><%= mWT.get(6) %>명</span>
            <span class="value1 value--prev1"><%= mWU.get(6) %>명</span>
          </div>
        </div>
      </div>
<!-- 여기까지 차트 디자인 -->

      <!-- 여기서부터 본문 영역 -->
    </div>
  </div>
  <div class="anal1-cont-wrapper">
      <div class="analy1-cont1">
        <div class="go-mm-wrapper">
        <button class="go-mm">회원 관리 화면으로 이동</button>
      </div>
        <p><%= sdf.format(date)%> 기준,</p>
        전체 회원은 <strong><%= list.get(0) %>명</strong>이며<br>
        그 중 현재 활동 중인 회원은 
        <strong><%= list.get(0) - list.get(1) %>명</strong>,<br>
        사이트를 탈퇴한 회원은 <strong><%= list.get(1) %>명</strong>입니다.<br>
        활동 중인 회원 중 일반 유저는 
        <strong><%= list.get(3) %>명</strong>,<br>
        튜터 회원은 <strong><%= list.get(2) %>명</strong>입니다.
      </div>
    </div>
  <!-- 여기까지 멤버 현황 -->
</div>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
      <script>
        // 여기서부터 매출 현황

        var canvas2 = document.getElementById("canvas2");

// Apply multiply blend when drawing datasets
var multiply2 = {
  beforeDatasetsDraw: function(chart, options, el) {
    chart.ctx.globalCompositeOperation = 'multiply';
  },
  afterDatasetsDraw: function(chart, options) {
    chart.ctx.globalCompositeOperation = 'source-over';
  },
};

var config2 = {
    type: 'line',
    data: {
        labels: ["", "", "", "", "", "", "", "", ""],
        datasets: [
          {
              label: '',
              data: [<%= rW.get(1)%>, <%= rW.get(2)%>, <%= rW.get(3)%>, 
            	  <%= rW.get(4)%>, <%= rW.get(5)%>, <%= rW.get(6)%>,<%= rW.get(7)%>, <%= rW.get(8)%>],
              fill: false,
              borderColor: 'rgba(255, 255, 255, 0.2)',
              borderWidth: 2,
              pointBackgroundColor: 'transparent',
              pointBorderColor: '#FFFFFF',
              pointBorderWidth: 3,
              pointHoverBorderColor: 'rgba(255, 255, 255, 0.2)',
              pointHoverBorderWidth: 10,
              lineTension: 0,
          }
        ]
    },
    options: {
    	responsive: false,
      elements: { 
        point: {
          radius: 7,
          hitRadius: 7, 
          hoverRadius: 7 
        } 
      },
      legend: {
        display: false,
      },
      tooltips: {
      	backgroundColor: 'sales',
        displayColors: false,
        bodyFontSize: 12,
        callbacks: {
          label: function(tooltipItems, data) { 
            return tooltipItems.yLabel+"원";
          }
        }
      },
      scales: {
        xAxes: [{
          display: false,
        }],
        yAxes: [{
          display: false,
          ticks: {
            beginAtZero: true,
          },
        }]
      }
    },
    plugins: [multiply2],
};

window.chart = new Chart(canvas2, config2);

// 여기까지 매출 현황

// 여기서부터 멤버 현황
var canvas1 = document.getElementById("canvas1");

// Apply multiply blend when drawing datasets
var multiply1 = {
  beforeDatasetsDraw: function(chart1, options, el) {
    chart.ctx.globalCompositeOperation = 'multiply';
  },
  afterDatasetsDraw: function(chart1, options) {
    chart.ctx.globalCompositeOperation = 'source-over';
  },
};

// 이 곳에 대입되는 색의 값은 순서대로 --this와 --prev에 대입된 값과 일치해야 함.
// 전위에 선언된 값이 상단의 색, 후위에 선언된 색이 하단의 색으로 gradient 적용됨.
// 주. 너무 어두운 색을 선택하는 경우 중첩된 색이 예쁘게 나오지 않으므로 alpha 값을 대입하거나
// 명도가 높은 색상을 선택할 것.
var gradT = canvas1.getContext('2d').createLinearGradient(0, 0, 0, 150);
gradT.addColorStop(0, 'rgba(6, 158, 6, 0.8)');
gradT.addColorStop(1, 'rgba(165, 151, 253, 0.453)');

var gradU = canvas1.getContext('2d').createLinearGradient(0, 0, 0, 150);
gradU.addColorStop(0, '#FF8787');
gradU.addColorStop(1, 'rgba(236, 140, 255, 0.8)');


var config1 = {
    type: 'line',
    data: {
        labels: ["", "", "", "", "", "", ""],
        datasets: [
          {
              label: 'tutors',
              data: [<%= mWT.get(0)%>, <%= mWT.get(1)%>, <%= mWT.get(2)%>, <%= mWT.get(3)%>, 
              <%= mWT.get(4)%>, <%= mWT.get(5)%>, <%= mWT.get(6)%>],
              backgroundColor: gradT,
              borderColor: 'transparent',
              pointBackgroundColor: '#FFFFFF',
              pointBorderColor: '#FFFFFF',
              lineTension: 0.40,
          },
          {
              label: 'users',
            // 값에 변화를 주면 그래프도 함께 따라옴. 
            // data array 내부에 도입할 각 값들(int)은 request.getParameter로 불러온 후 대입할 것.
              data: [<%= mWU.get(0)%>, <%= mWU.get(1)%>, <%= mWU.get(2)%>, <%= mWU.get(3)%>, 
            	  <%= mWU.get(4)%>, <%= mWU.get(5)%>, <%= mWU.get(6)%>],
              backgroundColor: gradU,
              borderColor: 'transparent',
              pointBackgroundColor: '#FFFFFF',
              pointBorderColor: '#FFFFFF',
              lineTension: 0.40,
          }
        ]
    },
    options: {
    		elements: { 
        	point: {
          	radius: 0,
          	hitRadius: 5, 
            hoverRadius: 5 
          } 
        },
    		legend: {
        		display: false,
        },
        scales: {
            xAxes: [{
            		display: false,
            }],
            yAxes: [{
            		display: false,
                ticks: {
                	beginAtZero: true,
              	},
            }]
        }
    },
    plugins: [multiply1],
};

window.chart = new Chart(canvas1, config1);
      </script>
</body>
</html>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>