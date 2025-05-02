<%@ page import="com.kopo.web_final.member.model.Member" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 30.
  Time: 오후 1:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Member loginMember = (Member) session.getAttribute("loginUser");
  String name = "사용자";
  if(loginMember != null){
    name = loginMember.getNmUser();
  }
  session.invalidate();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <script>
    alert("<%= name %>님 안녕히 가세요.");
    window.location.href = "<%= request.getContextPath() %>/index.jsp";
  </script>
</body>
</html>
