package com.jcms.service.user;

import com.jcms.domain.LoginUser;
import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.domain.SysUserEntity;
import com.jcms.repository.user.UserExtendRepository;
import com.jcms.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    @Autowired
    private UserExtendRepository userExtendRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    private final static Logger log = LoggerFactory.getLogger(LoginService.class);


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取到用户信息
        SysUserEntity userEntity = userRepository.findByUserName(username);
        //定义返回的UserDetails对象
        LoginUser loginUser = new LoginUser();
        if (null != userEntity) {
            loginUser.setUsername(userEntity.getUserName());
            loginUser.setPassword(userEntity.getUserPassowrd());
            loginUser.setEnabled(userEntity.getUserEnable() == 1 ? true : false);
            loginUser.setAccountNonExpired(true);
            loginUser.setAccountNonLocked(true);
            loginUser.setCredentialsNonExpired(true);

            //获取到用户的权限
            List<SysAuthoritiesEntity> list = new ArrayList<SysAuthoritiesEntity>();
            list = userExtendRepository.findAuthoritiesByUserName(username,false);

            if (null != list && list.size() > 0) {
                //将权限写入到authorities集合里
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for (SysAuthoritiesEntity entity : list) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(entity.getAuthCode());
                    authorities.add(grantedAuthority);
                    log.info(entity.getAuthCode());
                }
                loginUser.setAuthorities(authorities);
            }else{
                loginUser.setAuthorities(null);
            }

        }
        session.setAttribute("user",loginUser);
        return loginUser;
    }


}
