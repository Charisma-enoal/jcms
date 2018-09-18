package com.jcms.repository.impl.user;

import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.domain.SysUserEntity;
import com.jcms.repository.user.UserExtendRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserExtendRepository {
    @PersistenceContext
    private EntityManager em;

//    public void save(JcmsSystemUserinfoEntity jcmsSystemUserinfoEntity) {
//        if (jcmsSystemUserinfoEntity.getId() > 0){
//            em.merge(jcmsSystemUserinfoEntity);
//        }else{
//            em.persist(jcmsSystemUserinfoEntity);
//        }
//    }

    public List<SysAuthoritiesEntity> findAuthoritiesByUserName(String username,boolean onlyMenu) {
        String sql = "SELECT a.* FROM sys_authorities a \n" +
                "LEFT JOIN sys_role_authorities b ON a.authId = b.authId \n" +
                "LEFT JOIN sys_role c ON b.roleId = c.roleId AND roleEnable = 1 \n" +
                "LEFT JOIN sys_user_role d ON c.roleId = d.roleId\n" +
                "LEFT JOIN sys_user e ON d.userId = e.userId\n" +
                "WHERE e.userName = ?1";
        if (onlyMenu){
            sql += " AND a.authCode LIKE 'MENU_%'";
        }
        sql += " GROUP BY a.authCode ";
        sql += " ORDER BY a.authPriority ASC ";
        Query query = em.createNativeQuery(sql,SysAuthoritiesEntity.class);
        query.setParameter(1,username);
        List<SysAuthoritiesEntity> list = new ArrayList<SysAuthoritiesEntity>();
        list = query.getResultList();
        return list;
    }
}
