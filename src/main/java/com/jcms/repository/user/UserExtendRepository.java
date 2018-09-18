package com.jcms.repository.user;

import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.domain.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserExtendRepository {

    public List<SysAuthoritiesEntity> findAuthoritiesByUserName(String username,boolean onlyMenu);

}
