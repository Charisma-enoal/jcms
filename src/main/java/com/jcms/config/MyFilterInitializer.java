package com.jcms.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class MyFilterInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) {
//        FilterRegistration.Dynamic
    }
}
