package com.jcms.repository.role;

import com.jcms.domain.SysRoleAuthoritiesEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAuthExtendRepository {
    public void batchInsert(List<SysRoleAuthoritiesEntity> list);
    public void batchUpdate(List<SysRoleAuthoritiesEntity> list);
}
