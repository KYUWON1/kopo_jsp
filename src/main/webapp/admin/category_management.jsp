<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.kopo.web_final.member.model.Member" %>
<%@ page import="com.kopo.web_final.category.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kopo.web_final.utils.AuthUtils" %>
<%
    // 관리자 로그인 확인
    Member loginUser = AuthUtils.checkAdmin(request,response);
    if (loginUser == null) {
        request.setAttribute("message", "로그인이 필요한 서비스입니다.");
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
    }

    // 관리자 타입 확인 (_20)
    if (!"_20".equals(loginUser.getCdUserType())) {
        request.setAttribute("error","관리자만 접근할 수 있습니다.");
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
    }

    // 처리 결과 메시지 (추가, 수정, 삭제 후 리다이렉트 시 전달됨)
    String message = (String)request.getAttribute("message");
    String messageType = (String)request.getAttribute("type"); // success 또는 error
    
    // 임시 카테고리 데이터 (실제로는 DB에서 가져와야 함)
    List<Category> categoryList = (List<Category>)request.getAttribute("categoryList");
%>
<style>
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }
    
    /* 카테고리 관리 컨테이너 스타일 */
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

    /* 열 너비 균일화 및 정렬 */
    th:nth-child(4), td:nth-child(4) {
        width: 70px;
        text-align: center;
        white-space: nowrap;
    }

    th:nth-child(6), td:nth-child(6) {
        width: 100px;
        text-align: center;
        white-space: nowrap;
    }

    th:nth-child(7), td:nth-child(7) {
        width: 240px;
        text-align: center;
        white-space: nowrap;
    }

    /* 관리 버튼 간 간격 균일화 */
    .actions button {
        width: 70px;
        padding: 6px 10px;
        font-size: 13px;
        white-space: nowrap;
    }

    /* 상태 뱃지도 한 줄 유지 */
    .status-badge, .delete-badge, .level-badge {
        white-space: nowrap;
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
        height: 75px;
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
        overflow-y: auto; /* 세로 스크롤 추가 */
        padding: 20px 0; /* 상하 패딩 추가 */
    }
    
    .modal-content {
        background-color: white;
        margin: 3% auto; /* 상단 여백 줄임 (5%에서 3%로) */
        padding: 25px;
        width: 500px;
        max-height: 90vh; /* 최대 높이 설정 */
        border-radius: 8px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
        overflow-y: auto; /* 컨텐츠가 너무 길 경우 스크롤 */
        position: relative; /* 위치 지정 */
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

    .checkbox-group {
        display: flex;
        align-items: center;
        margin-top: 10px;
    }

    .checkbox-group input {
        margin-right: 8px;
    }

    .form-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 15px; /* 20px에서 15px로 줄임 */
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

    .delete-badge {
        background-color: #ffebee;
        color: #d32f2f;
    }

    .level-badge {
        display: inline-block;
        padding: 3px 6px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 500;
        background-color: #e3f2fd;
        color: #1976d2;
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
        min-height: 60px; /* 80px에서 60px로 줄임 */
        resize: vertical;
    }



</style>

<script>
    // 카테고리 추가 버튼 이벤트 핸들러를 직접 추가
document.addEventListener("DOMContentLoaded", function() {
    // 카테고리 추가 버튼에 이벤트 리스너 추가
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

// 모달 열기 함수 수정 - 콘솔 로그 추가
function openAddModal() {
    console.log("카테고리 추가 모달 열기");
    document.getElementById('categoryForm').reset();
    document.getElementById('modalTitle').textContent = '카테고리 추가';
    document.getElementById('categoryForm').action = 'categoryInsert.do';
    
    // 체크박스 초기화
    document.getElementById('ynUse').checked = true;
    document.getElementById('ynUseHidden').value = 'Y';
    
    // 카테고리 ID 필드 숨기기
    var nbCategoryField = document.getElementById('nbCategoryField');
    if(nbCategoryField) {
        nbCategoryField.style.display = 'none';
    }
    
    // 모달 표시
    var modal = document.getElementById('categoryModal');
    if(modal) {
        modal.style.display = 'block';
    } else {
        console.error("모달 창 요소를 찾을 수 없습니다");
    }
    
    // 레벨 초기화
    updateCategoryLevel();
}

// 모달 닫기 함수 수정
function closeModal() {
    console.log("모달 닫기");
    var modal = document.getElementById('categoryModal');
    if(modal) {
        modal.style.display = 'none';
    }
}
    // 모달 관련 함수
    function openEditModal(nbCategory, nbParentCategory, nmCategory, nmExplain, level, order, ynUse) {
        document.getElementById('categoryForm').reset();
        document.getElementById('modalTitle').textContent = '카테고리 수정';
        document.getElementById('nbCategory').value = nbCategory;
        document.getElementById('nbParentCategory').value = nbParentCategory || '';
        document.getElementById('nmCategory').value = nmCategory;
        document.getElementById('nmExplain').value = nmExplain;
        document.getElementById('cnLevel').value = level;
        document.getElementById('cnOrder').value = order;
        document.getElementById('ynUse').checked = (ynUse === 'Y');
        document.getElementById('categoryForm').action = 'categoryUpdate.do';
        document.getElementById('categoryModal').style.display = 'block';
        // 수정에서는 카테고리 번호 필드를 보이게 함 (읽기 전용)
        document.getElementById('nbCategoryField').style.display = 'block';
        document.getElementById('nbCategory').readOnly = true;

        updateCategoryLevel();

        // 상위 카테고리 옵션 필터링 (수정 중인 카테고리보다 높은 레벨만 보여줌)
        const currentLevel = parseInt(level); // 현재 수정 중인 카테고리 레벨
        const parentCategorySelect = document.getElementById('nbParentCategory');
        const categoryData = getCategoryData();

        for (let i = 0; i < parentCategorySelect.options.length; i++) {
            const option = parentCategorySelect.options[i];
            if (option.value === "") {
                option.style.display = "block"; // 최상위용은 항상 보이게
                continue;
            }

            // 옵션에 해당하는 카테고리의 레벨 찾기
            const matching = categoryData.find(cat => cat.id == option.value);
            if (matching) {
                const parentLevel = parseInt(matching.level);
                if (parentLevel >= currentLevel || parseInt(option.value) === parseInt(nbCategory)) {
                    option.style.display = "none"; // 자기 자신이거나 하위레벨이면 숨김
                } else {
                    option.style.display = "block"; // 상위 레벨이면 표시
                }
            }
        }

        // 모달 스크롤 맨 위로
        document.querySelector('.modal-content').scrollTop = 0;
        // 페이지 스크롤도 모달이 잘 보이도록 조정
        window.scrollTo(0, 0);
    }
    
    
    function confirmDelete(nbCategory, nmCategory) {
        if (confirm(nmCategory + ' 카테고리를 삭제하시겠습니까?')) {
            document.getElementById('deleteId').value = nbCategory;
            document.getElementById('deleteForm').submit();
        }
    }
    
    function toggleStatus(nbCategory, nmCategory, currentStatus) {
        const newStatus = currentStatus === 'Y' ? '비활성화' : '활성화';
        if (confirm(nmCategory + ' 카테고리를 ' + newStatus + ' 하시겠습니까?')) {
            document.getElementById('statusId').value = nbCategory;
            document.getElementById('statusActive').value = currentStatus === 'Y' ? 'N' : 'Y';
            document.getElementById('statusForm').submit();
        }
    }

    // 상위 카테고리 선택에 따라 레벨 자동 계산하는 함수 (수정)
    function updateCategoryLevel() {
        const parentCategorySelect = document.getElementById('nbParentCategory');
        const levelInput = document.getElementById('cnLevel');
        const fullCategoryInput = document.getElementById('nmFullCategory');

        let parentLevel = 0;
        let selectedValue = parentCategorySelect.value;

        if (selectedValue === "" || selectedValue === null) {
            // 상위 카테고리 없음 (최상위 카테고리)
            levelInput.value = 1;
            fullCategoryInput.value = "";
        } else {
            // 선택된 상위 카테고리의 레벨과 전체 경로 찾기
            const categoryData = getCategoryData();
            for (let i = 0; i < categoryData.length; i++) {
                if (categoryData[i].id == selectedValue) {
                    parentLevel = categoryData[i].level;
                    fullCategoryInput.value = categoryData[i].fullName;
                    break;
                }
            }

            // 부모 카테고리 레벨 + 1 (최대 3)
            const newLevel = Math.min(parentLevel + 1, 3);
            levelInput.value = newLevel;
        }

        // 카테고리명이 입력되어 있으면 전체 경로 업데이트
        const categoryNameInput = document.getElementById('nmCategory');
        if (categoryNameInput.value && fullCategoryInput.value) {
            fullCategoryInput.value = fullCategoryInput.value + " > " + categoryNameInput.value;
        } else if (categoryNameInput.value) {
            fullCategoryInput.value = categoryNameInput.value;
        }
    }

    function updateFullPath() {
        const parentCategorySelect = document.getElementById('nbParentCategory');
        const categoryNameInput = document.getElementById('nmCategory');
        const fullCategoryInput = document.getElementById('nmFullCategory');

        let selectedValue = parentCategorySelect.value;

        if (selectedValue === "" || selectedValue === null) {
            // 상위 카테고리가 없는 경우 - 자신의 이름만
            fullCategoryInput.value = categoryNameInput.value;
        } else {
            // 상위 카테고리가 있는 경우 - 상위 카테고리 전체경로 > 자신의 이름
            const categoryData = getCategoryData();
            for (let i = 0; i < categoryData.length; i++) {
                if (categoryData[i].id == selectedValue) {
                    fullCategoryInput.value = categoryData[i].fullName + " > " + categoryNameInput.value;
                    break;
                }
            }
        }
    }



    // 카테고리 데이터 반환 (JavaScript 객체 배열로) - 이미 있는 함수에 fullName 추가
    function getCategoryData() {
        return [
            <% for (Category cat : categoryList) { %>
            {
                id: <%= cat.getNbCategory() %>,
                parentId: <%= cat.getNbParentCategory() != null ? cat.getNbParentCategory() : "null" %>,
                name: "<%= cat.getNmCategory() %>",
                fullName: "<%= cat.getNmFullCategory() %>",
                level: <%= cat.getCnLevel() %>
            },
            <% } %>
        ];
    }

    function updateYnUse(checkbox) {
        document.getElementById('ynUseHidden').value = checkbox.checked ? 'Y' : 'N';
    }


    // 페이지 로드 시 모달 창 밖을 클릭하면 닫히도록 설정
    window.onclick = function(event) {
        const modal = document.getElementById('categoryModal');
        if (event.target === modal) {
            closeModal();
        }
    };
</script>

<%@ include file="/common/header.jsp" %>

<!-- 메인 컨텐츠 영역 -->
<div class="content-container">
    <h1 class="page-title">카테고리 관리</h1>
    
    <% if (message != null && !message.isEmpty()) { %>
    <div class="alert <%= "error".equals(messageType) ? "alert-error" : "alert-success" %>">
        <%= message %>
    </div>
    <% } %>
    
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">카테고리 목록</h2>
            <button class="btn btn-add">+ 카테고리 추가</button>
        </div>
        <div class="card-body">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>카테고리명</th>
                            <th>전체 경로</th>
                            <th>레벨</th>
                            <th>설명</th>
                            <th>상태</th>
                                <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (categoryList != null && !categoryList.isEmpty()) {
                            for (Category category : categoryList) {
                                if(category.getNbCategory() == 0)
                                    continue;
                        %>
                        <tr>
                            <td><%= category.getNbCategory() %></td>
                            <td><%= category.getNmCategory() %></td>
                            <td><%= category.getNmFullCategory() %></td>
                            <td><span class="level-badge">레벨 <%= category.getCnLevel() %></span></td>
                            <td><%= category.getNmExplain() %></td>
                            <td>
                                <% if ("Y".equals(category.getYnDelete())) { %>
                                    <span class="status-badge delete-badge">삭제됨</span>
                                <% } else { %>
                                    <span class="status-badge <%= "Y".equals(category.getYnUse()) ? "status-active" : "status-inactive" %>">
                                        <%= "Y".equals(category.getYnUse()) ? "활성" : "비활성" %>
                                    </span>
                                <% } %>
                            </td>
                            <td class="actions">
                                <% if ("Y".equals(category.getYnDelete())) { %>
                                <span style="color: #999;">삭제된 항목</span>
                                <% } else { %>
                                <button class="btn btn-edit" onclick="openEditModal(
                                    <%= category.getNbCategory() %>,
                                    <%= category.getNbParentCategory() == null ? "null" : category.getNbParentCategory() %>,
                                        '<%= category.getNmCategory() %>',
                                        '<%= category.getNmExplain() %>',
                                    <%= category.getCnLevel() %>,
                                    <%= category.getCnOrder() %>,
                                        '<%= category.getYnUse() %>'
                                        )">수정</button>
                                <button class="btn btn-status"
                                        onclick="toggleStatus(<%= category.getNbCategory() %>, '<%= category.getNmCategory() %>', '<%= category.getYnUse() %>')">
                                    <%= "Y".equals(category.getYnUse()) ? "비활성화" : "활성화" %>
                                </button>
                                <button class="btn btn-delete"
                                        onclick="confirmDelete(<%= category.getNbCategory() %>, '<%= category.getNmCategory() %>')">
                                    삭제
                                </button>
                                <% } %>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr>
                            <td colspan="7" class="empty-state">
                                <p class="empty-text">등록된 카테고리가 없습니다.</p>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 카테고리 추가/수정 모달 -->
<div id="categoryModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="modalTitle" class="modal-title">카테고리 추가</h3>
            <span class="close">&times;</span>
        </div>
        <form id="categoryForm" method="post" action="categoryInsert.do">
            <div id="nbCategoryField" class="form-group">
                <label for="nbCategory">카테고리 ID</label>
                <input type="number" id="nbCategory" name="nbCategory" class="form-input" readonly>
            </div>

            <div class="form-group">
                <label for="nbParentCategory">상위 카테고리</label>
                <select id="nbParentCategory" name="nbParentCategory" class="form-select" onchange="updateCategoryLevel()">
                    <option value="">없음 (최상위 카테고리)</option>
                    <% for (Category cat : categoryList) {
                        if(cat.getNbCategory() == 0)
                            continue;
                        if (cat.getCnLevel() < 3 && "Y".equals(cat.getYnUse()) && !"Y".equals(cat.getYnDelete())) { %>
                    <option value="<%= cat.getNbCategory() %>"><%= cat.getNmFullCategory() %></option>
                    <% } } %>
                </select>
            </div>

            <div class="form-group">
                <label for="nmCategory">카테고리명</label>
                <input type="text" id="nmCategory" name="nmCategory" class="form-input"
                       placeholder="카테고리 이름을 입력하세요" required onchange="updateFullPath()">
            </div>

            <div class="form-group">
                <label for="nmFullCategory">전체 경로</label>
                <input type="text" id="nmFullCategory" name="nmFullCategory" class="form-input" readonly>
            </div>

            <div class="form-group">
                <label for="nmExplain">설명</label>
                <textarea id="nmExplain" name="nmExplain" class="form-input"
                          placeholder="카테고리에 대한 설명을 입력하세요"></textarea>
            </div>

            <div class="form-group">
                <label for="cnLevel">레벨</label>
                <input type="number" id="cnLevel" name="cnLevel" class="form-input" value="1" min="1" max="3" readonly>
            </div>

            <div class="form-group">
                <label for="cnOrder">정렬 순서</label>
                <input type="number" id="cnOrder" name="cnOrder" class="form-input"
                       placeholder="정렬 순서 (숫자가 작을수록 먼저 표시)" value="1" min="1">
            </div>

            <div class="checkbox-group">
                <!-- hidden 필드 제거하고 체크박스 처리 방식 변경 -->
                <input type="checkbox" id="ynUse" onclick="updateYnUse(this)" checked>
                <input type="hidden" id="ynUseHidden" name="ynUse" value="Y">
                <label for="ynUse">카테고리 활성화</label>
            </div>



            <input type="hidden" name="noRegister" value="<%= loginUser.getIdUser() %>">

            <div class="form-footer">
                <button type="button" class="btn" onclick="closeModal()">취소</button>
                <button type="submit" class="btn btn-add">저장</button>
            </div>
        </form>
    </div>
</div>


<!-- 삭제 폼 (숨김) -->
<form id="deleteForm" method="post" action="categoryDelete.do" style="display: none;">
    <input type="hidden" id="deleteId" name="nbCategory">
</form>

<!-- 상태 변경 폼 (숨김) -->
<form id="statusForm" method="post" action="categoryStatusUpdate.do" style="display: none;">
    <input type="hidden" id="statusId" name="nbCategory">
    <input type="hidden" id="statusActive" name="ynUse">
</form>

<%@ include file="/common/simple_footer.jsp" %>