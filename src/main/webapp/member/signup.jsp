<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            margin: 40px auto;
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
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        
        .form-input:focus {
            border-color: #4caf50;
            outline: none;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }
        
        .form-row {
            display: flex;
            gap: 15px;
        }
        
        .form-col {
            flex: 1;
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
        
        /* 비밀번호 확인 피드백 스타일 */
        .password-feedback {
            font-size: 13px;
            margin-top: 5px;
            display: none;
        }
        
        .valid-feedback {
            color: #4caf50;
            display: block;
        }
        
        .invalid-feedback {
            color: #f44336;
            display: block;
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

<!-- 회원가입 폼 -->
<div class="signup-container">
    <h2>회원가입</h2>
    
    <form action="/member/join" method="post" id="signupForm">
        <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" class="form-input" required>
        </div>
        
        <div class="form-group">
            <label for="username">이름</label>
            <input type="text" id="username" name="username" class="form-input" required>
        </div>
        
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" class="form-input" 
                   required minlength="4" onkeyup="checkPasswordMatch()">
        </div>
        
        <div class="form-group">
            <label for="confirmPassword">비밀번호 확인</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" 
                   required minlength="4" onkeyup="checkPasswordMatch()">
            <div id="passwordFeedback" class="password-feedback">비밀번호를 입력해주세요.</div>
        </div>
        
        <div class="form-group">
            <label for="phoneNumber">휴대전화</label>
            <input type="text" id="phoneNumber" name="phoneNumber" class="form-input" 
                   placeholder="예: 010-1234-5678" required>
        </div>
        
        <button type="submit" class="btn-signup" id="submitBtn">가입하기</button>
        
        <div class="links">
            <a href="/member/login.jsp">로그인</a>
            <a href="/">메인으로</a>
        </div>
    </form>
</div>

<script>
    // 비밀번호 일치 여부 확인 함수
    function checkPasswordMatch() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        var feedback = document.getElementById("passwordFeedback");
        var submitBtn = document.getElementById("submitBtn");
        
        // 피드백 요소 표시
        feedback.style.display = "block";
        
        // 비밀번호 확인 필드가 비어있으면 메시지 숨김
        if (confirmPassword === "") {
            feedback.textContent = "비밀번호를 입력해주세요.";
            feedback.className = "password-feedback";
            return;
        }
        
        // 비밀번호 일치 여부 확인
        if (password === confirmPassword) {
            feedback.textContent = "비밀번호가 일치합니다.";
            feedback.className = "password-feedback valid-feedback";
            submitBtn.disabled = false;
        } else {
            feedback.textContent = "비밀번호가 일치하지 않습니다.";
            feedback.className = "password-feedback invalid-feedback";
            submitBtn.disabled = true;
        }
    }
    
    // 폼 제출 전 유효성 검사
    document.getElementById("signupForm").addEventListener("submit", function(event) {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        
        if (password !== confirmPassword) {
            event.preventDefault();
            alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }
    });
    
    // 페이지 로드 시 비밀번호 확인 상태 초기화
    window.onload = function() {
        var feedback = document.getElementById("passwordFeedback");
        feedback.style.display = "none";
        
        // 비밀번호 입력 필드에 포커스 이벤트 추가
        document.getElementById("confirmPassword").addEventListener("focus", function() {
            if (feedback.style.display === "none") {
                feedback.style.display = "block";
            }
        });
    };
</script>

<%@ include file="/common/simple_footer.jsp" %>