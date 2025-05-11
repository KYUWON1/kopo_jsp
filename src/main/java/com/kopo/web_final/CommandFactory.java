package com.kopo.web_final;

import com.kopo.web_final.category.controller.*;
import com.kopo.web_final.member.controller.*;
import com.kopo.web_final.product.controller.admin.GetProductManagementListCommand;
import com.kopo.web_final.product.controller.admin.ProductDeleteCommand;
import com.kopo.web_final.product.controller.admin.ProductInsertCommand;
import com.kopo.web_final.product.controller.admin.ProductUpdateCommand;
import com.kopo.web_final.product.controller.member.GetProductListCommand;

public class CommandFactory {
    public static Command getCommand(String action){
        if(action.equals("main")){
            return new GetProductListCommand();
        } else if(action.equals("memberJoin")){
            return new MemberJoinCommand();
        } else if(action.equals("memberLeave")){
            return new MemberLeaveCommand();
        } else if(action.equals("memberInfoAuth")){
            return new MemberInfoAuthCommand();
        } else if(action.equals("memberInfoUpdate")){
            return new MemberInfoUpdateCommand();
        } else if(action.equals("memberPasswordUpdate")){
            return new MemberPasswordUpdateCommand();
        } else if(action.equals("getMemberList")){
            return new GetMemberListCommand();
        } else if(action.equals("memberApproval")){
            return new MemberApprovalCommand();
        } else if(action.equals("memberStatusUpdate")){
            return new MemberStatusUpdateCommand();
        } else if(action.equals("login")){
            return new LoginCommand();
        } else if(action.equals("categoryInsert")){
            return new CateGoryInsertCommand();
        } else if(action.equals("categoryUpdate")){
            return new CategoryUpdateCommand();
        } else if(action.equals("categoryDelete")){
            return new CategoryDeleteCommand();
        } else if(action.equals("categoryStatusUpdate")){
            return new CategoryStatusUpdateCommand();
        } else if(action.equals("categoryManagement")){
            return new GetCategoryManagementListCommand();
        } else if(action.equals("productManagement")){
            return new GetProductManagementListCommand();
        } else if(action.equals("productInsert")){
            return new ProductInsertCommand();
        } else if(action.equals("productUpdate")){
            return new ProductUpdateCommand();
        } else if(action.equals("productDelete")){
            return new ProductDeleteCommand();
        }
        return null;
    }
}
