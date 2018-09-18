package com.jcms.web.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping(value = "/error403")
    public String error403(){
        return "403";
    }
}
