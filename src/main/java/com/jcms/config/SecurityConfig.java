package com.jcms.config;

import com.jcms.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import javax.sql.DataSource;
import java.net.PasswordAuthentication;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private LoginService loginService;

    @Bean
    public MyAccessDeniedHandler accessDeniedHandler(){
        MyAccessDeniedHandler accessDeniedHandler = new MyAccessDeniedHandler();
        accessDeniedHandler.setAccessDeniedUrl("/error403");
        return accessDeniedHandler;
    }

    /**
     * 忽略静态文件
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(loginService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * 配置用户认证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        通过JDBC的方式连接数据库加载用户
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("SELECT loginName,password,enable WHERE loginName = ?");
        auth.userDetailsService(loginService);
        auth.authenticationProvider(authenticationProvider());
//        auth.inMemoryAuthentication()
//                .withUser("user").password("123456").roles("USER").and()
//                .withUser("admin").password("admin").roles("ADMIN");
    }

    /**
     * url过滤
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/")
//                .and()
//                .rememberMe().tokenValiditySeconds(2419200).key("jcmsKey")
//                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/login")
//                .authenticated().anyRequest()
//                .permitAll();
        //登录页面都可以访问
//        http.authorizeRequests().antMatchers("/").permitAll()//访问登录页面不需要权限验证
//            .anyRequest().authenticated()//其他的所有路径都需要权限校验
//            .and().formLogin() //内部注册 UsernamePasswordAuthenticationFilter
//            .loginPage("/") //登录页面地址
//            .loginProcessingUrl("/login")//form表单POST请求URL提交地址
//            .passwordParameter("password")//form表单密码参数
//            .usernameParameter("username")//form表单用户名参数
//            .successForwardUrl("/success")//登录成功的跳转地址
//            .failureForwardUrl("/error")//登录失败的跳转地址
        //.defaultSuccessUrl()//如果用户没有访问受保护的页面，默认跳转到页面
        //.failureUrl()
        //.failureHandler(AuthenticationFailureHandler)
        //.successHandler(AuthenticationSuccessHandler)
        //.failureUrl("/login?error")
//            .permitAll();

        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/error403").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .failureUrl("/?error=true")
                .successForwardUrl("/login").and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("INTERNAL_SECRET_KEY")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }
    @Bean
    public RememberMeServices rememberMeServices(){
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        // 此处需要设置数据源，否则无法从数据库查询验证信息
        rememberMeTokenRepository.setDataSource(dataSource);
        // 此处的 key 可以为任意非空值(null 或 "")，单必须和起前面
        // rememberMeServices(RememberMeServices rememberMeServices).key(key)的值相同
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", loginService, rememberMeTokenRepository);
        // 该参数不是必须的，默认值为 "remember-me", 但如果设置必须和页面复选框的 name 一致
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }



}
