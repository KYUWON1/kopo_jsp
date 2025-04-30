<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%
    List<Member> memberList = (List<Member>) request.getAttribute("memberList");

    // 로그인 체크 및 관리자 권한 확인
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
        response.sendRedirect("/member/login.jsp");
        return;
    }

    // 처리 결과 메시지
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 정보 관리 - 심플리원 관리자</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .card-title {
            font-size: 18px;
            color: #2e7d32;
            font-weight: 600;
        }

        .card-tools {
            display: flex;
            gap: 10px;
        }

        .search-box {
            display: flex;
            border: 1px solid #c8e6c9;
            border-radius: 4px;
            overflow: hidden;
        }

        .search-input {
            padding: 8px 12px;
            border: none;
            outline: none;
            width: 200px;
        }

        .search-btn {
            background-color: #4caf50;
            color: white;
            border: none;
            padding: 0 15px;
            cursor: pointer;
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
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
        }

        th {
            background-color: #4caf50;
            color: white;
            font-weight: 600;
            text-align: center;
            padding: 12px 15px;
            border: 1px solid #c8e6c9;
        }

        td {
            padding: 12px 15px;
            border: 1px solid #c8e6c9;
            text-align: center;
            vertical-align: middle;
        }

        tr:nth-child(even) {
            background-color: #f2f7f2;
        }

        tr:hover {
            background-color: #e8f5e9;
        }

        select {
            padding: 8px 10px;
            border: 1px solid #a5d6a7;
            border-radius: 4px;
            background-color: white;
            width: 100%;
            font-size: 14px;
            color: #333;
            cursor: pointer;
        }

        select:focus {
            outline: none;
            border-color: #4caf50;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }

        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            font-weight: 500;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.2s;
            width: 100%;
        }

        .btn-update {
            background-color: #4caf50;
            color: white;
        }

        .btn-update:hover {
            background-color: #388e3c;
        }

        .empty-state {
            padding: 40px 20px;
            text-align: center;
            color: #757575;
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
    <script>
        // 테이블 검색 기능
        function searchTable() {
            const input = document.getElementById("searchInput").value.toUpperCase();
            const table = document.getElementById("membersTable");
            const rows = table.getElementsByTagName("tr");

            for (let i = 1; i < rows.length; i++) {
                let found = false;
                const cells = rows[i].getElementsByTagName("td");

                for (let j = 0; j < cells.length - 2; j++) { // 마지막 두 열(선택박스, 버튼) 제외
                    const cellText = cells[j].textContent || cells[j].innerText;
                    if (cellText.toUpperCase().indexOf(input) > -1) {
                        found = true;
                        break;
                    }
                }

                rows[i].style.display = found ? "" : "none";
            }
        }

        function confirmChange(idUser, userName) {
            return confirm(`${userName} 회원의 정보를 변경하시겠습니까?`);
        }
    </script>
</head>
<body>

<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
            <span>관리자</span>
        </div>
        <div class="navbar">
            <a href="/admin/member-list?status=active">회원 관리</a>
            <a href="/admin/member-list?status=apply">가입 승인</a>
            <a href="/admin/member-list?status=withdraw">탈퇴 승인</a>
            <a href="/member/logout.jsp">로그아웃</a>
        </div>
    </div>
</header>

<!-- 메인 컨텐츠 영역 -->
<div class="content-container">
    <h1 class="page-title">회원 정보 관리</h1>

    <% if (message != null) { %>
    <div class="alert <%= message.contains("오류") ? "alert-danger" : "alert-success" %>">
        <%= message %>
    </div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <h2 class="card-title">회원 목록</h2>
            <div class="card-tools">
                <div class="search-box">
                    <input type="text" id="searchInput" class="search-input" placeholder="회원 검색..." onkeyup="searchTable()">
                    <button class="search-btn" onclick="searchTable()">검색</button>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div class="table-container">
                <form action="/admin/member-update" method="post">
                    <table id="membersTable">
                        <thead>
                        <tr>
                            <th>회원 ID</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>전화번호</th>
                            <th>상태</th>
                            <th>권한</th>
                            <th>가입일</th>
                            <th>변경</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (memberList != null && !memberList.isEmpty()) {
                                for (Member member : memberList) {
                        %>
                        <tr>
                            <td><%= member.getIdUser() %></td>
                            <td><%= member.getNmUser() %></td>
                            <td><%= member.getNmEmail() %></td>
                            <td><%= member.getNoMobile() %></td>

                            <!-- 상태 선택 -->
                            <td>
                                <select name="status_<%= member.getIdUser() %>" class="form-select">
                                    <option value="ST01" <%= "ST01".equals(member.getStStatus()) ? "selected" : "" %>>활성</option>
                                    <option value="ST03" <%= "ST03".equals(member.getStStatus()) ? "selected" : "" %>>정지</option>
                                </select>
                            </td>

                            <!-- 권한 선택 -->
                            <td>
                                <select name="role_<%= member.getIdUser() %>" class="form-select">
                                    <option value="_10" <%= "_10".equals(member.getCdUserType()) ? "selected" : "" %>>사용자</option>
                                    <option value="_20" <%= "_20".equals(member.getCdUserType()) ? "selected" : "" %>>관리자</option>
                                </select>
                            </td>

                            <td><%= member.getDaFirstDate() %></td>

                            <!-- 변경 버튼 (회원 ID 포함) -->
                            <td>
                                <button type="submit" name="targetId" value="<%= member.getIdUser() %>"
                                        class="btn btn-update"
                                        onclick="return confirmChange('<%= member.getIdUser() %>', '<%= member.getNmUser() %>')">
                                    변경
                                </button>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="8" class="empty-state">
                                <p class="empty-text">회원 정보가 존재하지 않습니다.</p>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 푸터 영역 -->
<footer>
    <div class="footer-container">
        <p class="footer-text">&copy; 2023 심플리원 쇼핑몰 - 관리자 시스템</p>
    </div>
</footer>

</body>
</html>