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

<!-- 콘텐츠 영역 -->
<div class="content-container">
    <div class="banner">
        <h1>심플리원 쇼핑몰에 오신 것을 환영합니다</h1>
        <p>Simple is One, 심규원이 만든 심플하고 특별한 쇼핑몰</p>
    </div>

    <div class="main-content">
        <div class="featured-section">
            <h2>추천 상품</h2>
            <div class="product-grid">
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">친환경 에코백</div>
                        <div class="product-price">19,800원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">자연주의 화장품 세트</div>
                        <div class="product-price">48,000원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">유기농 티셔츠</div>
                        <div class="product-price">32,000원</div>
                    </div>
                </div>
                <div class="product-card">
                    <div class="product-img">상품 이미지</div>
                    <div class="product-info">
                        <div class="product-title">재활용 노트북 파우치</div>
                        <div class="product-price">25,500원</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp" %>