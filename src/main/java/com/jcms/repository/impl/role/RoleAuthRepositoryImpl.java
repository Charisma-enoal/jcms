package com.jcms.repository.impl.role;

import com.jcms.domain.SysRoleAuthoritiesEntity;
import com.jcms.repository.role.RoleAuthExtendRepository;
import com.jcms.repository.user.UserExtendRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
@Transactional
public class RoleAuthRepositoryImpl implements RoleAuthExtendRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void batchInsert(List<SysRoleAuthoritiesEntity> list) {
        for (int i = 0; i < list.size();i++){
            em.persist(list.get(i));
            if (i % 30 == 0){
                em.flush();
                em.clear();
            }
        }
    }
    @Override
    public void batchUpdate(List<SysRoleAuthoritiesEntity> list) {
        for (int i = 0; i < list.size();i++){
            em.merge(list.get(i));
            if (i % 30 == 0){
                em.flush();
                em.clear();
            }
        }
    }
}
