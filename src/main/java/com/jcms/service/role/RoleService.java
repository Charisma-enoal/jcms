package com.jcms.service.role;

import com.jcms.domain.SysRoleEntity;
import com.jcms.repository.role.RoleRepository;
import com.jcms.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    @Autowired
    public RoleRepository roleRepository;

    @Transactional(readOnly = false)
    public boolean saveRole(SysRoleEntity roleEntity){
        Integer roleCount = roleRepository.countByRoleNameEquals(roleEntity.getRoleName());
        if (roleCount > 0){
            return false;
        }else{
            roleRepository.save(roleEntity);
            return true;
        }
    }

    public Page<SysRoleEntity> findList(final SysRoleEntity roleEntity){
        Pageable pageable = new PageRequest(null == roleEntity.getPage() ? 0 : roleEntity.getPage(),
                null == roleEntity.getPageSize() ? 10 :roleEntity.getPageSize(),
                Sort.Direction.DESC,"roleId");
        Page<SysRoleEntity> roleEntityPage = roleRepository.findAll(new Specification<SysRoleEntity>() {
            @Override
            public Predicate toPredicate(Root<SysRoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (null != roleEntity.getKey() && !"".equals(roleEntity.getKey())){
                   list.add(criteriaBuilder.like(root.<String>get("roleName"),"%" + roleEntity.getKey().trim() + "%"));
                }
                if (null != roleEntity.getRoleEnable() && -1 != roleEntity.getRoleEnable()){
                    list.add(criteriaBuilder.equal(root.<Integer>get("roleEnable"),roleEntity.getRoleEnable()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return roleEntityPage;
    }
    @Transactional(readOnly = false)
    public void deleteRole(long roleId){
        roleRepository.delete(roleId);
    }
    @Transactional(readOnly = false)
    public void lockOrEnableRole(Long roleId){
        SysRoleEntity roleEntity = roleRepository.findOne(roleId);
        if (null != roleEntity.getRoleEnable()){
            if (roleEntity.getRoleEnable() == 1){
                roleEntity.setRoleEnable(0);
            }else{
                roleEntity.setRoleEnable(1);
            }
        }
        roleRepository.save(roleEntity);
    }

    /**
     * 查询出所有角色
     * @return
     */
    public List<SysRoleEntity> findAll(){
        return roleRepository.findAllByRoleEnableEquals(1);
    }

}
