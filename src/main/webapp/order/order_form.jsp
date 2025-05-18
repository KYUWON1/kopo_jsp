<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>

<%
  Member loginUser = AuthUtils.checkLogin(request,response);
  if (loginUser == null) {
    request.setAttribute("message", "로그인이 필요한 서비스입니다.");
    response.sendRedirect(request.getContextPath() + "/member/login.jsp");
  }
  String quantityStr = (String) request.getAttribute("quantity");
  String buyPriceStr = (String) request.getAttribute("buyPrice");
  String sellPriceStr = (String) request.getAttribute("sellPrice");
  String productId = (String) request.getAttribute("productId");
  String deliveryPrice = (String) request.getAttribute("deliveryPrice");

  int quantity = quantityStr != null ? Integer.parseInt(quantityStr) : 0;
  int buyPrice = buyPriceStr != null ? Integer.parseInt(buyPriceStr) : 0;
  int deliVeryFee = deliveryPrice != null ? Integer.parseInt(deliveryPrice) : 0;
  int totalAmount = quantity * buyPrice;
%>

<style>
  .order-container {
    max-width: 800px;
    margin: 100px auto;
    padding: 30px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
  }

  h2 {
    margin-bottom: 20px;
    color: #2e7d32;
  }

  .form-group {
    margin-bottom: 15px;
  }

  .form-group label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
  }

  .form-group input,
  .form-group textarea {
    width: 100%;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #ccc;
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

  .payment-info {
    margin-top: 30px;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 6px;
  }

  .payment-info h3 {
    margin-bottom: 15px;
    font-size: 18px;
    color: #444;
  }

  .payment-info p {
    font-size: 14px;
    margin: 5px 0;
  }
</style>

<%@ include file="/common/header.jsp" %>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<div class="order-container">
  <h2>주문서 작성</h2>

  <form method="post" action="submitOrder.do">
    <input type="hidden" name="noUser" value="<%= loginUser.getIdUser() %>">
    <input type="hidden" name="noProduct" value="<%= productId %>">
    <input type="hidden" name="qtOrderItem" value="<%= quantity %>">
    <input type="hidden" name="qtOrderAmount" value="<%= totalAmount %>">
    <input type="hidden" name="qtUnitPrice" value="<%= deliveryPrice %>">
    <input type="hidden" name="qtOrderItemDeliveryFee" value="3000"> <!-- 고정배송비 -->

    <div class="form-group">
      <label>주문자명</label>
      <input type="text" name="nmOrderPerson" value="<%= loginUser.getNmUser() %>" required>
    </div>

    <div class="form-group">
      <label>이메일</label>
      <input type="text" value="<%= loginUser.getNmEmail() %>" readonly>
    </div>

    <div class="form-group">
      <label>수령인명</label>
      <input type="text" name="nmReceiver" value="<%= loginUser.getNmUser() %>"required>
    </div>

    <div class="form-group">
      <label>우편번호</label>
      <div style="display: flex; gap: 10px;">
        <input type="text" id="postcode" name="noDeliveryZipno" required readonly>
        <button type="button" onclick="execDaumPostcode()" style="padding: 6px 12px;">우편번호 찾기</button>
      </div>
    </div>

    <div class="form-group">
      <label>배송 주소</label>
      <input type="text" id="address" name="nmDeliveryAddress" required readonly placeholder="기본 주소">
      <input type="text" id="detailAddress" name="nmDeliveryAddressDetail" placeholder="상세 주소">
    </div>

    <div class="form-group">
      <label>연락처</label>
      <input type="text" name="nmReceiverTelno" value="<%= loginUser.getNoMobile() %>"required>
    </div>

    <div class="form-group">
      <label>원하는 배송 장소</label>
      <input type="text" name="nmDeliverySpace">
    </div>

    <div class="form-group">
      <label>배송 예상 기간</label>
      <input type="number" name="qtDeliPeriod" value="3">
    </div>

    <input type="hidden" name="cdOrderType" value="10"> <!-- 일반 주문 -->
    <input type="hidden" name="stOrder" value="10">     <!-- 주문 완료 -->
    <input type="hidden" name="stPayment" value="20">   <!-- 결제 완료 -->
    <input type="hidden" name="noRegister" value="<%= loginUser.getIdUser() %>">

    <div class="payment-info">
      <h3>결제 정보</h3>
      <p>상품 가격: <strong><%= String.format("%,d원", buyPrice) %></strong></p>
      <p>주문 수량: <strong><%= quantity %>개</strong></p>
      <p>배송비: <strong><%= deliVeryFee %>원</strong></p>
      <p>총 결제 금액: <strong><%= String.format("%,d원", totalAmount + 3000) %></strong></p>
    </div>

    <div class="form-footer">
      <button type="submit" class="btn-submit">주문 제출</button>
    </div>
  </form>
</div>

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
