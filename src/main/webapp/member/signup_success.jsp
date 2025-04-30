<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = (String) request.getAttribute("userName");
    if (name == null) name = "회원";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입 완료</title>
    <script>
        alert("<%= name %>님, 회원가입 신청이 완료되었습니다! ");
        window.location.href = "<%= request.getContextPath() %>/index.jsp";
    </script>
</head>
<body>
</body>
</html>
