package com.jcms.repository.impl.dept;

import com.jcms.domain.SysDeptEntity;
import com.jcms.repository.dept.DeptExtendRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
public class DeptRepositoryImpl implements DeptExtendRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<SysDeptEntity> searchDept(String key) {
        String sql = "SELECT a.* FROM sys_dept a WHERE (a.deptCode LIKE:key OR a.deptName LIKE:key) AND a.deptEnable = 1";
        Query query = em.createNativeQuery(sql,SysDeptEntity.class);
        query.setParameter("key","%" + key +"%");
        List<SysDeptEntity> list = new ArrayList<SysDeptEntity>();
        list = query.getResultList();
        return list;
    }
}
