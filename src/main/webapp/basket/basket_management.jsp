<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.domain.basket.dto.BasketDto" %>
<%@ page import="com.kopo.web_final.domain.basket.dto.BasketItemDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>

<%
  Member loginUser = (Member) request.getSession().getAttribute("loginUser");

  BasketDto basket = (BasketDto) request.getAttribute("basketTable");
  List<BasketItemDto> basketItems = (List<BasketItemDto>) request.getAttribute("basketList");
%>

<style>
  body {
    background-color: #f0fff4;
  }

  .basket-container {
    max-width: 960px;
    margin: 80px auto;
    font-family: 'Noto Sans KR', sans-serif;
    padding: 20px;
  }

  .basket-container h2 {
    color: #2e7d32;
    margin-bottom: 30px;
    font-size: 26px;
    border-bottom: 2px solid #81c784;
    padding-bottom: 10px;
  }

  .basket-card {
    display: flex;
    border-radius: 12px;
    overflow: hidden;
    background-color: #ffffff;
    border: 2px solid #2e7d32;
    box-shadow: 0 4px 10px rgba(46, 125, 50, 0.1);
    margin-bottom: 15px;
    align-items: center;
    position: relative;
    padding-right: 20px;
  }

  .basket-card img {
    width: 220px;
    height: 160px;
    object-fit: cover;
    margin: 20px;
  }

  .basket-card-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding: 20px 0;
  }

  .basket-card-content h3 {
    margin: 0 0 10px;
    font-size: 20px;
    color: #1b5e20;
  }

  .basket-card-content p {
    margin: 6px 0;
    font-size: 15px;
    color: #2e7d32;
  }

  .inline-form {
    display: inline-flex;
    align-items: center;
    gap: 8px;
  }

  .inline-form input[type="number"] {
    width: 60px;
    padding: 6px;
    font-size: 14px;
    border: 1px solid #81c784;
    border-radius: 6px;
  }

  .btn-delete {
    background-color: #e53935;
    color: white;
    border: none;
    padding: 6px 14px;
    border-radius: 6px;
    font-size: 13px;
    cursor: pointer;
    font-weight: bold;
    position: absolute;
    top: 15px;
    right: 15px;
  }

  .btn-delete:hover {
    background-color: #c62828;
  }

  .total-amount {
    text-align: right;
    font-size: 22px;
    margin-top: 40px;
    color: #1b5e20;
    font-weight: bold;
  }

  .btn-purchase {
    margin-top: 30px;
    float: right;
    background-color: #4caf50;
    color: white;
    padding: 14px 28px;
    font-size: 16px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
  }

  .btn-purchase:hover {
    background-color: #388e3c;
  }

  .btn-clear {
    background-color: #ff9800;
    color: white;
    border: none;
    padding: 8px 16px;
    font-size: 14px;
    border-radius: 6px;
    cursor: pointer;
  }

  .btn-clear:hover {
    background-color: #f57c00;
  }
</style>

<%@ include file="/common/header.jsp" %>

<div class="basket-container">
  <div style="display: flex; justify-content: space-between; align-items: center;">
    <h2>장바구니</h2>
    <% if (basketItems != null && !basketItems.isEmpty()) { %>
    <form method="post" action="emptyBasket.do" style="margin: 0;">
      <input type="hidden" name="nbBasket" value="<%= basket.getNbBasket() %>">
      <button type="submit" class="btn-clear">장바구니 비우기</button>
    </form>
    <% } %>
  </div>

  <% if (basketItems != null && !basketItems.isEmpty()) { %>
  <form method="post" action="submitBasket.do">
    <input type="hidden" name="nbBasket" value="<%= basket.getNbBasket() %>">
    <%
      int totalAmount = 0;
      for (BasketItemDto item : basketItems) {
        totalAmount += item.getQtBasketItemAmount();
    %>
    <div class="basket-card">
      <img src="getImage.do?id=<%= item.getIdFile() %>" alt="상품 이미지">
      <div class="basket-card-content">
        <h3><%= item.getNmProduct() %></h3>
        <p>단가: <%= item.getQtBasketItemPrice() %>원</p>
        <div class="inline-form">
          <span>수량:</span>
          <input type="hidden" name="noProduct" value="<%= item.getNoProduct() %>">
          <input type="number" name="quantity" class="quantity-input" value="<%= item.getQtBasketItem() %>" min="1" max="<%= item.getQtStock() %>" data-price="<%= item.getQtBasketItemPrice() %>">
        </div>
        <p>금액: <span class="item-amount"><%= item.getQtBasketItemAmount() %></span>원</p>
      </div>
      <!-- 삭제 버튼 -->
      <button type="button" class="btn-delete" onclick="submitDeleteForm(<%= item.getNbBasketItem() %>)">삭제</button>
    </div>
    <% } %>

    <div class="total-amount">
      총 결제 금액: <span id="totalAmount"><%= totalAmount %></span>원
    </div>

    <button type="submit" class="btn-purchase">전체 상품 구매하기</button>
  </form>
  <!-- 숨겨진 삭제 form -->
  <!-- 삭제 요청을 위한 공통 폼 (하나만 존재) -->
  <form id="deleteForm" method="post" action="deleteBasketItem.do" style="display:none;">
    <input type="hidden" name="nbBasketItem" id="deleteInput">
  </form>
  <% } else { %>
  <p style="text-align:center; font-size: 18px; color: gray;">장바구니에 상품이 없습니다.</p>
  <% } %>
</div>

<%@ include file="/common/simple_footer.jsp" %>

<script>
  document.querySelectorAll(".quantity-input").forEach(input => {
    input.addEventListener("input", () => {
      const price = parseInt(input.dataset.price || "0");
      const quantity = parseInt(input.value || "0");
      const itemAmount = input.closest(".basket-card-content").querySelector(".item-amount");
      const newAmount = price * quantity;
      itemAmount.textContent = newAmount.toLocaleString();

      let total = 0;
      document.querySelectorAll(".item-amount").forEach(el => {
        total += parseInt(el.textContent.replace(/,/g, ""));
      });
      document.getElementById("totalAmount").textContent = total.toLocaleString();
    });
  });

  function submitDeleteForm(nbBasketItem) {
    if (confirm("정말 삭제하시겠습니까?")) {
      const input = document.getElementById("deleteInput");
      input.value = nbBasketItem;
      document.getElementById("deleteForm").submit();
    }
  }
</script>
