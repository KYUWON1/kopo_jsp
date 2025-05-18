<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.product.model.Product" %>

<style>
    .detail-wrapper {
        max-width: 1200px;
        margin: 120px auto;
        padding: 30px 20px;
        display: flex;
        gap: 40px;
        font-family: 'Helvetica', 'Arial', sans-serif;
    }

    .detail-left {
        flex: 1;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
        padding: 20px;
        text-align: center;
        background-color: #fff;
    }

    .detail-left img {
        max-width: 100%;
        height: auto;
        border-radius: 6px;
    }

    .detail-right {
        flex: 1;
        padding: 20px;
        background-color: #fff;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
    }

    .detail-right h1 {
        font-size: 24px;
        margin-bottom: 16px;
        color: #111;
    }

    .detail-price-original {
        font-size: 14px;
        color: #c40000;
        text-decoration: line-through;
        margin-bottom: 4px;
    }

    .detail-price-sale {
        font-size: 22px;
        font-weight: bold;
        color: #4caf50;
        margin-bottom: 12px;
    }

    .detail-desc {
        font-size: 14px;
        color: #555;
        margin-bottom: 20px;
        line-height: 1.6;
    }

    .detail-stock {
        font-size: 14px;
        color: #666;
        margin-bottom: 20px;
    }

    .quantity-group {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 20px;
    }

    .quantity-group input[type="number"] {
        width: 60px;
        padding: 6px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    .button-group {
        display: flex;
        gap: 10px;
        margin-top: 20px;
    }

    .btn-cart {
        flex: 1;
        background-color: #ffffff;
        color: #4caf50;
        font-weight: bold;
        border: 2px solid #4caf50;
        padding: 14px 0;
        border-radius: 6px;
        cursor: pointer;
        font-size: 16px;
        transition: all 0.2s;
    }

    .btn-cart:hover {
        background-color: #e8f5e9;
    }

    .btn-order {
        flex: 1;
        background-color: #4caf50;
        color: white;
        font-weight: bold;
        border: none;
        padding: 14px 0;
        border-radius: 6px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.2s;
    }

    .btn-order:hover {
        background-color: #388e3c;
    }


</style>
<%@ include file="/common/header.jsp" %>

<%
    String message = (String) request.getAttribute("message");
    String type = (String) request.getAttribute("type");
%>

<%-- 메시지가 존재하면 표시 --%>

<% if (message != null && !message.isEmpty()) { %>
<div style="max-width: 800px; margin: 80px auto 20px; padding: 15px; border-radius: 6px;
        font-weight: bold; font-size: 16px;
        color: <%= "success".equals(type) ? "#2e7d32" : "#c62828" %>;
        background-color: <%= "success".equals(type) ? "#e8f5e9" : "#ffebee" %>;
        border: 1px solid <%= "success".equals(type) ? "#a5d6a7" : "#ef9a9a" %>;">
    <%= message %>
</div>
<% } %>

<%
    Product product = (Product)request.getAttribute("productDetailById");
    if (product == null) {
%>
    <h2 style="text-align: center; margin-top: 150px;">해당 상품을 찾을 수 없습니다.</h2>
<%
        return;
    }
%>

<div class="detail-wrapper">
    <div class="detail-left">
        <img src="getImage.do?id=<%= product.getIdFile() %>" alt="상품 이미지">
    </div>

    <div class="detail-right">
        <h1><%= product.getNmProduct() %></h1>
        <div class="detail-price-original"><%= String.format("%,d원", product.getQtSalePrice()) %></div>
        <div class="detail-price-sale"><%= String.format("%,d원", product.getQtCustomer()) %></div>
        <div class="detail-desc"><%= product.getNmDetailExplain() %></div>
        <div class="detail-stock">재고: <%= product.getQtStock() %>개</div>

        <form method="post" action="productOrderForm.do">
            <input type="hidden" name="productId" value="<%= product.getNoProduct() %>">
            <input type="hidden" name="buyPrice" value="<%= product.getQtCustomer() %>">
            <input type="hidden" name="SellPrice" value="<%= product.getQtSalePrice() %>">
            <input type="hidden" name="deliveryPrice" value="<%= product.getQtDeliveryFee() %>">

            <div class="quantity-group">
                <label>수량</label>
                <input type="number" name="quantity" min="1" max="<%= product.getQtStock() %>" value="1">
            </div>
            <div class="button-group">
                <button type="submit" name="action" value="basket" class="btn-cart">장바구니 담기</button>
                <button type="submit" name="action" value="order" class="btn-order">바로 구매</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="/common/simple_footer.jsp" %>