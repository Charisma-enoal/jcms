package com.jcms.web.user;

import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.domain.SysRoleEntity;
import com.jcms.domain.SysUserEntity;
import com.jcms.domain.SysUserRoleEntity;
import com.jcms.service.role.RoleService;
import com.jcms.service.user.UserManagerService;
import com.jcms.utils.ExcelUtil;
import com.jcms.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "/getMenuByUsername", method = RequestMethod.GET)
    @ResponseBody
    public List<SysAuthoritiesEntity> getMenuByUsername(String userName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<SysAuthoritiesEntity> list = new ArrayList<SysAuthoritiesEntity>();
        list = userManagerService.getMenuList(auth.getName());
        return list;
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }

    @RequestMapping(value = "/findListBySuper", method = RequestMethod.GET)
    @ResponseBody
    public Page<SysUserEntity> findListBySuper(SysUserEntity entity) {
        return userManagerService.findListBySuper(entity);
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @ResponseBody
    public Page<SysUserEntity> findList(Integer page, Integer pageSize) {
        return userManagerService.findList(page, pageSize);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveUser(@Valid SysUserEntity sysUserEntity, BindingResult result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            resultMap.put("success", false);
            String errorMsg = errorHtml(result.getAllErrors());
            resultMap.put("errorMsg", errorMsg);
        } else {
            boolean flag = userManagerService.saveUser(sysUserEntity);
            resultMap.put("success", flag);
            if (!flag) {
                resultMap.put("errorMsg", "<li>用户名已存在</li>");
            }
        }
        return resultMap;
    }

    /**
     * 根据用户名获取到用户信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/initUser", method = RequestMethod.GET)
    @ResponseBody
    public SysUserEntity initUser(String userName) {
        return userManagerService.findOne(userName);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteUser(Long userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        userManagerService.deleteUser(userId);
        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(SysUserEntity sysUserEntity,HttpServletRequest request,
                            HttpServletResponse response) {
        String fileName = "用户列表";

        List<SysUserEntity> userEntityList = userManagerService.findListBySuperNotPage(sysUserEntity);

        ExcelUtil.exportExcel(fileName, userEntityList, SysUserEntity.class, request, response);
    }
    @RequestMapping(value = "/initRoles",method = RequestMethod.GET)
    @ResponseBody
    public List<SysRoleEntity> initRoles(){
        return roleService.findAll();
    }
    @RequestMapping(value = "/initRolesByUserId",method = RequestMethod.GET)
    @ResponseBody
    public List<SysUserRoleEntity> initRolesByUserId(Long userId){
        return userManagerService.findAllByUserId(userId);
    }
    @RequestMapping(value = "/alloRoles",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> alloRoles(Long userId,String roleIds) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean success = true;
        if (StringUtils.isNotEmpty(roleIds) && roleIds.split(",").length > 0) {
            userManagerService.alloRoles(userId,roleIds);
        } else {
            success = false;
            resultMap.put("errorMsg", "请选择角色");
        }
        resultMap.put("success",success);
        return resultMap;
    }

    /**
     * 禁用或启用用户
     * @param userId
     * @param userEnable
     * @return
     */
    @RequestMapping(value = "/updateUserStatus",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateUserStatus(Long userId,Integer userEnable){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean success = true;
        userManagerService.updateUserStatus(userId,userEnable);
        resultMap.put("success",success);
        return resultMap;
    }
}
