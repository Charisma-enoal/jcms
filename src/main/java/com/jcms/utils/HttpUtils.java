package com.jcms.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import java.util.zip.*;

public class HttpUtils {
    public static boolean isAjaxRequest(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            return true;
        }else{
            return false;
        }
    }
}
