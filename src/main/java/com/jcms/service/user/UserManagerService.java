package com.jcms.service.user;

import com.jcms.domain.*;
import com.jcms.repository.role.RoleRepository;
import com.jcms.repository.user.UserExtendRepository;
import com.jcms.repository.user.UserRepository;
import com.jcms.repository.user.UserRoleRepository;
import com.jcms.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserExtendRepository userExtendRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RedisUtils redisUtils;
    /**
     * 保存用户信息
     *
     * @param sysUserEntity
     */
    @Transactional(readOnly = false)
    public boolean saveUser(SysUserEntity sysUserEntity) {
        Integer userCount = userRepository.countByUserName(sysUserEntity.getUserName());
        if (sysUserEntity.getUserId() == 0){
            if(userCount > 0){
                return false;
            }else{
                sysUserEntity.setUserPassowrd(passwordEncoder.encode("666666"));
                userRepository.save(sysUserEntity);
                return true;
            }
        }else{
            if (userCount > 1){
                return false;
            }else{
                SysUserEntity userEntity = userRepository.findByUserName(sysUserEntity.getUserName());
                sysUserEntity.setUserPassowrd(userEntity.getUserPassowrd());
                sysUserEntity.setUserCreatePerson(userEntity.getUserCreatePerson());
                sysUserEntity.setUserCreateDate(userEntity.getUserCreateDate());
                userRepository.save(sysUserEntity);
                return true;
            }
        }
    }

    /**
     * 查询菜单权限
     *
     * @param userName
     * @return
     */
    @Cacheable(cacheNames = "menuList",key = "#userName")
    public List<SysAuthoritiesEntity> getMenuList(String userName) {
        List<SysAuthoritiesEntity> list = new ArrayList<SysAuthoritiesEntity>();
        list = userExtendRepository.findAuthoritiesByUserName(userName, true);
        return list;
    }

    /**
     *  查询所有数据并分页
     * @param page
     * @param pageSize
     * @return
     */
    public Page<SysUserEntity> findList(Integer page,Integer pageSize){
        Pageable pageable = new PageRequest(page,pageSize, Sort.Direction.ASC,"userId");
        return userRepository.findAll(pageable);
    }
    public SysUserEntity findOne(String userName){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity = userRepository.findByUserName(userName);
        return userEntity;
    }

    public List<SysUserEntity> findListBySuperNotPage(final SysUserEntity sysUserEntity){
        List<SysUserEntity> list = null;
        list = userRepository.findAll(new Specification<SysUserEntity>() {
            @Override
            public Predicate toPredicate(Root<SysUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (null != sysUserEntity.getKey() && !"".equals(sysUserEntity.getKey())){
                    switch (sysUserEntity.getKeyType()){
                        case 0 :
                            list.add(criteriaBuilder.or(criteriaBuilder.like(root.<String>get("userName"),"%" + sysUserEntity.getKey().trim() + "%"),criteriaBuilder.like(root.<String>get("userRealName"),"%" + sysUserEntity.getKey().trim() + "%")));
                            break;
                        case 1:
                            list.add(criteriaBuilder.like(root.<String>get("userName"),"%" + sysUserEntity.getKey().trim() + "%"));
                            break;
                        case 2:
                            list.add(criteriaBuilder.like(root.<String>get("userRealName"),"%" + sysUserEntity.getKey().trim() + "%"));
                            break;
                        default :
                            break;
                    }
                }
                if (null != sysUserEntity.getUserName() && !"".equals(sysUserEntity.getUserName())) {
//                    list.add(criteriaBuilder.equal(root.get("userName").as(String.class),sysUserEntity.getUserName()));
                    list.add(criteriaBuilder.like(root.<String>get("userName"), "%" + sysUserEntity.getUserName() + "%"));
                }
                if (null != sysUserEntity.getUserRealName() && !"".equals(sysUserEntity.getUserRealName())) {
                    list.add(criteriaBuilder.equal(root.get("userRealName").as(String.class), sysUserEntity.getUserRealName()));
                }
                if (null != sysUserEntity.getUserDeptName()  && !"".equals(sysUserEntity.getUserDeptName())) {
                    list.add(criteriaBuilder.like(root.<String>get("sysDeptEntity").<String>get("deptName"), "%" + sysUserEntity.getUserDeptName() + "%"));
                }
                if (null != sysUserEntity.getUserEnable() && -1 != sysUserEntity.getUserEnable()){
                    list.add(criteriaBuilder.equal(root.get("userEnable").as(Integer.class),sysUserEntity.getUserEnable()));
                }
                if (null != sysUserEntity.getUserDateRangeBegin() && !"".equals(sysUserEntity.getUserDateRangeBegin())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("userCreateDate").as(String.class),sysUserEntity.getUserDateRangeBegin()));
                }
                if (null != sysUserEntity.getUserDateRangeEnd() && !"".equals(sysUserEntity.getUserDateRangeEnd())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.<String>get("userCreateDate").as(String.class),sysUserEntity.getUserDateRangeEnd()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return list;
    }

    /**
     * 根据高级查询条件分页查询
     * @param sysUserEntity
     * @return
     */
    public Page<SysUserEntity> findListBySuper(final SysUserEntity sysUserEntity) {
        Pageable pageable = new PageRequest(null == sysUserEntity.getPage() ? 0 :sysUserEntity.getPage(), null == sysUserEntity.getPageSize() ? 10 :sysUserEntity.getPageSize(), Sort.Direction.ASC, "userId");
        Page<SysUserEntity> userEntityPage = userRepository.findAll(new Specification<SysUserEntity>() {
            public Predicate toPredicate(Root<SysUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (null != sysUserEntity.getKey() && !"".equals(sysUserEntity.getKey())){
                    switch (sysUserEntity.getKeyType()){
                        case 0 :
                            list.add(criteriaBuilder.or(criteriaBuilder.like(root.<String>get("userName"),"%" + sysUserEntity.getKey().trim() + "%"),criteriaBuilder.like(root.<String>get("userRealName"),"%" + sysUserEntity.getKey().trim() + "%")));
                            break;
                        case 1:
                            list.add(criteriaBuilder.like(root.<String>get("userName"),"%" + sysUserEntity.getKey().trim() + "%"));
                            break;
                        case 2:
                            list.add(criteriaBuilder.like(root.<String>get("userRealName"),"%" + sysUserEntity.getKey().trim() + "%"));
                            break;
                        default :
                            break;
                    }
                }
                if (null != sysUserEntity.getUserName() && !"".equals(sysUserEntity.getUserName())) {
//                    list.add(criteriaBuilder.equal(root.get("userName").as(String.class),sysUserEntity.getUserName()));
                    list.add(criteriaBuilder.like(root.<String>get("userName"), "%" + sysUserEntity.getUserName() + "%"));
                }
                if (null != sysUserEntity.getUserRealName() && !"".equals(sysUserEntity.getUserRealName())) {
                    list.add(criteriaBuilder.equal(root.get("userRealName").as(String.class), sysUserEntity.getUserRealName()));
                }
                if (null != sysUserEntity.getUserDeptName() && !"".equals(sysUserEntity.getUserDeptName())) {
                    list.add(criteriaBuilder.like(root.<String>get("sysDeptEntity").<String>get("deptName"), "%" + sysUserEntity.getUserDeptName() + "%"));
                }
                if (null != sysUserEntity.getUserEnable() && -1 != sysUserEntity.getUserEnable()){
                    list.add(criteriaBuilder.equal(root.get("userEnable").as(Integer.class),sysUserEntity.getUserEnable()));
                }
                if (null != sysUserEntity.getUserDateRangeBegin() && !"".equals(sysUserEntity.getUserDateRangeBegin())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("userCreateDate").as(String.class),sysUserEntity.getUserDateRangeBegin()));
                }
                if (null != sysUserEntity.getUserDateRangeEnd() && !"".equals(sysUserEntity.getUserDateRangeEnd())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.<String>get("userCreateDate").as(String.class),sysUserEntity.getUserDateRangeEnd()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return userEntityPage;
    }


    /**
     * 删除用户
     * @param userId
     */
    @Transactional(readOnly = false)
    public void deleteUser(Long userId){
        userRepository.delete(userId);
    }

    /**
     * 根据用户ID查询用户的所有角色
     * @param userId
     * @return
     */
    public List<SysUserRoleEntity> findAllByUserId(Long userId){
        return userRoleRepository.findAllByUserId(userId);
    }

    /**分配角色
     * @param roleIds
     */
    @Transactional(readOnly = false)
    public void alloRoles(Long userId,String roleIds){
        //查询出所有角色
        List<SysRoleEntity> hasRoles = new ArrayList<SysRoleEntity>();
        hasRoles = roleRepository.findAll();
        //获取到当前选择的角色ID
        String[] roleIdsArray = roleIds.split(",");
        //定义需要保存的LIST
        List<SysUserRoleEntity> saveList = new ArrayList<SysUserRoleEntity>();
        //定义需要删除的LIST
        List<SysUserRoleEntity> deleteList = new ArrayList<SysUserRoleEntity>();
        //循环所有角色，判断出哪些是新增的，哪些是删除的
        for (int i = 0;i < hasRoles.size();i++){
            boolean flag = false;
            for (int j = 0; j < roleIdsArray.length; j++){
                if(hasRoles.get(i).getRoleId() == Long.parseLong(roleIdsArray[j])){
                    flag = true;
                }
            }
            if (flag){
                Integer count = userRoleRepository.countByUserIdAndRoleId(userId,hasRoles.get(i).getRoleId());
                if (count <= 0){
                    SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                    userRoleEntity.setUserId(userId);
                    userRoleEntity.setRoleId(hasRoles.get(i).getRoleId());
                    saveList.add(userRoleEntity);
                }
            }else{
                SysUserRoleEntity resultEntity =
                        userRoleRepository.getSysUserRoleEntityByUserIdAndRoleId(userId,hasRoles.get(i).getRoleId());
                if (null != resultEntity){
                    SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                    userRoleEntity.setUrId(resultEntity.getUrId());
                    deleteList.add(userRoleEntity);
                }
            }
        }
        userRoleRepository.delete(deleteList);
        userRoleRepository.save(saveList);
    }
    @Transactional(readOnly = false)
    public void updateUserStatus(Long userId,Integer userEnable){
        SysUserEntity userEntity = userRepository.findOne(userId);
        if (userEnable == 0){
            userEntity.setUserEnable(1);
        }else{
            userEntity.setUserEnable(0);
        }

        userRepository.save(userEntity);
    }
}
