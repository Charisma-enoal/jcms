package com.jcms.repository.dept;

import com.jcms.domain.SysDeptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptExtendRepository {
    public List<SysDeptEntity> searchDept(String key);
}
