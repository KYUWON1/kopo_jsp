<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - 심플리원</title>
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
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        
        /* 헤더 스타일 */
        header {
            background-color: #2e7d32;
            color: white;
            padding: 15px 0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
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
        
        /* 회원가입 폼 스타일 */
        .signup-container {
            width: 500px;
            margin: 50px auto;
            background: white;
            padding: 35px 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            border: 1px solid #e0e0e0;
        }
        
        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
            font-size: 28px;
            font-weight: 600;
        }
        
        .error {
            color: #e53935;
            background-color: #ffebee;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 14px;
        }
        
        .form-row {
            display: flex;
            margin-bottom: 20px;
        }
        
        .form-label {
            flex: 1;
            padding: 12px 10px 12px 0;
            text-align: right;
            font-weight: 500;
            color: #555;
        }
        
        .form-input-container {
            flex: 2;
            padding-left: 10px;
        }
        
        .form-input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #c8e6c9;
            border-radius: 6px;
            font-size: 15px;
            transition: border-color 0.3s;
        }
        
        .form-input:focus {
            border-color: #4caf50;
            outline: none;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }
        
        .hint {
            font-size: 12px;
            color: #757575;
            margin-top: 5px;
        }
        
        .btn-signup {
            width: 100%;
            padding: 14px;
            background-color: #4caf50;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 10px;
        }
        
        .btn-signup:hover {
            background-color: #388e3c;
        }
        
        .links {
            text-align: center;
            margin-top: 25px;
            font-size: 14px;
        }
        
        .links a {
            color: #4caf50;
            text-decoration: none;
            margin: 0 10px;
        }
        
        .links a:hover {
            text-decoration: underline;
        }
        
        /* 푸터 스타일 */
        footer {
            background-color: #2e7d32;
            color: white;
            padding: 20px 0;
            margin-top: auto;
        }
        
        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            text-align: center;
        }
        
        .footer-text {
            font-size: 14px;
            opacity: 0.8;
        }
    </style>
</head>
<body>

<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
        </div>
    </div>
</header>

<!-- 회원가입 폼 -->
<div class="signup-container">
    <h2>회원가입</h2>
    
    <% if (error != null && !error.isEmpty()) { %>
    <div class="error"><%= error %></div>
    <% } %>
    
    <form action="/member/join" method="post">
        <div class="form-row">
            <div class="form-label">이메일</div>
            <div class="form-input-container">
                <input type="email" name="email" class="form-input" 
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" 
                       required>
                <div class="hint">이메일 주소를 입력하세요</div>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">이름</div>
            <div class="form-input-container">
                <input type="text" name="userName" class="form-input" required>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">비밀번호</div>
            <div class="form-input-container">
                <input type="password" name="password" class="form-input" 
                       pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{5,15}" 
                       required>
                <div class="hint">대소문자와 숫자를 포함한 5~15자</div>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">휴대전화</div>
            <div class="form-input-container">
                <input type="text" name="phoneNumber" class="form-input" 
                       pattern="\d{10,11}" 
                       required>
                <div class="hint">10~11자리 숫자만 입력</div>
            </div>
        </div>
        
        <button type="submit" class="btn-signup">회원가입</button>
        
        <div class="links">
            <a href="/member/login.jsp">로그인</a>
            <a href="/">메인으로</a>
        </div>
    </form>
</div>

<!-- 푸터 영역 -->
<footer>
    <div class="footer-container">
        <p class="footer-text">&copy; 2023 심플리원 쇼핑몰 All Rights Reserved.</p>
    </div>
</footer>

</body>
</html>