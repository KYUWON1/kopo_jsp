<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.type.UserType" %>
<%
    Member loginUser = (Member) session.getAttribute("loginUser");
    String userType = (loginUser != null) ? loginUser.getCdUserType() : null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>심플리원 쇼핑몰</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Noto Sans KR', sans-serif;
        }
        
        body {
            background-color: #f0f8f0;
            color: #333;
        }
        
        /* 헤더 스타일 */
        header {
            background-color: #2e7d32;
            color: white;
            padding: 15px 0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
        }
        
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }
        
        .logo {
            font-size: 24px;
            font-weight: 700;
            letter-spacing: 1px;
        }
        
        .logo a {
            color: white;
            text-decoration: none;
        }

        .logo span {
            font-size: 14px;
            background-color: #1b5e20;
            padding: 4px 8px;
            border-radius: 4px;
            margin-left: 8px;
            vertical-align: middle;
        }

        /* 네비게이션 스타일 */
        .navbar {
            display: flex;
            align-items: center;
        }
        
        .navbar a {
            color: white;
            text-align: center;
            padding: 10px 16px;
            text-decoration: none;
            font-weight: 500;
            border-radius: 4px;
            margin-left: 8px;
            transition: background-color 0.3s;
        }
        
        .navbar a:hover {
            background-color: #388e3c;
        }
        
        .welcome-msg {
            color: white;
            margin-left: 20px;
            font-weight: 500;
        }
        
        /* 콘텐츠 영역 */
        .content-container {
            max-width: 1200px;
            margin: 120px auto 40px;
            padding: 0 20px;
        }
        
        .main-content {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            border: 1px solid #e0e0e0;
        }
        
        .banner {
            background-color: #4caf50;
            color: white;
            text-align: center;
            padding: 60px 30px;
            border-radius: 8px;
            margin-bottom: 30px;
        }
        
        .banner h1 {
            font-size: 32px;
            margin-bottom: 15px;
        }
        
        .banner p {
            font-size: 18px;
            opacity: 0.9;
        }
        
        .featured-section {
            margin: 40px 0;
        }
        
        .featured-section h2 {
            color: #2e7d32;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #e0e0e0;
        }
        
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        
        .product-card {
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .product-img {
            height: 200px;
            background-color: #e8f5e9;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #4caf50;
            font-size: 16px;
        }
        
        .product-info {
            padding: 15px;
        }
        
        .product-title {
            font-weight: 600;
            margin-bottom: 8px;
        }
        
        .product-price {
            color: #4caf50;
            font-weight: 600;
        }
        
        /* 푸터 스타일 */
        footer {
            background-color: #2e7d32;
            color: white;
            padding: 30px 0;
            margin-top: 50px;
        }
        
        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        
        .footer-section {
            flex: 1;
            min-width: 200px;
            margin-bottom: 20px;
        }
        
        .footer-section h3 {
            margin-bottom: 15px;
            font-size: 18px;
        }
        
        .footer-section ul {
            list-style: none;
        }
        
        .footer-section ul li {
            margin-bottom: 8px;
        }
        
        .footer-section a {
            color: rgba(255,255,255,0.8);
            text-decoration: none;
        }
        
        .footer-section a:hover {
            color: white;
            text-decoration: underline;
        }
        
        .copyright {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid rgba(255,255,255,0.1);
            width: 100%;
        }
    </style>
</head>
<body>
<%
    String leaveResult = request.getParameter("leave");
    if ("success".equals(leaveResult)) {
%>
<script>
    alert("정상적으로 탈퇴 처리되었습니다.");
</script>
<%
    }
%>

<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
            <%
                System.out.println(userType);
                if (loginUser != null && userType.equals("_20")) {
            %>
                <span>관리자</span>
            <%
                }
            %>
        </div>
        <div class="navbar">
            <% if (loginUser == null) { %>
            <a href="member/signup.jsp">회원가입</a>
            <a href="member/login.jsp">로그인</a>
            <% } else if ("_20".equals(userType)) { %>
            <a href="admin/member-list?status=active">회원 관리</a>
            <a href="admin/member-list?status=apply">가입 승인</a>
            <a href="admin/member-list?status=withdraw">탈퇴 승인</a>
            <a href="member/logout.jsp">로그아웃</a>
            <% } else { %>
            <a href="/member/info_auth.jsp">개인정보</a>
            <a href="/member/leave.jsp">회원탈퇴</a>
            <a href="/member/logout.jsp">로그아웃</a>
            <% } %>
        </div>
    </div>
</header>

<!-- 콘텐츠 영역 -->
<div class="content-container">
    <div class="banner">
        <h1>심플리원 쇼핑몰에 오신 것을 환영합니다</h1>
        <p>Simple is One, 심규원이 만든 심플하고 특별한 쇼핑몰</p>
    </div>
    
    <div class="main-content">
        <div class="featured-section">
            <h2>추천 상품</h2>
            <div class="product-grid">
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">친환경 에코백</div>
                        <div class="product-price">19,800원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">자연주의 화장품 세트</div>
                        <div class="product-price">48,000원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">유기농 티셔츠</div>
                        <div class="product-price">32,000원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">재활용 노트북 파우치</div>
                        <div class="product-price">25,500원</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 푸터 영역 -->
<footer>
    <div class="footer-container">
        <div class="footer-section">
            <h3>심플리원 쇼핑몰</h3>
            <p>Simple is One, 심규원이 만든<br>심플하고 특별한 쇼핑몰</p>
        </div>
        <div class="footer-section">
            <h3>고객 서비스</h3>
            <ul>
                <li><a href="#">자주 묻는 질문</a></li>
                <li><a href="#">배송 정보</a></li>
                <li><a href="#">반품 정책</a></li>
                <li><a href="#">문의하기</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>회사 정보</h3>
            <ul>
                <li><a href="#">회사 소개</a></li>
                <li><a href="#">이용약관</a></li>
                <li><a href="#">개인정보처리방침</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>연락처</h3>
            <p>이메일: contact@simplyone.co.kr<br>
               전화: 02-123-4567<br>
               주소: 서울시 강남구 테헤란로 123</p>
        </div>
        <div class="copyright">
            &copy; 2023 심플리원 쇼핑몰 All Rights Reserved.
        </div>
    </div>
</footer>

</body>
</html>