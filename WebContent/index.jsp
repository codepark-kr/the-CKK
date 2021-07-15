<%@ include file="/WEB-INF/views/common/userHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cookingking</title>
    <script src="<%= request.getContextPath() %>/js/jquery-3.5.1.js"></script>
<style>

    @font-face {
        font-family: 'IBMPlexSansKR-Text';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Text.woff') format('woff');
        font-weight: normal;
        font-style: normal;
        }
        
    .index-wrapper{
    	position: relative;
        width: 98em;
        height: 42em;
        background-color: #f6faff;  
/*         border: 1px solid turquoise;  */
        text-align: center;
        z-index: -999;
        border: 2px solid red;
        }
    .flipping-section{
        height: 4em;
        position: absolute;
        letter-spacing: 1px;
        margin-top: 6vh;
        border: 1px solid yellowgreen;
        width: 32.6em;
        font-size: 3em;
        color: #3B4654;  
        text-align: center;
        vertical-align: center;
        text-transform: uppercase;
        font-weight: bold;
    }
    #flip {
        text-align: center;
/*         border: 1px solid violet; */
        height: 13vh;
        overflow: hidden;
    }

    #flip > div > div {
        padding: 4px 12px;
        height: 45px;
        margin-bottom: 45px;
        display:inline-block;
        }

    #flip div:first-child {
        animation: show 13s linear infinite;
    }
    @keyframes show{
        0% {margin-top:-450px;}
        2% {margin-top:-360px;}
        20% {margin-top:-360px;}
        22% {margin-top:-270px;}
        40% {margin-top:-270px;}
        42% {margin-top:-180px;}
        60% {margin-top:-180px;}
        62% {margin-top:-90px;}
        80% {margin-top:-90px;}
        82% {margin-top:0px;}
        98%{margin-top:0px;}
        100% {margin-top:-450px;}
    }
     .logo-title{
            text-transform: uppercase;
            font-weight: bold;
            font-size: 1.2em;
        }
        .cat-images{
        margin-top: 1em;
		margin-left: 7em;
        }
        .cat-i{
/*             border: 1px solid yellow; */
            position: relative;
            position: inline-block;
            width: 100%;
            height: 100%;
            border-radius: 12px 12px 12px 12px;
            z-index: 998;
        }
        /* [class^="c-info"]{
            width: 15vw;
            height: 10vh;
            border-radius: 0px 0px 12px 12px;
            color: white;
            border: 1px solid cyan; 
        } */
        .cat-info > div > p{
            text-transform: uppercase;
            font-weight: bold;
            font-size: 1.2em;
            color: black;
            letter-spacing: 1px;
        }
        [class^="ci"]{
            border: 1px solid black;
            width: 19.3em;
            height: 19.3em;
            float: left;
            margin-right: 2vw;
            margin-top: 280px;
            border-radius: 12px 12px 12px 12px;
        }
        [class^="ci"] > a {
            font-family: 'Inter var', sans-serif;
            letter-spacing: 1px;
            font-size: 1em;
            text-transform: uppercase;
            font-weight: bold;
        }
        [class^="lt-btn"]{
            top: 1em;
            left: -5.6em;
            position: relative;
            display: inline-block;
            border-radius: 50%;
            width: 12%;
            height: 10%;
            border: none;
            outline: none;
            box-shadow:
            -7px -7px 20px 2px rgba(158, 157, 157, 0.6),
            -4px -4px 10px 0px rgba(161, 161, 161, 0.4),
            7px 7px 20px 0px rgba(0, 0, 0, 0.133),
            4px 4px 10px 0px #0001,
            inset 0px 0px 4px 0px rgba(255, 255, 255, 0.4),
            inset 0px 0px 4px 0px rgba(0, 26, 255, 0.067),
            inset 0px 0px 4px 0px rgba(255, 255, 255, 0.4),        
            inset 0px 0px 4px 0px rgba(38, 0, 255, 0.067);
            transition: box-shadow 0.6s cubic-bezier(.79,.21,.06,.81);
            color:rgba(0, 0, 0, 0.6);
            text-transform: uppercase;
            font-weight: bold;
            background: #f6faff; 
            z-index: 999;
        }
</style>
<body>
    <div class="index-wrapper">
        <div class="flipping-section">
            <div id="flip">
                <!-- 캐치프레이즈 결정되고 나면 각 이름들은 대체할 것. -->
                <div><div>박예빈 박예빈 박예빈 박예빈</div></div>
                <div><div>박준혁 박준혁 박준혁 박준혁</div></div>
                <div><div>이호근 이호근 이호근 이호근</div></div>
                <div><div>김종완 김종완 김종완 김종완</div></div>
                <div><div>김가영 김가영 김가영 김가영</div></div>
            </div>
            <div class="fixed">
                cooking-king
            </div>
            </div>
            <div class="cat-images">
                <div class="ci1">
                    <!-- <input type="button" class="lt-btn1" value="&lt;"></input> -->
                    <a href=""><img src="" alt="" class="cat-i" id="cat-img1"></a>
                </div>
                <div class="ci2">
                    <!-- <input type="button" class="lt-btn2" value="&lt;"></input> -->
                    <a href=""><img src="" alt="" class="cat-i" id="cat-img2"></a>
                </div>
                <div class="ci3">
                    <!-- <input type="button" class="lt-btn3" value="&lt;"></input> -->
                    <a href=""><img src="" alt="" class="cat-i" id="cat-img3"></a>
                </div>
                <div class="ci4">
                    <!-- <input type="button" class="lt-btn4" value="&lt;"></input> -->
                    <a href=""><img src="" alt="" class="cat-i" id="cat-img4"></a>
                </div>
            </div>
            </div>
</body>
</html>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>