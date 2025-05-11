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

  .category-menu {
    margin-bottom: 30px;
    position: relative;
    z-index: 100;
  }

  .category-root {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }

  .category-item {
    position: relative;
  }

  .category-item > a {
    display: inline-block;
    font-weight: bold;
    color: #2e7d32;
    text-decoration: none;
    padding: 8px 12px;
    background-color: #e8f5e9;
    border-radius: 6px;
    transition: background-color 0.2s;
  }

  .category-item > a:hover {
    background-color: #c8e6c9;
  }

  .category-sub {
    position: absolute;
    top: 100%;
    left: 0;
    list-style: none;
    background: white;
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 10px 0;
    margin: 0;
    display: none;
    min-width: 180px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    z-index: 200;
  }

  .category-item:hover > .category-sub {
    display: block;
  }

  .category-sub li {
    position: relative;
    padding: 0;
  }

  .category-sub a {
    display: block;
    padding: 8px 16px;
    color: #333;
    text-decoration: none;
    font-size: 14px;
  }

  .category-sub a:hover {
    color: #4caf50;
    font-weight: bold;
    background-color: #f1f8f4;
  }

  .category-sub .category-sub {
    top: 0;
    left: 100%;
    margin-left: 2px;
    z-index: 300;
  }

  .category-sub li:hover > .category-sub {
    display: block;
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

  Member loginUser = (Member) session.getAttribute("loginUser");

%>

<!-- 콘텐츠 영역 -->
<div class="content-container">
  <div class="banner">
    <h1>심플리원 쇼핑몰에 오신 것을 환영합니다</h1>
    <p>Simple is One, 심규원이 만든 심플하고 특별한 쇼핑몰</p>
  </div>



  <div class="main-content">
    <div class="category-menu">
      <ul class="category-root">
        <% for (Category parent : categoryList) {
          if (parent.getNbCategory() == 0) { %>
          <li class="category-item">
            <a href="main.do?categoryId=" style="font-weight: bold; color: #2e7d32; text-decoration: none;">
              <%= parent.getNmCategory() %>
            </a>
          </li>
        <%
          continue; }
          if (parent.getNbParentCategory() == 0 && "Y".equals(parent.getYnUse()) && !"Y".equals(parent.getYnDelete())) { %>
        <li class="category-item">
          <!-- ✅ 부모 카테고리도 링크로 변경 -->
          <a href="main.do?categoryId=<%= parent.getNbCategory() %>" style="font-weight: bold; color: #2e7d32; text-decoration: none;">
            <%= parent.getNmCategory() %>
          </a>

          <ul class="category-sub">
            <% for (Category child : categoryList) {
              if (child.getNbParentCategory() == parent.getNbCategory() && "Y".equals(child.getYnUse()) && !"Y".equals(child.getYnDelete())) { %>
            <li>
              <a href="main.do?categoryId=<%= child.getNbCategory() %>"><%= child.getNmCategory() %></a>

              <!-- ✅ 손자 카테고리(3단계) -->
              <ul class="category-sub">
                <% for (Category grandChild : categoryList) {
                  if (grandChild.getNbParentCategory() == child.getNbCategory() && "Y".equals(grandChild.getYnUse()) && !"Y".equals(grandChild.getYnDelete())) { %>
                <li>
                  <a href="main.do?categoryId=<%= grandChild.getNbCategory() %>"><%= grandChild.getNmCategory() %></a>
                </li>
                <% } } %>
              </ul>
            </li>
            <% } } %>
          </ul>
        </li>
        <% } } %>
      </ul>
    </div>
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