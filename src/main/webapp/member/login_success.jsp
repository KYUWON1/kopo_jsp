<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 30.
  Time: 오후 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = (String)request.getAttribute("userName");
    if(name == null || name.isEmpty()) name = "사용자";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <script>
        alert("<%= name %>님, 어서오세요!")
        window.location.href = "<%= request.getContextPath() %>/index.jsp"
    </script>
</body>
</html>
