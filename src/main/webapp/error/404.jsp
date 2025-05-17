<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>서버 오류</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            padding: 40px;
        }
        .error-box {
            background-color: white;
            border: 1px solid #ccc;
            padding: 30px;
            border-radius: 10px;
            width: 500px;
            margin: 0 auto;
            text-align: center;
        }
        .error-title {
            font-size: 24px;
            color: #d9534f;
            margin-bottom: 10px;
        }
        .error-message {
            color: #333;
        }
    </style>
</head>
<body>

<div class="error-box">
    <div class="error-title">404 - 잘못된 요청입니다.</div>
    <div class="error-message">
        <%
            String message = (String) request.getAttribute("errorMessage");
            if (message != null) {
        %>
        <p><%= message %></p>
        <% } else { %>
        <p>404 Error. 잘못된 요청입니다. 관리자에게 문의하세요.</p>
        <% } %>
    </div>
</div>

</body>
</html>
