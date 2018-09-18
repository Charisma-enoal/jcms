package com.jcms.repository.user;

import com.jcms.domain.SysUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<SysUserRoleEntity, Long> {
    List<SysUserRoleEntity> findAllByUserId(Long userId);

    Integer countByUserIdAndRoleId(Long userId,Long roleId);

    SysUserRoleEntity getSysUserRoleEntityByUserIdAndRoleId(Long userId,Long roleId);
}
