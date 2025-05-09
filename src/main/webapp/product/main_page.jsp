<%@ page import="com.kopo.web_final.category.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.product.dto.ProductDisplayDto" %>
<%@ page import="com.kopo.web_final.product.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  /* 콘텐츠 영역 */
  .content-container {
    max-width: 1200px;
    margin: 120px auto 40px;
    padding: 0 20px;
  }

  .main-content {
    background-color: white;
    border-radius: 8px;
    padding: 30px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    border: 1px solid #e0e0e0;
  }

  .banner {
    background-color: #4caf50;
    color: white;
    text-align: center;
    padding: 60px 30px;
    border-radius: 8px;
    margin-bottom: 30px;
  }

  .banner h1 {
    font-size: 32px;
    margin-bottom: 15px;
  }

  .banner p {
    font-size: 18px;
    opacity: 0.9;
  }

  .featured-section {
    margin: 40px 0;
  }

  .featured-section h2 {
    color: #2e7d32;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #e0e0e0;
  }

  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
  }

  .product-card {
    background-color: white;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    transition: transform 0.3s, box-shadow 0.3s;
  }

  .product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  }

  .product-img {
    height: 200px;
    background-color: #e8f5e9;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #4caf50;
    font-size: 16px;
  }

  .product-info {
    padding: 15px;
  }

  .product-title {
    font-weight: 600;
    margin-bottom: 8px;
  }

  .product-price {
    color: #4caf50;
    font-weight: 600;
  }

  .category-select-form {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 30px;
  }

  .category-select-form label {
    font-size: 16px;
    font-weight: 500;
    color: #2e7d32;
  }

  .category-select-form select {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 15px;
    outline: none;
    transition: border-color 0.3s;
  }

  .category-select-form select:hover,
  .category-select-form select:focus {
    border-color: #4caf50;
    box-shadow: 0 0 5px rgba(76, 175, 80, 0.4);
  }
</style>

<%@ include file="/common/header.jsp" %>

<%
  String leaveResult = request.getParameter("leave");
  if ("success".equals(leaveResult)) {
%>
  <script>
    alert("정상적으로 탈퇴 처리되었습니다.");
  </script>
<%
  }
%>
<%
  List<Category> categoryList = (List<Category>)request.getAttribute("categoryList");
  List<ProductDisplayDto> productListWithCategory = (List<ProductDisplayDto>)request.getAttribute("productListWithCategory");
%>

<!-- 콘텐츠 영역 -->
<div class="content-container">
  <div class="banner">
    <h1>심플리원 쇼핑몰에 오신 것을 환영합니다</h1>
    <p>Simple is One, 심규원이 만든 심플하고 특별한 쇼핑몰</p>
  </div>



  <div class="main-content">
    <form method="get" action="main" class="category-select-form">
      <label for="categoryId">카테고리 선택:</label>
      <select name="categoryId" id="categoryId" onchange="this.form.submit()">
        <option value="">전체</option>
        <% for (Category c : categoryList) { %>
        <option value="<%= c.getNbCategory() %>"
                <%= request.getParameter("categoryId") != null && request.getParameter("categoryId").equals(String.valueOf(c.getNbCategory())) ? "selected" : "" %>>
          <%= c.getNmFullCategory() %>
        </option>
        <% } %>
      </select>
    </form>
    <div class="featured-section">
      <h2>추천 상품</h2>

      <div class="product-grid">
        <% for (ProductDisplayDto dto : productListWithCategory) {
          Product p = dto.getProduct();
        %>
        <div class="product-card">
          <div class="product-img">
            <img src="<%= request.getContextPath() %>/upload/<%= p.getIdFile() %>" alt="상품 이미지" style="max-width: 100%; max-height: 100%;">
          </div>
          <div class="product-info">
            <div class="product-title"><%= p.getNmProduct() %></div>
            <div class="product-price"><%= String.format("%,d원", p.getQtSalePrice()) %></div>
            <div class="product-desc" style="font-size: 14px; color: #777; margin-top: 8px;"><%= p.getNmDetailExplain() %></div>
            <div class="product-category" style="font-size: 13px; color: #999; margin-top: 4px;">카테고리: <%= dto.getCategoryName() %></div>
          </div>
        </div>
        <% } %>
      </div>
    </div>
  </div>
</div>

<%@ include file="/common/footer.jsp" %>