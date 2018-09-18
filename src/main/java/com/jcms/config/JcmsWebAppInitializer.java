package com.jcms.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class JcmsWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /*将DispatcherServlet映射到 / */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    //指定中间层及数据层组件的配置类
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }
    /*指定Web组件配置类*/
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        //配置支持multipart,文件大小限制2M，请求不能超过4M
        registration.setMultipartConfig(new MultipartConfigElement("/temp/jcms/uploads",
                2097152,4194304,0));
    }

    //如果仅仅只是注册一个filter到DispatcherServlet的话，这是最简单的方法
//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[]{new MyFilter()};
//    }

}
