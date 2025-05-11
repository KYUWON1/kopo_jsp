<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
%>
<style>
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    /* 로그인 폼 스타일 */
    .login-container {
        width: 400px;
        margin: 80px auto;
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

    .form-group {
        margin-bottom: 20px;
    }

    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
        color: #555;
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

    .btn-login {
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

    .btn-login:hover {
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
</style>

<%@ include file="/common/header.jsp" %>

<!-- 로그인 폼 -->
<div class="login-container">
    <h2>로그인</h2>

    <% if (error != null && !error.isEmpty()) { %>
    <div class="error"><%= error %></div>
    <% } %>

    <form action="login.do" method="post">
        <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" class="form-input" required>
        </div>

        <button type="submit" class="btn-login">로그인</button>

        <div class="links">
            <a href="/member/signup.jsp">회원가입</a>
            <a href="/">메인으로</a>
        </div>
    </form>
</div>

<%@ include file="/common/simple_footer.jsp" %>