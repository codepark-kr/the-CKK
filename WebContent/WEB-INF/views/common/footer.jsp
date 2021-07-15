<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <style>
        @font-face {
            font-family: 'IBMPlexSansKR-Text';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Text.woff') format('woff');
            font-weight: normal;
            font-style: normal;
        }
        .public-footer{
        	top: 104em;
        	position: absolute;
            padding-top: 1.6em;
            padding-bottom: 1.6em;
            text-align: center;
            vertical-align: center;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            letter-spacing: 1px;
            font-size: .86em;
            color: rgba(0, 0, 0, 0.7);
            box-shadow: 0.5rem 0.5rem 0.5rem #BCC6D2, 
            -0.5rem -0.5rem 0.5rem rgba(244, 249, 255, 0.726);
        	border-radius: 3rem;
            background-color: rgb(204, 219, 238);
        }
        .footer-inner{
            width: 114em;
            height: 10em;
        }
        .logo-title{
            text-transform: uppercase;
            font-weight: bold;
            font-size: 1.2em;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <div class="public-footer">
        <p class=logo-title>Cookingking </p>
            <div class="footer-inner">
            <p class="first-line">
                <strong>상호명</strong> 
                    주식회사 Cookingking
                <strong>개인정보책임자</strong> 
                    김동현
                <strong>사업자등록번호</strong> 
                    111-11-11111
                <strong>통신판매신고번호</strong> 
                    2020-서울강남-0825
            </p>
            <p class="second-line">
                <strong>주소</strong> 
                    서울특별시 강남구 테헤란로 10길 9 그랑프리빌딩 5F
                <strong>대표번호</strong> 
                    0000-0000
                <strong>이메일</strong>
                    we_love_cookingking@ckk.com 
            </p>
            <p class="third-line">
                <strong>(주)Cookingking</strong>
                    은 통신판매중개자이며 통신판매 당사자가 아닙니다. 
                    따라서 클래스/개인간 거래 및 판매에 대하여 책임을 지지 않습니다.
            </p>        
            <p class="last-line">
            <strong>Copyright © Cookingking. All Rights Reserved.</strong>
            </p>
        </div>
    </div>
</body>
</html>