<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect("/member/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>개인정보 수정 - 심플리원</title>
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
        
        /* 폼 컨테이너 스타일 */
        .form-container {
            width: 500px;
            margin: 60px auto;
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
            font-size: 26px;
            font-weight: 600;
            padding-bottom: 15px;
            border-bottom: 2px solid #e8f5e9;
        }
        
        .form-row {
            display: flex;
            margin-bottom: 20px;
            align-items: center;
        }
        
        .form-label {
            flex: 1;
            text-align: right;
            padding-right: 15px;
            font-weight: 500;
            color: #555;
        }
        
        .form-input-container {
            flex: 2;
        }
        
        .form-input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #c8e6c9;
            border-radius: 6px;
            font-size: 15px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        
        .form-input:focus {
            border-color: #4caf50;
            outline: none;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }
        
        .form-input::placeholder {
            color: #aaa;
        }
        
        .btn-submit {
            display: block;
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
            margin-top: 20px;
        }
        
        .btn-submit:hover {
            background-color: #388e3c;
        }
        
        .info-message {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
            color: #757575;
        }
        
        .info-message a {
            color: #4caf50;
            text-decoration: none;
            font-weight: 500;
        }
        
        .info-message a:hover {
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
<%
    Boolean success = (Boolean) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>

<% if (success != null && success) { %>
    <script>alert("회원 정보가 정상적으로 수정되었습니다.");</script>
<% } else if (error != null) { %>
    <script>alert("<%= error %>");</script>
<% } %>

<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
        </div>
        <div class="navbar">
            <a href="/">홈으로</a>
            <a href="/member/leave.jsp">회원탈퇴</a>
            <a href="/member/logout.jsp">로그아웃</a>
        </div>
    </div>
</header>

<!-- 개인정보 수정 폼 -->
<div class="form-container">
    <h2>개인정보 수정</h2>

    <form method="post" action="/member/update">
        <input type="hidden" name="idUser" value="<%= loginUser.getIdUser() %>">
        <input type="hidden" name="bfEmail" value="<%= loginUser.getNmEmail() %>">
        
        <div class="form-row">
            <div class="form-label">이메일</div>
            <div class="form-input-container">
                <input type="email" name="email" value="<%= loginUser.getNmEmail() %>" class="form-input" required>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">이름</div>
            <div class="form-input-container">
                <input type="text" name="username" value="<%= loginUser.getNmUser() %>" class="form-input" required>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">비밀번호</div>
            <div class="form-input-container">
                <input type="password" name="password" placeholder="변경할 비밀번호 입력" class="form-input">
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-label">휴대전화</div>
            <div class="form-input-container">
                <input type="text" name="phoneNumber" value="<%= loginUser.getNoMobile() %>" class="form-input" required>
            </div>
        </div>
        
        <button class="btn-submit" type="submit">정보 수정</button>
        
        <div class="info-message">
            <a href="/">메인으로 돌아가기</a>
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