<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.type.UserStatus" %>
<%
    // 로그인 체크 및 관리자 권한 확인
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
        response.sendRedirect("/member/login.jsp");
        return;
    }

    // 가입 신청 회원 목록 조회는 서블릿에서 처리한다고 가정
    List<Member> applyMembers = (List<Member>) request.getAttribute("memberList");

    // 승인/거절 결과 메시지
    String message = (String) request.getAttribute("message");
    
    // 현재 상태(가입 신청 or 탈퇴 신청)
    String status = request.getParameter("status");
    boolean isWithdraw = "withdraw".equals(status);
    
    // 페이지 타이틀 및 버튼 텍스트 설정
    String pageTitle = isWithdraw ? "회원 탈퇴 신청 관리" : "회원 가입 신청 관리";
    String cardTitle = isWithdraw ? "탈퇴 대기 회원 목록" : "가입 대기 회원 목록";
    String statusBadgeText = isWithdraw ? "탈퇴대기" : "승인대기";
    String buttonText = isWithdraw ? "탈퇴 승인" : "가입 승인";
    String buttonClass = isWithdraw ? "btn-reject" : "btn-approve";
    String actionValue = isWithdraw ? "withdraw" : "approve";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= pageTitle %> - 심플리원 관리자</title>
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

        .logo span {
            font-size: 14px;
            background-color: #1b5e20;
            padding: 4px 8px;
            border-radius: 4px;
            margin-left: 8px;
            vertical-align: middle;
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

        .welcome-msg {
            color: white;
            margin-left: 20px;
            font-weight: 500;
        }

        /* 메인 컨텐츠 영역 */
        .content-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
            flex-grow: 1;
        }

        .page-title {
            font-size: 24px;
            color: #2e7d32;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #c8e6c9;
        }

        .alert {
            padding: 12px 15px;
            margin-bottom: 20px;
            border-radius: 6px;
            font-weight: 500;
        }

        .alert-success {
            background-color: #e8f5e9;
            border-left: 4px solid #4caf50;
            color: #2e7d32;
        }

        .alert-danger {
            background-color: #ffebee;
            border-left: 4px solid #f44336;
            color: #d32f2f;
        }

        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            overflow: hidden;
            margin-bottom: 30px;
        }

        .card-header {
            background-color: #e8f5e9;
            padding: 15px 20px;
            border-bottom: 1px solid #c8e6c9;
        }

        .card-title {
            font-size: 18px;
            color: #2e7d32;
            font-weight: 600;
        }

        .card-body {
            padding: 20px;
        }

        /* 테이블 스타일 */
        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #f5f5f5;
            color: #555;
            font-weight: 600;
            text-align: left;
            padding: 12px 15px;
            border-bottom: 2px solid #e0e0e0;
        }

        td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
            vertical-align: middle;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-align: center;
        }

        .status-pending {
            background-color: #fff8e1;
            color: #ff8f00;
        }
        
        .status-withdraw {
            background-color: #ffebee;
            color: #d32f2f;
        }

        .btn-group {
            display: flex;
            gap: 8px;
        }

        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            font-weight: 500;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.2s;
        }

        .btn-approve {
            background-color: #4caf50;
            color: white;
        }

        .btn-approve:hover {
            background-color: #388e3c;
        }

        .btn-reject {
            background-color: #f44336;
            color: white;
        }

        .btn-reject:hover {
            background-color: #d32f2f;
        }

        .empty-state {
            padding: 40px 20px;
            text-align: center;
            color: #757575;
        }

        .empty-icon {
            font-size: 48px;
            margin-bottom: 15px;
            color: #e0e0e0;
        }

        .empty-text {
            font-size: 16px;
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

<!-- 메인 컨텐츠 영역 -->
<div class="content-container">
    <h1 class="page-title"><%= pageTitle %></h1>

    <% if (message != null) { %>
    <div class="alert <%= message.contains("오류") ? "alert-danger" : "alert-success" %>">
        <%= message %>
    </div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <h2 class="card-title"><%= cardTitle %></h2>
        </div>
        <div class="card-body">
            <% if (applyMembers != null && !applyMembers.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>휴대전화</th>
                        <th>가입일</th>
                        <th>상태</th>
                        <th>작업</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Member member : applyMembers) { %>
                    <tr>
                        <td><%= member.getIdUser() %></td>
                        <td><%= member.getNmUser() %></td>
                        <td><%= member.getNmEmail() %></td>
                        <td><%= member.getNoMobile() %></td>
                        <td><%= member.getDaFirstDate() == null ? "미등록" : member.getDaFirstDate() %></td>
                        <td>
                            <span class="status-badge <%= isWithdraw ? "status-withdraw" : "status-pending" %>">
                                <%= statusBadgeText %>
                            </span>
                        </td>
                        <td>
                            <div class="btn-group">
                                <!-- 단일 승인 버튼 -->
                                <form action="/admin/member-approval" method="POST" style="display:inline;">
                                    <input type="hidden" name="idUser" value="<%= member.getIdUser() %>" />
                                    <input type="hidden" name="action" value="<%= actionValue %>" />
                                    <button type="submit" class="btn <%= buttonClass %>"><%= buttonText %></button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <% } else { %>
            <div class="empty-state">
                <div class="empty-icon">📭</div>
                <p class="empty-text">현재 <%= isWithdraw ? "탈퇴" : "승인" %> 대기 중인 회원이 없습니다.</p>
            </div>
            <% } %>
        </div>
    </div>
</div>

<%@ include file="/common/simple_footer.jsp" %>