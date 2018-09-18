package com.jcms.repository.authorities;

import com.jcms.domain.SysAuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthoritiesRepository extends JpaRepository<SysAuthoritiesEntity,Long> {
    List<SysAuthoritiesEntity> findAllByAuthPidEqualsOrderByAuthPriorityAsc(long authId);

    Integer countByAuthCodeOrAuthNameEquals(String authCode,String authName);
    @Query("SELECT a.authId FROM SysAuthoritiesEntity a")
    List<String> authIdsLists();
}
