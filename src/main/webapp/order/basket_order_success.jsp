<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.domain.order.dto.GetOrderItemDto" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>
<%
  Member loginUser = (Member) request.getSession().getAttribute("loginUser");

  String message = (String) request.getAttribute("message");
  String orderId = (String) request.getAttribute("orderId");
  String nmUser = (String) request.getAttribute("nmUser");
  int totalPrice = (int) request.getAttribute("totalPrice");
  int deliveryFee = (int) request.getAttribute("deliveryFee");
  List<GetOrderItemDto> orderItemList = (List<GetOrderItemDto>) request.getAttribute("orderItemList");
%>

<%@ include file="/common/header.jsp" %>

<style>
  .order-complete-container {
    max-width: 800px;
    margin: 60px auto;
    padding: 30px;
    background-color: #f9fff9;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0,0,0,0.1);
    font-family: 'Noto Sans KR', sans-serif;
  }

  .order-complete-title {
    font-size: 24px;
    color: #2e7d32;
    font-weight: bold;
    margin-bottom: 20px;
  }

  .order-summary {
    margin-top: 20px;
    line-height: 1.8;
  }

  .order-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 30px;
  }

  .order-table th, .order-table td {
    padding: 10px;
    border: 1px solid #ccc;
    text-align: center;
  }

  .order-table th {
    background-color: #e8f5e9;
    color: #2e7d32;
  }

  .total-box {
    text-align: right;
    margin-top: 20px;
    font-size: 18px;
    font-weight: bold;
    color: #333;
  }

  .btn-home {
    display: inline-block;
    margin-top: 30px;
    padding: 10px 20px;
    background-color: #4caf50;
    color: white;
    text-decoration: none;
    border-radius: 6px;
  }

  .btn-home:hover {
    background-color: #388e3c;
  }
</style>

<div class="order-complete-container">
  <div class="order-complete-title"><%= message %></div>

  <div class="order-summary">
    <p><strong>주문번호:</strong> <%= orderId %></p>
    <p><strong>주문자명:</strong> <%= nmUser %></p>
    <p><strong>배송비:</strong> <%= String.format("%,d", deliveryFee) %>원</p>
  </div>

  <table class="order-table">
    <thead>
    <tr>
      <th>상품명</th>
      <th>수량</th>
      <th>단가</th>
      <th>총액</th>
    </tr>
    </thead>
    <tbody>
    <% for (GetOrderItemDto item : orderItemList) { %>
    <tr>
      <td><%= item.getNmProduct() %></td>
      <td><%= item.getQtOrderItem() %>개</td>
      <td><%= String.format("%,d", item.getQtUnitPrice()) %>원</td>
      <td><%= String.format("%,d", item.getQtOrderItem() * item.getQtUnitPrice()) %>원</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <div class="total-box">
    총 결제 금액: <%= String.format("%,d", totalPrice) %>원
  </div>

  <a href="main.do" class="btn-home">홈으로 이동</a>
</div>

<%@ include file="/common/simple_footer.jsp" %>
