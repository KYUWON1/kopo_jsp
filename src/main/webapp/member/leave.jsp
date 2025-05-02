<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.type.ErrorType" %>
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
    <title>회원 탈퇴 - 심플리원</title>
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

        /* 탈퇴 폼 컨테이너 스타일 */
        .leave-container {
            width: 500px;
            margin: 60px auto;
            background: white;
            padding: 35px 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            border: 1px solid #e0e0e0;
        }

        .error {
            color: #e53935;
            background-color: #ffebee;
            padding: 10px;
            border-radius: 5px;
            margin-top: 10px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 14px;
        }

        h2 {
            text-align: center;
            color: #d32f2f;
            margin-bottom: 25px;
            font-size: 26px;
            font-weight: 600;
            padding-bottom: 15px;
            border-bottom: 2px solid #ffebee;
        }

        .warning-box {
            background-color: #ffebee;
            border-left: 4px solid #d32f2f;
            padding: 15px;
            margin-bottom: 25px;
            border-radius: 4px;
        }

        .warning-title {
            color: #d32f2f;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 16px;
        }

        .warning-text {
            color: #555;
            font-size: 14px;
            line-height: 1.5;
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
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 15px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .form-input:focus {
            border-color: #d32f2f;
            outline: none;
            box-shadow: 0 0 0 2px rgba(211, 47, 47, 0.2);
        }

        .checkbox-container {
            margin: 20px 0;
            display: flex;
            align-items: center;
        }

        .checkbox-container input[type="checkbox"] {
            margin-right: 10px;
            transform: scale(1.2);
        }

        .checkbox-label {
            font-size: 15px;
            color: #555;
            font-weight: 500;
        }

        .buttons {
            display: flex;
            gap: 15px;
            margin-top: 25px;
        }

        .btn-cancel {
            flex: 1;
            padding: 14px;
            background-color: #e0e0e0;
            color: #555;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-cancel:hover {
            background-color: #d5d5d5;
        }

        .btn-leave {
            flex: 1;
            padding: 14px;
            background-color: #d32f2f;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-leave:hover {
            background-color: #b71c1c;
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
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var checkbox = document.getElementById("confirm-leave").checked;

            if (!password) {
                alert("비밀번호를 입력해주세요.");
                return false;
            }

            if (!checkbox) {
                alert("탈퇴 동의 사항을 확인해주세요.");
                return false;
            }

            return confirm("정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.");
        }
    </script>
</head>
<body>

<!-- 헤더 영역 -->
<header>
    <div class="header-container">
        <div class="logo">
            <a href="/">심플리원</a>
        </div>
        <div class="navbar">
            <a href="/member/info_auth.jsp">개인정보</a>
            <a href="/member/leave.jsp">회원탈퇴</a>
            <a href="/member/logout.jsp">로그아웃</a>
        </div>
    </div>
</header>
<%
    String error = request.getParameter("error");
%>

<!-- 회원 탈퇴 폼 -->
<div class="leave-container">
    <h2>회원 탈퇴</h2>

    <div class="warning-box">
        <div class="warning-title">탈퇴 전 꼭 확인해주세요!</div>
        <div class="warning-text">
            <p>• 탈퇴 후에는 계정과 관련된 모든 데이터가 삭제되며 복구할 수 없습니다.</p>
            <p>• 진행 중인 주문이 있는 경우 주문 처리 후 탈퇴하시기 바랍니다.</p>
            <p>• 고객센터를 통해 도움을 받으실 수 있습니다.</p>
        </div>
    </div>

    <form method="post" action="/member/leave" onsubmit="return validateForm()">
        <input type="hidden" name="idUser" value="<%= loginUser.getIdUser() %>">
        <input type="hidden" name="password" value="<%= loginUser.getNmPaswd() %>">
        <div class="form-group">
            <label for="password">비밀번호 확인</label>
            <input type="password" id="password" name="passwordCheck" class="form-input"
                   placeholder="본인 확인을 위해 비밀번호를 입력해주세요">
        </div>

        <div class="checkbox-container">
            <input type="checkbox" id="confirm-leave" name="confirm-leave">
            <label class="checkbox-label" for="confirm-leave">
                탈퇴 시 모든 회원 정보가 삭제되며 복구할 수 없음을 이해했습니다.
            </label>
        </div>

        <div class="buttons">
            <button type="button" class="btn-cancel" onclick="location.href='/'">취소</button>
            <button type="submit" class="btn-leave">탈퇴하기</button>
        </div>
        <% if (error != null && !error.isEmpty()) {
            String msg = ErrorType.valueOf(error).getMessage();
        %>
            <div class="error"><%= msg %></div>
        <% } %>
        <div class="info-message">
            <a href="/member/info_auth.jsp">개인정보 관리로 돌아가기</a>
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