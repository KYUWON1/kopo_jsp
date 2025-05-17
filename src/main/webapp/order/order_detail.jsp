<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.order.dto.GetOrderItemDto" %>
<%@ page import="java.util.List" %>

<%
  String idOrder = (String) request.getAttribute("idOrder");
  String totalPrice = (String) request.getAttribute("totalPrice");
  String nmReceiver = (String) request.getAttribute("nmReceiver");
  String nmDeliveryAddress = (String) request.getAttribute("nmDeliveryAddress");
  String daOrder = (String) request.getAttribute("daOrder");

  List<GetOrderItemDto> orderItemList = (List<GetOrderItemDto>) request.getAttribute("orderItemList");
%>

<%@ include file="/common/header.jsp" %>

<style>
  body {
    background-color: #f0fff4;
  }

  .order-detail-container {
    max-width: 960px;
    margin: 80px auto;
    padding: 30px;
    font-family: 'Noto Sans KR', sans-serif;
  }

  .title {
    font-size: 26px;
    margin-bottom: 20px;
    color: #1b5e20;
    border-bottom: 2px solid #81c784;
    padding-bottom: 8px;
  }

  .order-summary {
    margin-bottom: 40px;
    padding: 20px;
    background-color: #ffffff;
    border: 2px solid #2e7d32;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(46, 125, 50, 0.1);
  }

  .order-summary p {
    margin: 8px 0;
    font-size: 15px;
    color: #2e7d32;
  }

  .product-item {
    background-color: #ffffff;
    border: 2px solid #2e7d32;
    border-radius: 12px;
    display: flex;
    margin-bottom: 20px;
    box-shadow: 0 4px 10px rgba(46, 125, 50, 0.1);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }

  .product-item:hover {
    transform: translateY(-4px);
    box-shadow: 0 6px 14px rgba(46, 125, 50, 0.15);
  }

  .product-item img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-top-left-radius: 12px;
    border-bottom-left-radius: 12px;
  }

  .product-info {
    padding: 20px;
    flex: 1;
  }

  .product-info h4 {
    margin-top: 0;
    font-size: 18px;
    color: #2e7d32;
  }

  .product-info p {
    margin: 6px 0;
    font-size: 14px;
    color: #333;
  }

  .btn-back {
    background-color: #9e9e9e;
    color: white;
    padding: 10px 20px;
    font-size: 15px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    float: right;
    margin-bottom: 30px;
  }

  .btn-back:hover {
    background-color: #757575;
  }
</style>


<div class="order-detail-container">
  <button onclick="history.back()" class="btn-back">뒤로가기</button>

  <h2 class="title">주문 상세 정보</h2>

  <div class="order-summary">
    <p><strong>주문번호:</strong> <%= idOrder %></p>
    <p><strong>총 결제 금액:</strong> <%= totalPrice %>원</p>
    <p><strong>수령인:</strong> <%= nmReceiver %></p>
    <p><strong>배송지:</strong> <%= nmDeliveryAddress %></p>
    <p><strong>주문일자:</strong> <%= daOrder %></p>
  </div>

  <h3 class="title">상품 정보</h3>

  <% if (orderItemList != null && !orderItemList.isEmpty()) {
    for (GetOrderItemDto item : orderItemList) { %>
  <a href="productDetail.do?productId=<%= item.getNoProduct() %>" style="text-decoration: none;">
    <div class="product-item">
      <img src="getImage.do?id=<%= item.getIdFile() %>" alt="상품 이미지">
      <div class="product-info">
        <h4><%= item.getNmProduct() %></h4>
        <p>단가: <%= item.getQtUnitPrice() %>원</p>
        <p>수량: <%= item.getQtOrderItem() %>개</p>
        <p>상품 금액: <%= item.getQtOrderItemAmount() %>원</p>
        <p>배송비: <%= item.getQtOrderItemDelivery() %>원</p>
      </div>
    </div>
  </a>
  <%  }
  } else { %>
  <p style="text-align: center; color: gray;">주문된 상품이 없습니다.</p>
  <% } %>
</div>

<%@ include file="/common/simple_footer.jsp" %>
