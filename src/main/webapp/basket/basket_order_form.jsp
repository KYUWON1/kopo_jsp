<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.basket.dto.ProductItemDto" %>
<%@ page import="java.util.List" %>

<%
  Member loginUser = (Member) session.getAttribute("loginUser");
  List<ProductItemDto> productList = (List<ProductItemDto>) request.getAttribute("productListByBasketIdList");
  int totalPrice = (Integer) request.getAttribute("totalPrice");
  int totalDeliveryPrice = (Integer) request.getAttribute("totalDeliveryPrice");

  int deliveryFee = 3000;
%>

<%@ include file="/common/header.jsp" %>

<style>
  .order-container {
    max-width: 900px;
    margin: 100px auto;
    padding: 30px;
    font-family: 'Noto Sans KR', sans-serif;
    background-color: #f9f9f9;
  }

  h2 {
    color: #2e7d32;
    margin-bottom: 30px;
  }

  .product-card {
    display: flex;
    align-items: center;
    padding: 20px;
    margin-bottom: 20px;
    background: #fff;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.05);
  }

  .product-image {
    width: 120px;
    height: 120px;
    object-fit: cover;
    margin-right: 20px;
  }

  .product-info {
    flex: 1;
  }

  .product-info p {
    margin: 6px 0;
    font-size: 14px;
  }

  .form-group {
    margin-top: 20px;
  }

  .form-group label {
    display: block;
    margin-bottom: 6px;
    font-weight: bold;
  }

  .form-group input {
    width: 100%;
    padding: 8px;
    border-radius: 4px;
    border: 1px solid #ccc;
  }

  .payment-summary {
    margin-top: 30px;
    padding: 20px;
    background-color: #ffffff;
    border: 1px solid #ddd;
    border-radius: 6px;
  }

  .payment-summary p {
    font-size: 15px;
    margin: 6px 0;
  }

  .form-footer {
    margin-top: 30px;
    text-align: right;
  }

  .btn-submit {
    background-color: #4caf50;
    color: white;
    padding: 12px 24px;
    font-size: 16px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
  }

  .btn-submit:hover {
    background-color: #388e3c;
  }
</style>

<div class="order-container">
  <h2>장바구니 주문서</h2>

  <form method="post" action="submitBasketOrder.do">
    <input type="hidden" name="noUser" value="<%= loginUser.getIdUser() %>">
    <input type="hidden" name="nmUser" value="<%= loginUser.getNmUser() %>">
    <input type="hidden" name="cdOrderType" value="10">
    <input type="hidden" name="stOrder" value="10">
    <input type="hidden" name="stPayment" value="20">

    <% for (int i = 0; i < productList.size(); i++) {
      ProductItemDto item = productList.get(i);
    %>
    <div class="product-card">
      <img src="getImage.do?id=<%= item.getIdFile() %>" class="product-image" alt="상품 이미지">
      <div class="product-info">
        <strong><%= item.getNmProduct() %></strong>
        <input type="hidden" name="noProduct" value="<%= item.getNoProduct() %>">
        <input type="hidden" name="qtQuantity" value="<%= item.getQuantity() %>">
        <input type="hidden" name="qtCustomer" value="<%= item.getQtCustomer() %>">
        <input type="hidden" name="qtDelivery" value="<%= item.getQtDeliveryFee() %>">
        <p>수량: <%= item.getQuantity() %>개</p>
        <p>단가: <%= item.getQtCustomer() %>원</p>
        <p>상품 금액: <%= item.getQtCustomer() * item.getQuantity()%>원</p>
        <p>배송비: <%= item.getQtDeliveryFee() %>원</p>
      </div>
    </div>
    <% } %>

    <div class="payment-summary">
      <p>총 상품 금액: <strong><%= totalPrice %>원</strong></p>
      <p>배송비 총합: <strong><%= deliveryFee %>원</strong></p>
      <p>최종 결제 금액: <strong><%= totalPrice + deliveryFee %>원</strong></p>
      <input type="hidden" name="qtTotalPrice" value="<%= totalPrice %>">
      <input type="hidden" name="qtOrderItemDeliveryFee" value="<%= deliveryFee %>">
    </div>

    <!-- 배송자 정보 -->
    <div class="form-group">
      <label>주문자명</label>
      <input type="text" name="nmOrderPerson" value="<%= loginUser.getNmUser() %>" required>
    </div>

    <div class="form-group">
      <label>수령인명</label>
      <input type="text" name="nmReceiver" value="<%= loginUser.getNmUser() %>" required>
    </div>

    <div class="form-group">
      <label>우편번호</label>
      <div style="display: flex; gap: 10px;">
        <input type="text" id="postcode" name="noDeliveryZipno" required readonly>
        <button type="button" onclick="execDaumPostcode()">우편번호 찾기</button>
      </div>
    </div>

    <div class="form-group">
      <label>주소</label>
      <input type="text" id="address" name="nmDeliveryAddress" required readonly placeholder="기본 주소">
      <input type="text" id="detailAddress" name="nmDeliveryAddressDetail" placeholder="상세 주소">
    </div>

    <div class="form-group">
      <label>연락처</label>
      <input type="text" name="nmReceiverTelno" value="<%= loginUser.getNoMobile() %>" required>
    </div>

    <div class="form-group">
      <label>배송 희망장소</label>
      <input type="text" name="nmDeliverySpace">
    </div>

    <div class="form-group">
      <label>배송 예상 기간</label>
      <input type="number" name="qtDeliPeriod" value="3">
    </div>

    <div class="form-footer">
      <button type="submit" class="btn-submit">주문 제출</button>
    </div>
  </form>
</div>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  function execDaumPostcode() {
    new daum.Postcode({
      oncomplete: function(data) {
        document.getElementById('postcode').value = data.zonecode;
        document.getElementById('address').value = data.roadAddress || data.jibunAddress;
        document.getElementById('detailAddress').focus();
      }
    }).open();
  }
</script>

<%@ include file="/common/simple_footer.jsp" %>
