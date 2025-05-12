package com.kopo.web_final;

import com.kopo.web_final.basket.command.DeleteBasketItemCommand;
import com.kopo.web_final.basket.command.InsertBasketItemCommand;
import com.kopo.web_final.basket.command.InsertOrGetBasketCommand;
import com.kopo.web_final.category.command.*;
import com.kopo.web_final.member.command.*;
import com.kopo.web_final.order.command.OrderInsertCommand;
import com.kopo.web_final.order.command.ProductOrderFormCommand;
import com.kopo.web_final.product.command.admin.GetProductManagementListCommand;
import com.kopo.web_final.product.command.admin.ProductDeleteCommand;
import com.kopo.web_final.product.command.admin.ProductInsertCommand;
import com.kopo.web_final.product.command.admin.ProductUpdateCommand;
import com.kopo.web_final.product.command.member.*;

public class CommandFactory {
    public static Command getCommand(String action) {
        return switch (action) {
            case "main" -> new GetProductListCommand();
            case "productListSort" -> new SortProductListCommand();
            case "getImage" -> new GetProductImageCommand();
            case "productDetail" -> new GetProductDetailCommand();
            case "productOrderForm" -> new ProductOrderFormCommand();
            case "submitOrder" -> new OrderInsertCommand();
            case "getBasket" -> new InsertOrGetBasketCommand();
            case "insertBasketItem" -> new InsertBasketItemCommand();
            case "deleteBasketItem" -> new DeleteBasketItemCommand();
            case "memberJoin" -> new MemberJoinCommand();
            case "memberLeave" -> new MemberLeaveCommand();
            case "memberInfoAuth" -> new MemberInfoAuthCommand();
            case "memberInfoUpdate" -> new MemberInfoUpdateCommand();
            case "memberPasswordUpdate" -> new MemberPasswordUpdateCommand();
            case "memberManagement" -> new MemberManagementCommand();
            case "memberApproval" -> new MemberApprovalCommand();
            case "memberStatusUpdate" -> new MemberStatusUpdateCommand();
            case "login" -> new LoginCommand();
            case "categoryInsert" -> new CateGoryInsertCommand();
            case "categoryUpdate" -> new CategoryUpdateCommand();
            case "categoryDelete" -> new CategoryDeleteCommand();
            case "categoryStatusUpdate" -> new CategoryStatusUpdateCommand();
            case "categoryManagement" -> new GetCategoryManagementListCommand();
            case "productManagement" -> new GetProductManagementListCommand();
            case "productInsert" -> new ProductInsertCommand();
            case "productUpdate" -> new ProductUpdateCommand();
            case "productDelete" -> new ProductDeleteCommand();
            default -> null;
        };
    }
}
