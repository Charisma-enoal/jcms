package com.jcms.service.authorities;

import com.jcms.domain.AuthTreeEntity;
import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.domain.SysRoleAuthoritiesEntity;
import com.jcms.repository.authorities.AuthoritiesRepository;
import com.jcms.repository.role.RoleAuthExtendRepository;
import com.jcms.repository.role.RoleAuthRepository;
import com.jcms.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthoritiesService {
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private RoleAuthRepository roleAuthRepository;
    @Autowired
    private RoleAuthExtendRepository roleAuthExtendRepository;

    @Autowired
    private RedisUtils redisUtils;
    /**
     * 保存角色权限
     * @param roleId
     * @param authIdStr
     */
    @Transactional(readOnly = false)
    public void saveAuthByRole(Long roleId,String authIdStr,String deleteIdStr){
        //将权限字符串转换为数组
        String[] authIdArr = authIdStr.split(",");
        String[] deleteIdArr = null;
        if (StringUtils.isNotEmpty(deleteIdStr)){
            deleteIdArr = deleteIdStr.split(",");
        }


        //定义需要删除的权限集合
        List<SysRoleAuthoritiesEntity> deleteList = new ArrayList<SysRoleAuthoritiesEntity>();
        if (null != deleteIdArr && deleteIdArr.length > 0)
        {
            //循环需要删除的权限
            for (String deleteId:deleteIdArr) {
                SysRoleAuthoritiesEntity deleteRoleAuthoritiesEntity = new SysRoleAuthoritiesEntity();
                deleteRoleAuthoritiesEntity.setRaId(Long.parseLong(deleteId));
                deleteList.add(deleteRoleAuthoritiesEntity);
            }
        }

        //定义角色权限映射实体的集合
        List<SysRoleAuthoritiesEntity> list = new ArrayList<SysRoleAuthoritiesEntity>();
        //循环权限，并插入到角色权限映射表
        for (String authId:authIdArr) {
            Integer count = roleAuthRepository.countByRoleIdAndAuthId(roleId,Long.parseLong(authId));
            if (count > 0){
                continue;
            }else{
                //实例化映射表实体
                SysRoleAuthoritiesEntity roleAuthoritiesEntity = new SysRoleAuthoritiesEntity();
                roleAuthoritiesEntity.setRoleId(roleId);
                roleAuthoritiesEntity.setAuthId(Long.parseLong(authId));
                list.add(roleAuthoritiesEntity);
            }
        }
        if (null != deleteList && deleteList.size() > 0){
            roleAuthRepository.delete(deleteList);
        }
        roleAuthRepository.save(list);
    }
    /**
     * 权限树形结果数据
     *
     * @return
     */
    @Cacheable(cacheNames = "authTree",key = "'authTree'")
    public List<AuthTreeEntity> findAuthTree() {
        //获取到所有的模块
        List<SysAuthoritiesEntity> authoritiesEntityList =
                authoritiesRepository.findAllByAuthPidEqualsOrderByAuthPriorityAsc(1);
        //实例化树形结构List
        List<AuthTreeEntity> treeList = new ArrayList<AuthTreeEntity>();

        //实例化根目录
        AuthTreeEntity rootEntity = new AuthTreeEntity();
        rootEntity.setId(1);
        rootEntity.setpId(0);
        rootEntity.setOpen(true);
        rootEntity.setName("权限集合");
        rootEntity.setCode("MEMU_ROOT");
        rootEntity.setAuthIsOption(0);
        treeList.add(rootEntity);
        //循环所有模块
        if (null != authoritiesEntityList && authoritiesEntityList.size() > 0) {
            for (SysAuthoritiesEntity entity : authoritiesEntityList) {
                //将1级Note设计为模块
                AuthTreeEntity treeEntity = new AuthTreeEntity();
                treeEntity.setId(entity.getAuthId());
                treeEntity.setpId(entity.getAuthPid());
                treeEntity.setCode(entity.getAuthCode());
                treeEntity.setName(entity.getAuthName());
                treeEntity.setAuthIsOption(entity.getAuthIsOption());
                treeEntity.setAuthIcon(entity.getAuthIcon());
                treeEntity.setAuthPriority(entity.getAuthPriority());
                treeEntity.setAuthUrl(entity.getAuthUrl());
                treeEntity.setAuthRemark(entity.getAuthRemark());
                treeEntity.setOpen(true);
                treeList.add(treeEntity);

                //根据模块的ID查询所有的子菜单
                List<SysAuthoritiesEntity> secondList =
                        authoritiesRepository.findAllByAuthPidEqualsOrderByAuthPriorityAsc(entity.getAuthId());
                //循环子菜单并添加到树形结果中
                if (null != secondList && secondList.size() > 0){
                    for (SysAuthoritiesEntity secondEntity: secondList) {
                        AuthTreeEntity secondTreeEntity = new AuthTreeEntity();
                        secondTreeEntity.setId(secondEntity.getAuthId());
                        secondTreeEntity.setpId(secondEntity.getAuthPid());
                        secondTreeEntity.setCode(secondEntity.getAuthCode());
                        secondTreeEntity.setName(secondEntity.getAuthName());
                        secondTreeEntity.setAuthIsOption(secondEntity.getAuthIsOption());
                        secondTreeEntity.setAuthIcon(secondEntity.getAuthIcon());
                        secondTreeEntity.setAuthPriority(secondEntity.getAuthPriority());
                        secondTreeEntity.setAuthUrl(secondEntity.getAuthUrl());
                        secondTreeEntity.setAuthRemark(secondEntity.getAuthRemark());
                        secondTreeEntity.setOpen(true);
                        treeList.add(secondTreeEntity);

                        //根据菜单的ID查询所有的操作权限
                        List<SysAuthoritiesEntity> thirdList =
                                authoritiesRepository.findAllByAuthPidEqualsOrderByAuthPriorityAsc(secondEntity.getAuthId());
                        //循环操作权限，并添加到树形结构中
                        if (null != thirdList && thirdList.size() > 0){
                            for (SysAuthoritiesEntity thirdEntity: thirdList) {
                                AuthTreeEntity thirdTreeEntity = new AuthTreeEntity();
                                thirdTreeEntity.setId(thirdEntity.getAuthId());
                                thirdTreeEntity.setpId(thirdEntity.getAuthPid());
                                thirdTreeEntity.setCode(thirdEntity.getAuthCode());
                                thirdTreeEntity.setName(thirdEntity.getAuthName());
                                thirdTreeEntity.setAuthIsOption(thirdEntity.getAuthIsOption());
                                thirdTreeEntity.setAuthIcon(thirdEntity.getAuthIcon());
                                thirdTreeEntity.setAuthPriority(thirdEntity.getAuthPriority());
                                thirdTreeEntity.setAuthUrl(thirdEntity.getAuthUrl());
                                thirdTreeEntity.setAuthRemark(thirdEntity.getAuthRemark());
                                thirdTreeEntity.setOpen(false);
                                treeList.add(thirdTreeEntity);
                            }
                        }
                    }
                }
            }
        }
        return treeList;
    }


    /**
     * 根据角色查询角色所拥有的权限
     * @param roleId
     * @return
     */
    public List<AuthTreeEntity> getAuthByRoleId(long roleId){
        //获取到该角色的所有权限
        List<SysRoleAuthoritiesEntity> roleAuthoritiesEntityList =
                roleAuthRepository.findAllByRoleId(roleId);
        //获取到所有权限的tree结构
        List<AuthTreeEntity> authTreeEntityList = findAuthTree();
        //循环所有权限，如果该角色拥有该权限，那么就将该权限设置为选中
        if (null != authTreeEntityList && authTreeEntityList.size() > 0){
            for (AuthTreeEntity entity:authTreeEntityList) {
                for (SysRoleAuthoritiesEntity roleEntity:roleAuthoritiesEntityList) {
                    if (roleEntity.getAuthId() == entity.getId()){
                        entity.setRaId(roleEntity.getRaId());
                        entity.setChecked(true);
                    }
                }
            }
        }
        return authTreeEntityList;
    }

    /**
     * 根据权限ID删除权限
     * @param authId
     */
    @Transactional(readOnly = false)
    public void deleteByAuthId(Long authId){
        authoritiesRepository.delete(authId);
    }

    /**
     * 新增或编辑权限
     * @param authoritiesEntity
     */
    @Transactional(readOnly = false)
    public boolean save(SysAuthoritiesEntity authoritiesEntity){
        //根据权限编码或权限名称查询是否存在
        Integer count = authoritiesRepository.countByAuthCodeOrAuthNameEquals(authoritiesEntity.getAuthCode(),authoritiesEntity.getAuthName());
        //返回结果
        boolean flag = true;
        //新增
        if (authoritiesEntity.getAuthId() == 0){
            if (count > 0){
                flag = false;
            }else{
                authoritiesRepository.save(authoritiesEntity);
            }
        }
        //修改
        else{
            if (count > 1){
                flag = false;
            }else{
                authoritiesRepository.save(authoritiesEntity);
            }
        }
        return flag;
    }
}
