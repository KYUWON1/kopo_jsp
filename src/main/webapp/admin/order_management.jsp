<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.domain.order.dto.GetOrderDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>

<%
  Member loginUser = (Member) request.getSession().getAttribute("loginUser");

  List<GetOrderDto> orderList = (List<GetOrderDto>) request.getAttribute("orderList");
  String message = (String) request.getAttribute("message");
%>

<%@ include file="/common/header.jsp" %>

<style>
  body {
    background-color: #f0fff4;
    font-family: 'Noto Sans KR', sans-serif;
  }

  .order-container {
    max-width: 960px;
    margin: 80px auto;
    padding: 20px;
  }

  .order-title {
    font-size: 26px;
    color: #2e7d32;
    margin-bottom: 30px;
    border-bottom: 2px solid #81c784;
    padding-bottom: 10px;
  }

  .order-card {
    background-color: #fff;
    border: 2px solid #2e7d32;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(46, 125, 50, 0.1);
    padding: 20px;
    margin-bottom: 20px;
    transition: all 0.25s ease;
  }

  .order-card:hover {
    box-shadow: 0 8px 16px rgba(46, 125, 50, 0.2);
    transform: translateY(-4px);
  }

  .order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
  }

  .order-date {
    font-size: 17px;
    font-weight: bold;
    color: #2e7d32;
  }

  .order-detail-btn {
    padding: 8px 16px;
    background-color: #ffffff;
    border: 1px solid #2e7d32;
    color: #2e7d32;
    border-radius: 6px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s;
  }

  .order-detail-btn:hover {
    background-color: #e8f5e9;
  }

  .order-body p {
    margin: 8px 0;
    font-size: 15px;
    color: #333;
  }

  .order-amount {
    font-size: 17px;
    font-weight: bold;
    color: #1b5e20;
  }

  .no-orders {
    text-align: center;
    font-size: 18px;
    color: gray;
    margin-top: 60px;
  }

  form {
    margin: 0;
  }

  .message-box {
    margin-bottom: 30px;
    padding: 15px 20px;
    border-radius: 8px;
    background-color: #e8f5e9;
    border-left: 4px solid #4caf50;
    color: #2e7d32;
    font-size: 15px;
    font-weight: 500;
  }
</style>

<div class="order-container">
  <h2 class="order-title">주문 내역</h2>

  <% if (message != null && !message.isEmpty()) { %>
  <div class="message-box"><%= message %></div>
  <% } %>

  <% if (orderList == null || orderList.isEmpty()) { %>
  <div class="no-orders">주문 내역이 없습니다.</div>
  <% } else {
    for (GetOrderDto order : orderList) {
  %>

  <div class="order-card">
    <div class="order-header">
      <div class="order-date"><%= order.getDaOrder() %> 주문</div>

      <form method="post" action="orderDetailManagement.do">
        <input type="hidden" name="idOrder" value="<%= order.getIdOrder() %>">
        <input type="hidden" name="totalPrice" value="<%= order.getQtOrderAmount() %>">
        <input type="hidden" name="nmReceiver" value="<%= order.getNmReceiver() %>">
        <input type="hidden" name="nmDeliveryAddress" value="<%= order.getNmDeliveryAddress() %>">
        <input type="hidden" name="daOrder" value="<%= order.getDaOrder() %>">
        <button type="submit" class="order-detail-btn">주문 상세보기</button>
      </form>
    </div>

    <div class="order-body">
      <p><strong>주문번호:</strong> <%= order.getIdOrder() %></p>
      <p><strong>고객 ID:</strong> <%= order.getNoUser() %></p>
      <p><strong>주문자:</strong> <%= order.getNmOrderPerson() %></p>
      <p class="order-amount">주문 금액: <%= String.format("%,d", order.getQtOrderAmount()) %>원</p>
      <p><strong>배송지:</strong> <%= order.getNmDeliveryAddress() %> <%= order.getNmDeliverySpace() %></p>
      <p><strong>수령인:</strong> <%= order.getNmReceiver() %></p>
      <p><strong>배송 상태:</strong>
        <%= "10".equals(order.getStOrder()) ? "결제완료" :
                "20".equals(order.getStOrder()) ? "배송중" :
                        "기타 상태 (" + order.getStOrder() + ")" %>
      </p>
    </div>
  </div>

  <% } } %>
</div>

<%@ include file="/common/simple_footer.jsp" %>
