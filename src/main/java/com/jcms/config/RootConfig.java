package com.jcms.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.jcms"},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
})
public class RootConfig {
}
