package com.jcms.repository.role;

import com.jcms.domain.SysRoleAuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAuthRepository extends JpaRepository<SysRoleAuthoritiesEntity, Long> {
    List<SysRoleAuthoritiesEntity> findAllByRoleId(Long roleId);
    Integer countByRoleIdAndAuthId(Long roleId,Long authId);
}
