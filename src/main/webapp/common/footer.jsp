<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 푸터 영역 -->
<footer>
  <div class="footer-container">
    <div class="footer-section">
      <h3>심플리원 쇼핑몰</h3>
      <p>Simple is One, 심규원이 만든<br>심플하고 특별한 쇼핑몰</p>
    </div>
    <div class="footer-section">
      <h3>고객 서비스</h3>
      <ul>
        <li><a href="#">자주 묻는 질문</a></li>
        <li><a href="#">배송 정보</a></li>
        <li><a href="#">반품 정책</a></li>
        <li><a href="#">문의하기</a></li>
      </ul>
    </div>
    <div class="footer-section">
      <h3>회사 정보</h3>
      <ul>
        <li><a href="#">회사 소개</a></li>
        <li><a href="#">이용약관</a></li>
        <li><a href="#">개인정보처리방침</a></li>
      </ul>
    </div>
    <div class="footer-section">
      <h3>연락처</h3>
      <p>이메일: contact@simplyone.co.kr<br>
        전화: 02-123-4567<br>
        주소: 서울시 강남구 테헤란로 123</p>
    </div>
    <div class="copyright">
      &copy; 2023 심플리원 쇼핑몰 All Rights Reserved.
    </div>
  </div>
</footer>
<style>
  /* 푸터 스타일 */
  footer {
    background-color: #2e7d32;
    color: white;
    padding: 30px 0;
    margin-top: 50px;
  }

  .footer-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }

  .footer-section {
    flex: 1;
    min-width: 200px;
    margin-bottom: 20px;
  }

  .footer-section h3 {
    margin-bottom: 15px;
    font-size: 18px;
  }

  .footer-section ul {
    list-style: none;
  }

  .footer-section ul li {
    margin-bottom: 8px;
  }

  .footer-section a {
    color: rgba(255,255,255,0.8);
    text-decoration: none;
  }

  .footer-section a:hover {
    color: white;
    text-decoration: underline;
  }

  .copyright {
    text-align: center;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid rgba(255,255,255,0.1);
    width: 100%;
  }
</style>
</body>
</html>