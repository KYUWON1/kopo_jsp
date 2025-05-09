<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.product.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.kopo.web_final.category.model.Category" %>
<%@ page import="com.kopo.web_final.product.dto.ProductDisplayDto" %>
<%
    // 관리자 로그인 확인
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
        response.sendRedirect("/member/login.jsp");
        return;
    }

    // 처리 결과 메시지
    String message = request.getParameter("message");
    String messageType = request.getParameter("type"); // success 또는 error

    // 임시 상품 데이터 (실제로는 DB에서 가져와야 함)
    List<ProductDisplayDto> productList = (List<ProductDisplayDto>)request.getAttribute("productListWithCategory");
    List<Category> categoryList = (List<Category>)request.getAttribute("categoryList");

    // 숫자 포맷
    NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.KOREA);
%>
<style>
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    /* 상품 관리 컨테이너 스타일 */
    .content-container {
        max-width: 1200px;
        margin: 40px auto;
        padding: 0 20px;
        flex-grow: 1;
    }

    .page-title {
        font-size: 24px;
        color: #2e7d32;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 2px solid #c8e6c9;
    }

    .alert {
        padding: 12px 15px;
        margin-bottom: 20px;
        border-radius: 6px;
        font-weight: 500;
    }

    .alert-success {
        background-color: #e8f5e9;
        border-left: 4px solid #4caf50;
        color: #2e7d32;
    }

    .alert-error {
        background-color: #ffebee;
        border-left: 4px solid #f44336;
        color: #d32f2f;
    }

    .card {
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        overflow: hidden;
        margin-bottom: 30px;
    }

    .card-header {
        background-color: #e8f5e9;
        padding: 15px 20px;
        border-bottom: 1px solid #c8e6c9;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .card-title {
        font-size: 18px;
        color: #2e7d32;
        font-weight: 600;
    }

    .card-body {
        padding: 20px;
    }

    /* 테이블 스타일 */
    .table-container {
        overflow-x: auto;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
    }

    th {
        background-color: #4caf50;
        color: white;
        font-weight: 600;
        text-align: left;
        padding: 12px 15px;
        border: 1px solid #c8e6c9;
    }

    td {
        padding: 12px 15px;
        border: 1px solid #c8e6c9;
        text-align: left;
        vertical-align: middle;
    }

    tr:nth-child(even) {
        background-color: #f2f7f2;
    }

    tr:hover {
        background-color: #e8f5e9;
    }

    /* 버튼 스타일 */
    .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        font-weight: 500;
        transition: all 0.2s;
    }

    .btn-add {
        background-color: #4caf50;
        color: white;
    }

    .btn-add:hover {
        background-color: #388e3c;
    }

    .btn-edit {
        background-color: #2196f3;
        color: white;
        margin-right: 5px;
    }

    .btn-edit:hover {
        background-color: #1976d2;
    }

    .btn-delete {
        background-color: #f44336;
        color: white;
    }

    .btn-delete:hover {
        background-color: #d32f2f;
    }

    .btn-status {
        background-color: #ff9800;
        color: white;
    }

    .btn-status:hover {
        background-color: #f57c00;
    }

    .actions {
        display: flex;
        gap: 5px;
    }

    /* 모달 스타일 */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        overflow-y: auto;
        padding: 20px 0;
    }

    .modal-content {
        background-color: white;
        margin: 3% auto;
        padding: 25px;
        width: 500px;
        max-height: 90vh;
        border-radius: 8px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
        overflow-y: auto;
        position: relative;
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 1px solid #e0e0e0;
    }

    .modal-title {
        font-size: 20px;
        color: #2e7d32;
        font-weight: 600;
    }

    .close {
        font-size: 24px;
        font-weight: bold;
        color: #757575;
        cursor: pointer;
    }

    .close:hover {
        color: #333;
    }

    .form-group {
        margin-bottom: 10px;
    }

    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
        color: #555;
    }

    .form-input {
        width: 100%;
        padding: 10px 12px;
        border: 1px solid #c8e6c9;
        border-radius: 4px;
        font-size: 14px;
    }

    .form-input:focus {
        border-color: #4caf50;
        outline: none;
        box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
    }

    .form-select {
        width: 100%;
        padding: 10px 12px;
        border: 1px solid #c8e6c9;
        border-radius: 4px;
        font-size: 14px;
        background-color: white;
    }

    .form-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 15px;
    }

    .status-badge {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 500;
    }

    .status-active {
        background-color: #e8f5e9;
        color: #2e7d32;
    }

    .status-inactive {
        background-color: #ffebee;
        color: #d32f2f;
    }

    .empty-state {
        padding: 40px 20px;
        text-align: center;
        color: #757575;
    }

    .empty-text {
        font-size: 16px;
    }

    textarea.form-input {
        min-height: 60px;
        resize: vertical;
    }

    .product-thumbnail {
        width: 60px;
        height: 60px;
        object-fit: cover;
        border-radius: 4px;
    }

    .stock-badge {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 500;
    }

    .stock-available {
        background-color: #e8f5e9;
        color: #2e7d32;
    }

    .stock-low {
        background-color: #fff3e0;
        color: #e65100;
    }

    .stock-out {
        background-color: #ffebee;
        color: #d32f2f;
    }

    .status-inactive {
        background-color: #ffebee;
        color: #d32f2f;
        font-weight: bold;
    }

    /* 판매상태, 배송비, 관리 열 줄바꿈 방지 및 너비 조정 */
    th:nth-child(7), td:nth-child(7),  /* 판매상태 */
    th:nth-child(9), td:nth-child(9),  /* 배송비 */
    th:nth-child(10), td:nth-child(10) /* 관리 */
    {
        white-space: nowrap;
        text-align: center;
        vertical-align: middle;
    }

    /* 관리 열 너비 및 버튼 정렬 */
    th:nth-child(10),
    td:nth-child(10) {
        width: 180px; /* 버튼 2개 넉넉히 감쌀 크기 */
        padding: 0;
    }

    .actions {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 6px;
        height: 100px;
        padding: 8px 0;
        box-sizing: border-box;
    }

    .actions button {
        flex: 1;
        max-width: 70px;
        padding: 15px 10px;
        font-size: 13px;
        white-space: nowrap;
    }

</style>

<script>
    // 상품 추가 버튼 이벤트 핸들러
    document.addEventListener("DOMContentLoaded", function() {
        // 상품 추가 버튼에 이벤트 리스너 추가
        var addButton = document.querySelector('.btn-add');
        if (addButton) {
            addButton.addEventListener('click', function() {
                openAddModal();
            });
        }

        // 모달 닫기 버튼에 이벤트 리스너 추가
        var closeButton = document.querySelector('.close');
        if (closeButton) {
            closeButton.addEventListener('click', function() {
                closeModal();
            });
        }
    });

    // 모달 열기 함수
    function openAddModal() {
        document.getElementById('productForm').reset();
        document.getElementById('modalTitle').textContent = '상품 추가';
        document.getElementById('productForm').action = '/admin/product-insert';

        // 오늘 날짜와 1년 후 날짜로 기본값 설정
        var today = new Date().toISOString().split('T')[0];
        var nextYear = new Date();
        nextYear.setFullYear(nextYear.getFullYear() + 1);
        var nextYearStr = nextYear.toISOString().split('T')[0];

        document.getElementById('dtStartDate').value = today;
        document.getElementById('dtEndDate').value = nextYearStr;

        // 상품 ID 필드 숨기기
        var noProductField = document.getElementById('noProductField');
        if(noProductField) {
            noProductField.style.display = 'none';
        }

        // 모달 표시
        var modal = document.getElementById('productModal');
        if(modal) {
            modal.style.display = 'block';
        } else {
            console.error("모달 창 요소를 찾을 수 없습니다");
        }
    }

    // 모달 닫기 함수
    function closeModal() {
        var modal = document.getElementById('productModal');
        if(modal) {
            modal.style.display = 'none';
        }
    }

    // 상품 수정 모달 열기
    function openEditModal(noProduct, nmProduct, nmDetailExplain, dtStartDate, dtEndDate, qtCustomer, qtSalePrice, qtStock, qtDeliveryFee, categoryId) {
        document.getElementById('productForm').reset();
        document.getElementById('modalTitle').textContent = '상품 수정';
        document.getElementById('noProduct').value = noProduct;
        document.getElementById('nmProduct').value = nmProduct;
        document.getElementById('nmDetailExplain').value = nmDetailExplain;
        document.getElementById('dtStartDate').value = dtStartDate;
        document.getElementById('dtEndDate').value = dtEndDate;
        document.getElementById('qtCustomer').value = qtCustomer;
        document.getElementById('qtSalePrice').value = qtSalePrice;
        document.getElementById('qtStock').value = qtStock;
        document.getElementById('qtDeliveryFee').value = qtDeliveryFee;

        // 상품 수정 모달 열기 함수 중...
        const categorySelect = document.getElementById('categorySelection');
        const categoryValue = String(categoryId);

// 해당 categoryId가 select에 존재하는지 확인
        let exists = false;
        for (let i = 0; i < categorySelect.options.length; i++) {
            if (categorySelect.options[i].value === categoryValue) {
                exists = true;
                break;
            }
        }

// 존재하면 선택, 없으면 "없음" 선택 (value="0")
        categorySelect.value = exists ? categoryValue : "0";

        document.getElementById('productForm').action = '/admin/product-update';

        // 상품 ID 필드 표시 (읽기 전용)
        document.getElementById('noProductField').style.display = 'block';
        document.getElementById('noProduct').readOnly = true;

        // 모달 표시
        document.getElementById('productModal').style.display = 'block';
    }

    let deleteProductId = null;
    let deleteCategoryId = null;

    function confirmDelete(noProduct,categoryId) {
        deleteProductId = noProduct;
        deleteCategoryId = categoryId;
        document.getElementById('deleteMessage').textContent = `정말 상품을 삭제하시겠습니까?`;
        document.getElementById('deleteConfirmModal').style.display = 'block';
    }

    function closeDeleteModal() {
        document.getElementById('deleteConfirmModal').style.display = 'none';
        deleteProductId = null;
        deleteCategoryId = null;
    }

    function submitDelete() {
        if (deleteProductId) {
            document.getElementById('deleteId').value = deleteProductId;
            document.getElementById('deleteCategoryId').value = deleteCategoryId;
            document.getElementById('deleteForm').submit();
        }
    }

    // 페이지 로드 시 모달 창 밖을 클릭하면 닫히도록 설정
    window.onclick = function(event) {
        const modal = document.getElementById('productModal');
        if (event.target === modal) {
            closeModal();
        }
    };
</script>

<%@ include file="/common/header.jsp" %>

<!-- 메인 컨텐츠 영역 -->
<div class="content-container">
    <h1 class="page-title">상품 관리</h1>

    <% if (message != null && !message.isEmpty()) { %>
    <div class="alert <%= "error".equals(messageType) ? "alert-error" : "alert-success" %>">
        <%= message %>
    </div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <h2 class="card-title">상품 목록</h2>
            <button class="btn btn-add">+ 상품 추가</button>
        </div>
        <div class="card-body">
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>상품코드</th>
                        <th>이미지</th>
                        <th>카테고리</th>
                        <th>상품명</th>
                        <th>판매가격</th>
                        <th>재고</th>
                        <th>판매상태</th>
                        <th>판매기간</th>
                        <th>배송비</th>
                        <th>관리</th>
                    </tr>
                    </thead>

                    <tbody>
                    <% if (productList != null && !productList.isEmpty()) {
                        for (ProductDisplayDto productDto : productList) {
                            Product product = productDto.getProduct();
                            // 날짜 형식 변환 (YYYYMMDD -> YYYY-MM-DD)
                            String startDate = product.getDtStartDate();
                            String endDate = product.getDtEndDate();

                            if (startDate != null && startDate.length() == 8) {
                                startDate = startDate.substring(0, 4) + "-" +
                                        startDate.substring(4, 6) + "-" +
                                        startDate.substring(6, 8);
                            }

                            if (endDate != null && endDate.length() == 8) {
                                endDate = endDate.substring(0, 4) + "-" +
                                        endDate.substring(4, 6) + "-" +
                                        endDate.substring(6, 8);
                            }

                            // 판매 상태 확인
                            LocalDate today = LocalDate.now();
                            LocalDate start = (startDate != null && !startDate.isEmpty()) ?
                                    LocalDate.parse(startDate) : null;
                            LocalDate end = (endDate != null && !endDate.isEmpty()) ?
                                    LocalDate.parse(endDate) : null;

                            boolean isSelling = (start != null && end != null) &&
                                    (!today.isBefore(start) && !today.isAfter(end));
                    %>
                    <tr>
                        <td><%= product.getNoProduct() %></td>
                        <td>
                            <img src="https://via.placeholder.com/60x60?text=<%= product.getNmProduct() %>"
                                 alt="<%= product.getNmProduct() %>" class="product-thumbnail">
                        </td>
                        <td><%= productDto.getCategoryName() %></td>
                        <td><%= product.getNmProduct() %></td>
                        <td><%= currencyFormat.format(product.getQtSalePrice()) %>원</td>
                        <td>
                            <% if (product.getQtStock() <= 0) { %>
                            <span class="stock-badge stock-out">품절</span>
                            <% } else if (product.getQtStock() < 10) { %>
                            <span class="stock-badge stock-low"><%= product.getQtStock() %>개 (부족)</span>
                            <% } else { %>
                            <span class="stock-badge stock-available"><%= product.getQtStock() %>개</span>
                            <% } %>
                        </td>
                        <td>
                            <% if (isSelling) { %>
                            <span class="status-badge status-active">판매중</span>
                            <% } else { %>
                            <span class="status-badge status-inactive">판매중지</span>
                            <% } %>
                        </td>
                        <td><%= startDate %> ~ <%= endDate %></td>
                        <td>
                            <% if (product.getQtDeliveryFee() <= 0) { %>
                            <span class="status-badge status-active">무료배송</span>
                            <% } else { %>
                            <%= currencyFormat.format(product.getQtDeliveryFee()) %>원
                            <% } %>
                        </td>
                        <td class="actions">
                            <button class="btn btn-edit" onclick="openEditModal(
                                    '<%= product.getNoProduct() %>',
                                    '<%= product.getNmProduct() %>',
                                    '<%= product.getNmDetailExplain() %>',
                                    '<%= startDate %>',
                                    '<%= endDate %>',
                                <%= product.getQtCustomer() %>,
                                <%= product.getQtSalePrice() %>,
                                <%= product.getQtStock() %>,
                                <%= product.getQtDeliveryFee() %>,
                                <%= productDto.getCategoryId() %>  <!-- 이 부분 추가 -->
                                    )">수정</button>
                            <button class="btn btn-delete"
                                    onclick="confirmDelete('<%= product.getNoProduct() %>', '<%= productDto.getCategoryId() %>')">
                                삭제
                            </button>
                        </td>
                    </tr>
                    <% }
                    } else { %>
                        <tr>
                            <td colspan="8" class="empty-state">
                                <p class="empty-text">등록된 상품이 없습니다.</p>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 상품 추가/수정 모달 -->
<div id="productModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="modalTitle" class="modal-title">상품 추가</h3>
            <span class="close">&times;</span>
        </div>
        <form id="productForm" method="post" action="/admin/product-insert">
            <div id="noProductField" class="form-group" style="display: none;">
                <label for="noProduct">상품 코드</label>
                <input type="text" id="noProduct" name="noProduct" class="form-input" readonly>
            </div>

            <!-- 카테고리 선택 드롭다운 -->
            <div class="form-group">
                <label for="categorySelection">카테고리</label>
                <select id="categorySelection" name="nbCategory" class="form-select" onchange="updateOrderFromCategory()" required>
                    <option value="">카테고리를 선택해주세요.</option> <!-- 추가된 없음 옵션 -->
                    <% if (categoryList != null && !categoryList.isEmpty()) {
                        for (Category cat : categoryList) {
                            if ("Y".equals(cat.getYnUse()) && !"Y".equals(cat.getYnDelete())) { %>
                    <option value="<%= cat.getNbCategory() %>" data-order="<%= cat.getCnOrder() %>">
                        <%= cat.getNmFullCategory() %>
                    </option>
                    <% }
                    }
                    } %>
                </select>
                <!-- 카테고리 정렬 순서를 저장할 hidden 필드 -->
                <input type="hidden" id="categoryOrder" name="cnOrder" value="1">
            </div>

            <div class="form-group">
                <label for="nmProduct">상품명</label>
                <input type="text" id="nmProduct" name="nmProduct" class="form-input"
                       placeholder="상품명을 입력하세요" required>
            </div>

            <div class="form-group">
                <label for="nmDetailExplain">상품 설명</label>
                <textarea id="nmDetailExplain" name="nmDetailExplain" class="form-input"
                          placeholder="상품에 대한 상세 설명을 입력하세요" rows="5" required></textarea>
            </div>

            <div class="form-group">
                <label for="qtCustomer">소비자 가격</label>
                <input type="number" id="qtCustomer" name="qtCustomer" class="form-input"
                       placeholder="소비자 가격을 입력하세요" min="0" required>
            </div>

            <div class="form-group">
                <label for="qtSalePrice">판매 가격</label>
                <input type="number" id="qtSalePrice" name="qtSalePrice" class="form-input"
                       placeholder="판매 가격을 입력하세요" min="0" required>
            </div>

            <div class="form-group">
                <label for="qtStock">재고 수량</label>
                <input type="number" id="qtStock" name="qtStock" class="form-input"
                       placeholder="재고 수량을 입력하세요" min="0" required>
            </div>

            <div class="form-group">
                <label for="qtDeliveryFee">배송비</label>
                <input type="number" id="qtDeliveryFee" name="qtDeliveryFee" class="form-input"
                       placeholder="배송비를 입력하세요 (무료배송은 0)" min="0" required>
            </div>

            <div class="form-group">
                <label for="dtStartDate">판매 시작일</label>
                <input type="date" id="dtStartDate" name="dtStartDate" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="dtEndDate">판매 종료일</label>
                <input type="date" id="dtEndDate" name="dtEndDate" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="productImage">상품 이미지</label>
                <input type="file" id="productImage" name="productImage" class="form-input" accept="image/*">
            </div>

            <input type="hidden" name="noRegister" value="<%= loginUser.getIdUser() %>">

            <div class="form-footer">
                <button type="button" class="btn" onclick="closeModal()">취소</button>
                <button type="submit" class="btn btn-add">저장</button>
            </div>
        </form>
    </div>
</div>


<!-- 삭제 확인 모달 -->
<div id="deleteConfirmModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">삭제 확인</h3>
            <span class="close" onclick="closeDeleteModal()">&times;</span>
        </div>
        <p id="deleteMessage">정말 삭제하시겠습니까?</p>
        <div class="form-footer">
            <button type="button" class="btn" onclick="closeDeleteModal()">취소</button>
            <button type="button" class="btn btn-delete" onclick="submitDelete()">삭제</button>
        </div>
    </div>
</div>

<!-- 숨김 삭제 폼 -->
<form id="deleteForm" method="post" action="/admin/product-delete" style="display:none;">
    <input type="hidden" id="deleteId" name="noProduct">
    <input type="hidden" id="deleteCategoryId" name="nmCategory">
</form>

<%@ include file="/common/simple_footer.jsp" %>