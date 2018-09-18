package com.jcms.repository.user;

import com.jcms.domain.SysUserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<SysUserEntity, Long>,
        JpaSpecificationExecutor<SysUserEntity> {

    SysUserEntity findByUserName(String userName);

    Integer countByUserName(String userName);

    SysUserEntity findByUserNameAndUserPassowrd(String userName, String userPassword);

//    @Modifying
//    @Query("update SysUserEntity u set u.userRealName = ?1,u.userDept = ?2,u.userLastEditDate = current_timestamp ,u.userLastEditPerson = ?3 where u.userId = ?4")
//    Integer updateUser(String userRealName,String userDept,String userLastEditPerson,Long userId);


}
