package com.jcms.web;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BaseController {
    public String errorHtml(List<ObjectError> errorList){
        String errorMsg = "";
        for (ObjectError error : errorList) {
            errorMsg += "<li>" + error.getDefaultMessage() + "</li>";
        }
        return errorMsg;
    }

    public String errorMsg(String msg){
        msg = "<li>" + msg + "</li>";
        return msg;
    }
}
