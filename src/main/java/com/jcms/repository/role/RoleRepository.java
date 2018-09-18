package com.jcms.repository.role;

import com.jcms.domain.SysRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<SysRoleEntity, Long>,
        JpaSpecificationExecutor<SysRoleEntity> {
    Integer countByRoleNameEquals(String roleName);
    List<SysRoleEntity> findAllByRoleEnableEquals(Integer roleEnable);
}
