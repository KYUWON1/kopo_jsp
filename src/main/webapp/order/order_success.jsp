<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.*,java.util.*" %>
<%@ page import="com.kopo.web_final.domain.member.model.Member" %>
<%
  Member loginUser = (Member) request.getSession().getAttribute("loginUser");

  String message = (String) request.getAttribute("message");
  String type = (String) request.getAttribute("type");
  String orderId = (String) request.getAttribute("orderId");
  String orderAmount = (String) request.getAttribute("orderAmount");
  String deliveryFee = (String) request.getAttribute("deliveryFee");
%>

<style>
  .success-container {
    max-width: 700px;
    margin: 120px auto;
    padding: 40px;
    font-family: Arial, sans-serif;
    border-radius: 8px;
  }

  .success-container.success {
    background-color: #f9fdf9;
    border: 1px solid #c8e6c9;
    color: #2e7d32;
  }

  .success-container.error {
    background-color: #fff8f8;
    border: 1px solid #ef9a9a;
    color: #c62828;
  }

  .success-container h2 {
    margin-bottom: 20px;
    font-size: 24px;
  }

  .success-container p {
    margin-bottom: 10px;
    font-size: 16px;
  }

  .success-container .highlight {
    font-weight: bold;
    color: inherit; /* 현재 텍스트 컬러 상속 */
  }

  .success-container .btn-home {
    margin-top: 30px;
    display: inline-block;
    background-color: #4caf50;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 6px;
    transition: background-color 0.2s;
  }

  .success-container .btn-home:hover {
    background-color: #388e3c;
  }
</style>

<%@ include file="/common/header.jsp" %>

<div class="success-container <%= "error".equals(type) ? "error" : "success" %>">
  <h2><%= message != null ? message : "주문 완료" %></h2>

  <p>주문 ID: <span class="highlight"><%= orderId %></span></p>
  <p>주문자: <span class="highlight"><%= loginUser.getNmUser() %></span></p>
  <p>주문 금액: <span class="highlight"><%= orderAmount %>원</span></p>
  <p>배송비: <span class="highlight"><%= deliveryFee %>원</span></p>
  <p>총 결제 금액: <span class="highlight"><%= Integer.parseInt(orderAmount) + Integer.parseInt(deliveryFee) %>원</span></p>

  <a href="main.do" class="btn-home">홈으로 이동</a>
</div>

<%@ include file="/common/simple_footer.jsp" %>
