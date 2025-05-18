<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.domain.member.model.Member" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>
<%
  // 로그인 체크
  Member loginUser = (Member) request.getSession().getAttribute("loginUser");

  // 에러 메시지 처리
  String error = (String) request.getAttribute("error");
  if (error != null && !error.isEmpty()) {
    error = URLDecoder.decode(error, "UTF-8");
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>본인 확인 - 심플리원</title>
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

    /* 메인 컨텐츠 */
    .auth-container {
      width: 450px;
      margin: 60px auto;
      background: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }

    .auth-title {
      font-size: 24px;
      color: #2e7d32;
      margin-bottom: 20px;
      text-align: center;
      padding-bottom: 15px;
      border-bottom: 2px solid #e8f5e9;
    }

    .auth-message {
      background-color: #e8f5e9;
      padding: 15px;
      border-radius: 6px;
      margin-bottom: 20px;
      color: #333;
      font-size: 14px;
      line-height: 1.5;
      text-align: center;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 8px;
      font-weight: 500;
      color: #444;
    }

    .form-control {
      width: 100%;
      padding: 12px 15px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 15px;
      transition: border-color 0.3s, box-shadow 0.3s;
    }

    .form-control:focus {
      border-color: #4caf50;
      outline: none;
      box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
    }

    .btn {
      display: block;
      width: 100%;
      padding: 13px;
      background-color: #4caf50;
      color: white;
      border: none;
      border-radius: 14px;
      font-size: 16px;
      font-weight: 500;
      cursor: pointer;
      text-align: center;
      transition: background-color 0.3s;
    }

    .btn:hover {
      background-color: #388e3c;
    }

    .error-message {
      color: #d32f2f;
      font-size: 14px;
      margin-top: 15px;
      text-align: center;
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

<%@ include file="/common/header.jsp" %>

<!-- 본인 확인 폼 -->
<div class="auth-container">
  <h2 class="auth-title">본인 확인</h2>

  <div class="auth-message">
    개인정보 보호를 위해 비밀번호를 한번 더 입력해주세요.
  </div>

  <form action="memberInfoAuth.do" method="POST">
    <input type="hidden" name="idUser" value="<%= loginUser.getIdUser() %>">

    <div class="form-group">
      <label for="password">비밀번호</label>
      <input type="password" id="password" name="password" class="form-control" required>
    </div>

    <button type="submit" class="btn">확인</button>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="error-message"><%= error %></div>
    <% } %>
  </form>
</div>

<%@ include file="/common/simple_footer.jsp" %>