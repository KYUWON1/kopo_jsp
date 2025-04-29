<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 28.
  Time: 오후 8:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="login" method="post">
    <table border="1" cellpadding="5">
        <tr>
            <td align="right">사용자 ID (이메일)</td>
            <td><input type="email" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" required></td>
        </tr>
        <tr>
            <td align="right">비밀번호</td>
            <td><input type="password" name="password"
                       pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{5,15}"
                       title="대소문자와 숫자를 포함한 5~15자" required></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="회원가입">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
