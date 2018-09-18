package com.jcms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcConfig {
    //自动注入
//    @Autowired
//    private DataSource dataSource;


//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource){
//        return new JdbcTemplate(dataSource);
//    }


}
