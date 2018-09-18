package com.jcms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class UserIDAuditorBean implements AuditorAware<String> {

    public String getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (null == ctx){
            return null;
        }
        if (ctx.getAuthentication() == null) {
            return null;
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return null;
        }
        UserDetails principal = (UserDetails)ctx.getAuthentication().getPrincipal();
        return principal.getUsername();
    }

}
