<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.type.UserType" %>
<%@ page import="java.time.LocalDate" %>
<%
    // 이미 페이지에서 loginUser가 정의되어 있다면 재정의하지 않음
    Member headerLoginUser = null;
    String userType = null;

    // 세션에서 사용자 정보 가져오기
    if(session.getAttribute("loginUser") != null) {
        headerLoginUser = (Member) session.getAttribute("loginUser");
        userType = headerLoginUser.getCdUserType();
    }

    String currentPath = request.getRequestURI();
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
        <% if (currentPath.equals("/index.jsp")) { %>
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
        <% } %>
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
    </style>
    <!-- 페이지별 추가 스타일은 헤더 포함 전에 정의 -->
</head>
<body>
<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
            <%
                if (headerLoginUser != null && "_20".equals(userType)) {
            %>
            <span>관리자</span>
            <%
                }
            %>
        </div>
        <div class="navbar">
            <% if (headerLoginUser == null) { %>
            <a href="/member/signup.jsp">회원가입</a>
            <a href="/member/login.jsp">로그인</a>
            <% } else if ("_20".equals(userType)) { %>
            <a href="/admin/category">카테고리 관리</a>
            <a href="/admin/product">상품 관리</a>
            <a href="/admin/member-list?status=active">회원 관리</a>
            <a href="/admin/member-list?status=apply">가입 승인</a>
            <a href="/admin/member-list?status=withdraw">탈퇴 승인</a>
            <a href="/member/logout.jsp">로그아웃</a>
            <% } else { %>
            <a href="/member/info_auth.jsp">개인정보</a>
            <a href="/member/leave.jsp">회원탈퇴</a>
            <a href="/member/logout.jsp">로그아웃</a>
            <% } %>
        </div>
    </div>
</header>