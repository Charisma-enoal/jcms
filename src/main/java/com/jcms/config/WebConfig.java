package com.jcms.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan("com.jcms.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public TilesConfigurer tilesConfigurer(){
        TilesConfigurer tiles = new TilesConfigurer();
        tiles.setDefinitions(new String[]{
                "/WEB-INF/views/layout/tiles.xml"
        });
        tiles.setCheckRefresh(true);
        return tiles;
    }

    //配置视图解析器

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
//        resourceViewResolver.setPrefix("/WEB-INF/views/");
//        resourceViewResolver.setSuffix(".jsp");
//        resourceViewResolver.setExposeContextBeansAsAttributes(true);
//        resourceViewResolver.setOrder(2);
//        return resourceViewResolver;
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setOrder(1);
        registry.viewResolver(tilesViewResolver);

//        registry.viewResolver(viewResolver());
//        super.configureViewResolvers();
    }

    //配置静态资源的处理
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 以StandardServletMultipartResolver的形式实现解析multipart请求数据
     *
     * @return
     * @throws IOException
     */
    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        return new StandardServletMultipartResolver();
    }


    /**
     * Servlet3.0以下需要这样解析multipart请求的数据
     *
     * @return
     */
//    public MultipartResolver multipartResolver() throws IOException {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setUploadTempDir(new FileSystemResource("/temp/jcms/uploads"));
//        multipartResolver.setMaxUploadSize(2097152);
//        multipartResolver.setMaxInMemorySize(0);
//        return multipartResolver;
//    }
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("message_zh_CN");
//        source.setBasenames("localization/validation");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setWriteAcceptCharset(false);
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.TEXT_HTML);
        list.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(list);
        return converter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.TEXT_HTML);
        list.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(list);
        return converter;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
