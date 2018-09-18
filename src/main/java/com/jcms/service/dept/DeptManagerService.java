package com.jcms.service.dept;

import com.jcms.domain.DeptTreeEntity;
import com.jcms.domain.SysDeptEntity;
import com.jcms.repository.dept.DeptExtendRepository;
import com.jcms.repository.dept.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeptManagerService {
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private DeptExtendRepository deptExtendRepository;
    /**
     * 保存部门信息
     *
     * @param sysDeptEntity
     */
    @Transactional(readOnly = false)
    public boolean saveDept(SysDeptEntity sysDeptEntity) {
        Integer count = deptRepository.countByDeptCode(sysDeptEntity.getDeptCode());
        if (0 == sysDeptEntity.getDeptId()){
            if (count > 0){
                return false;
            }else{
                deptRepository.save(sysDeptEntity);
                return true;
            }
        }else{
            if (count > 1){
                return false;
            }else{
                deptRepository.save(sysDeptEntity);
                return true;
            }
        }

    }

    public List<SysDeptEntity> searchDept(String key){
        return deptExtendRepository.searchDept(key);
    }

    public List<DeptTreeEntity> findListForTree(){
        List<SysDeptEntity> deptEntityList = deptRepository.findAll();
        return convertToDeptTreeList(deptEntityList);
    }

    public List<DeptTreeEntity> findListForTreeByParentId(long deptPid){
        List<SysDeptEntity> deptEntityList = deptRepository.findAllByDeptPidEquals(deptPid);
        return convertToDeptTreeList(deptEntityList);
    }

    public List<DeptTreeEntity> convertToDeptTreeList(List<SysDeptEntity> deptEntityList){
        List<DeptTreeEntity> deptTreeEntityList = new ArrayList<DeptTreeEntity>();
        if (null != deptEntityList && deptEntityList.size() > 0){
            for (SysDeptEntity entity : deptEntityList)
            {
                DeptTreeEntity treeEntity = new DeptTreeEntity();
                treeEntity.setId(entity.getDeptId());
                treeEntity.setpId(entity.getDeptPid());
                treeEntity.setName(entity.getDeptName());
                treeEntity.setDeptRemark(entity.getDeptRemark());
                treeEntity.setDeptEnable(entity.getDeptEnable());
                treeEntity.setOpen(entity.getDeptPid() == 0 ? true : false );
                treeEntity.setCode(entity.getDeptCode());
                deptTreeEntityList.add(treeEntity);
            }
        }
        return deptTreeEntityList;
    }
    @Transactional(readOnly = false)
    public void removeDept(Long deptId){
        deptRepository.delete(deptId);
    }
    @Transactional(readOnly = false)
    public Integer countByDeptPidEquals(Long deptId){
       return deptRepository.countByDeptPidEquals(deptId);
    }
}
