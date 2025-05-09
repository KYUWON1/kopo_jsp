package com.kopo.web_final;

import com.kopo.web_final.product.controller.member.GetProductListCommand;

public class CommandFactory {
    public static Command getCommand(String action){
        if(action.equals("main")){
            return new GetProductListCommand();
        }
        return null;
    }
}
