package com.kopo.web_final;

import com.kopo.web_final.domain.basket.command.*;
import com.kopo.web_final.domain.category.command.*;
import com.kopo.web_final.domain.member.command.*;
import com.kopo.web_final.domain.order.command.*;
import com.kopo.web_final.domain.order.command.admin.GetOrderDetailManagementCommand;
import com.kopo.web_final.domain.order.command.admin.GetOrderManagementCommand;
import com.kopo.web_final.domain.product.command.admin.*;
import com.kopo.web_final.domain.product.command.member.*;

public class CommandFactory {
    public static Command getCommand(String action) {
        return switch (action) {
            // 상품 메뉴
            case "main" -> new GetProductListCommand();
            case "productListSort" -> new SortProductListCommand();
            case "getImage" -> new GetProductImageCommand();
            case "productDetail" -> new GetProductDetailCommand();
            // 상품 및 장바구니 관리 메뉴
            case "productOrderForm" -> new ProductOrderFormCommand();
            case "submitOrder" -> new SubmitProductOrderCommand();
            case "submitBasket" -> new BasketOrderFormCommand();
            case "submitBasketOrder" -> new SubmitBasketOrderCommand();
            case "getBasket" -> new InsertOrGetBasketCommand();
            case "getOrder" -> new GetOrderCommand();
            case "getOrderDetail" -> new GetOrderDetailCommand();
            case "insertBasketItem" -> new InsertBasketItemCommand();
            case "deleteBasketItem" -> new DeleteBasketItemCommand();
            case "emptyBasket" -> new EmptyBasketCommand();
            // 회원관리 메뉴
            case "memberJoin" -> new MemberJoinCommand();
            case "memberLeave" -> new MemberLeaveCommand();
            case "memberInfoAuth" -> new MemberInfoAuthCommand();
            case "memberInfoUpdate" -> new MemberInfoUpdateCommand();
            case "memberPasswordUpdate" -> new MemberPasswordUpdateCommand();
            case "login" -> new LoginCommand();
            // 관리자 메뉴
            case "memberManagement" -> new MemberManagementCommand();
            case "memberApproval" -> new MemberApprovalCommand();
            case "memberStatusUpdate" -> new MemberStatusUpdateCommand();
            case "categoryInsert" -> new CateGoryInsertCommand();
            case "categoryUpdate" -> new CategoryUpdateCommand();
            case "categoryDelete" -> new CategoryDeleteCommand();
            case "categoryStatusUpdate" -> new CategoryStatusUpdateCommand();
            case "categoryManagement" -> new GetCategoryManagementListCommand();
            case "productManagement" -> new GetProductManagementListCommand();
            case "productInsert" -> new ProductInsertCommand();
            case "productUpdate" -> new ProductUpdateCommand();
            case "productDelete" -> new ProductDeleteCommand();
            case "orderManagement" -> new GetOrderManagementCommand();
            case "productSoldOut" -> new SetSoldOutCommand();
            case "orderDetailManagement" -> new GetOrderDetailManagementCommand();

            default -> null;
        };
    }
}
