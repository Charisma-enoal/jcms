package com.jcms.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.jcms.repository",
        repositoryImplementationPostfix = "Impl") //扫描此包下扩展了JpaRepository的所有接口
@EnableJpaAuditing
public class JpaConfiguration {
    /**
     * 配置容器管理的EntityManager
     * @param dataSource
     * @param jpaVendorAdapter
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan("com.jcms.domain");
        return emfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource,JpaVendorAdapter jpaVendorAdapter) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource,jpaVendorAdapter).getObject());
        return transactionManager;
    }

    /**
     * hibernate厂商
     * @return
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return adapter;
    }

    /**
     * 将以Repository注解的类所报的异常统一为Spring异常
     * @return
     */
    @Bean
    public BeanPostProcessor persistsistenceTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    @Bean
    public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
