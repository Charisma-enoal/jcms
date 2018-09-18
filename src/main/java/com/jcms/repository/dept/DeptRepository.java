package com.jcms.repository.dept;

import com.jcms.domain.SysDeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<SysDeptEntity,Long> {
    Integer countByDeptPidEquals(long deptPid);

    List<SysDeptEntity> findAllByDeptPidEquals(long deptPid);

    Integer countByDeptCode(String deptCode);
}
