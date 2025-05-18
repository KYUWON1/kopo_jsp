<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.domain.member.model.Member" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>
<%
    // 로그인 체크
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    // 공통 메시지 처리
    String message = (String) request.getAttribute("message");
    String type = (String) request.getAttribute("type"); // "success" 또는 "error"
    String passwordError = (String) request.getAttribute("passwordError");
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

        /* 알림 메시지 스타일 */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: 500;
        }

        .alert-success {
            background-color: #e8f5e9;
            border-left: 4px solid #4caf50;
            color: #2e7d32;
        }

        .alert-error {
            background-color: #ffebee;
            border-left: 4px solid #f44336;
            color: #d32f2f;
        }

        /* 모달 스타일 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 30px;
            width: 450px;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            position: relative;
        }

        .modal-title {
            color: #2e7d32;
            font-size: 22px;
            margin-bottom: 20px;
            text-align: center;
            padding-bottom: 15px;
            border-bottom: 2px solid #e8f5e9;
        }

        .modal-close {
            position: absolute;
            top: 15px;
            right: 20px;
            font-size: 24px;
            color: #888;
            cursor: pointer;
            transition: color 0.3s;
        }

        .modal-close:hover {
            color: #555;
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

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #c8e6c9;
            border-radius: 6px;
            font-size: 15px;
        }

        .form-control:focus {
            border-color: #4caf50;
            outline: none;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }

        .password-feedback {
            font-size: 13px;
            margin-top: 5px;
        }

        .valid-feedback {
            color: #4caf50;
        }

        .invalid-feedback {
            color: #f44336;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            text-align: center;
        }

        .btn-primary {
            background-color: #4caf50;
            color: white;
        }

        .btn-primary:hover {
            background-color: #388e3c;
        }

        .btn-secondary {
            background-color: #e0e0e0;
            color: #333;
        }

        .btn-secondary:hover {
            background-color: #d5d5d5;
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

<!-- 개인정보 수정 폼 -->
<div class="form-container">
    <h2>개인정보 수정</h2>

    <% if (message != null && !message.isEmpty()) { %>
    <div class="alert <%= "success".equals(type) ? "alert-success" : "alert-error" %>">
        <%= message %>
    </div>
    <% } %>

    <% if (passwordError != null && !passwordError.isEmpty()) { %>
    <div class="alert alert-error"><%= passwordError %></div>
    <% } %>

    <form method="post" action="memberInfoUpdate.do" id="updateForm">
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
            <div class="form-label">휴대전화</div>
            <div class="form-input-container">
                <input type="text" name="phoneNumber" value="<%= loginUser.getNoMobile() %>" class="form-input" required>
            </div>
        </div>

        <div class="form-row">
            <div class="form-label">비밀번호</div>
            <div class="form-input-container">
                <button type="button" id="changePasswordBtn" class="btn-submit" style="margin-top: 0;">비밀번호 변경</button>
            </div>
        </div>

        <button class="btn-submit" type="submit">정보 수정</button>

        <div class="info-message">
            <a href="/">메인으로 돌아가기</a>
        </div>
    </form>
</div>

<!-- 비밀번호 변경 모달 -->
<div id="passwordModal" class="modal">
    <div class="modal-content">
        <span class="modal-close">&times;</span>
        <h3 class="modal-title">비밀번호 변경</h3>

        <form action="memberPasswordUpdate.do" method="POST" id="passwordForm">
            <input type="hidden" name="idUser" value="<%= loginUser.getIdUser() %>">
            <input type="hidden" id="realCurrentPassword" value="<%= loginUser.getNmPaswd() %>">

            <div class="form-group">
                <label for="currentPassword">현재 비밀번호</label>
                <input type="password" id="currentPassword" name="currentPassword" class="form-control"
                       required onkeyup="checkCurrentPassword()">

            </div>

            <div class="form-group">
                <label for="newPassword">새 비밀번호</label>
                <input type="password" id="newPassword" name="newPassword" class="form-control"
                       required minlength="4" onkeyup="checkPasswordMatch()">
            </div>

            <div class="form-group">
                <label for="confirmPassword">새 비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
                       required minlength="4" onkeyup="checkPasswordMatch()">
                <div id="passwordFeedback" class="password-feedback"></div>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-secondary" id="cancelPasswordBtn">취소</button>
                <button type="submit" class="btn btn-primary" id="savePasswordBtn">변경하기</button>
            </div>
        </form>
    </div>
</div>

<script>
    // 모달 관련 스크립트
    var modal = document.getElementById("passwordModal");
    var btn = document.getElementById("changePasswordBtn");
    var closeBtn = document.getElementsByClassName("modal-close")[0];
    var cancelBtn = document.getElementById("cancelPasswordBtn");
    var passwordForm = document.getElementById("passwordForm");
    var savePasswordBtn = document.getElementById("savePasswordBtn");

    // 비밀번호 변경 필드
    var newPassword = document.getElementById("newPassword");
    var confirmPassword = document.getElementById("confirmPassword");
    var passwordFeedback = document.getElementById("passwordFeedback");

    // 모달 열기
    btn.onclick = function() {
        modal.style.display = "block";
        resetPasswordForm();
    }

    // 모달 닫기
    closeBtn.onclick = function() {
        modal.style.display = "none";
    }

    cancelBtn.onclick = function() {
        modal.style.display = "none";
    }

    // 모달 외부 클릭 시 닫기
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    // 비밀번호 일치 여부 실시간 확인
    function checkPasswordMatch() {
        var newPasswordValue = newPassword.value;
        var confirmPasswordValue = confirmPassword.value;

        // 정규식: 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 길이 5~15자
        var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{5,15}$/;

        if (!passwordPattern.test(newPasswordValue)) {
            passwordFeedback.innerHTML = "비밀번호는 대문자, 소문자, 숫자를 포함한 5~15자여야 합니다.";
            passwordFeedback.className = "password-feedback invalid-feedback";
            savePasswordBtn.disabled = true;
            return;
        }

        if (confirmPasswordValue === "") {
            passwordFeedback.innerHTML = "";
            passwordFeedback.className = "password-feedback";
            savePasswordBtn.disabled = true;
            return;
        }

        if (newPasswordValue === confirmPasswordValue) {
            passwordFeedback.innerHTML = "비밀번호가 일치합니다.";
            passwordFeedback.className = "password-feedback valid-feedback";
            savePasswordBtn.disabled = false;
        } else {
            passwordFeedback.innerHTML = "비밀번호가 일치하지 않습니다.";
            passwordFeedback.className = "password-feedback invalid-feedback";
            savePasswordBtn.disabled = true;
        }
    }

    // 초기 상태에서는 저장 버튼 비활성화
    savePasswordBtn.disabled = true;

    // 비밀번호 변경 폼 제출 전 유효성 검사
    passwordForm.onsubmit = function(e) {
        var currentPasswordValue = document.getElementById("currentPassword").value;
        var newPasswordValue = newPassword.value;
        var confirmPasswordValue = confirmPassword.value;

        var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{5,15}$/;

        if (currentPasswordValue === "") {
            e.preventDefault();
            alert("현재 비밀번호를 입력해주세요.");
            return false;
        }

        if (!passwordPattern.test(newPasswordValue)) {
            e.preventDefault();
            alert("새 비밀번호는 대문자, 소문자, 숫자를 포함한 5~15자여야 합니다.");
            return false;
        }

        if (newPasswordValue !== confirmPasswordValue) {
            e.preventDefault();
            alert("새 비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    // 비밀번호 변경 폼 초기화
    function resetPasswordForm() {
        document.getElementById("currentPassword").value = "";
        document.getElementById("newPassword").value = "";
        document.getElementById("confirmPassword").value = "";
        passwordFeedback.innerHTML = "";
        passwordFeedback.className = "password-feedback";
        savePasswordBtn.disabled = true;
    }

    // 비밀번호 일치 여부 이벤트 리스너 추가
    newPassword.addEventListener("keyup", checkPasswordMatch);
    confirmPassword.addEventListener("keyup", checkPasswordMatch);
</script>

<%@ include file="/common/simple_footer.jsp" %>