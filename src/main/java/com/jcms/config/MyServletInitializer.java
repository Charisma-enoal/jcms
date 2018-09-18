package com.jcms.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyServletInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) {
//        ServletRegistration.Dynamic myServlet = servletContext.addServlet("myServlet",MyServlet.class);
//        myServlet.addMapping("/custom/**");
    }
}
