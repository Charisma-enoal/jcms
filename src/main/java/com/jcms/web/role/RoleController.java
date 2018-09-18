package com.jcms.web.role;

import com.jcms.domain.AuthTreeEntity;
import com.jcms.domain.SysRoleEntity;
import com.jcms.service.authorities.AuthoritiesService;
import com.jcms.service.role.RoleService;
import com.jcms.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;

    /**
     * 访问角色列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "roleList";
    }

    /**
     * 根据查询条件筛选角色
     * @param roleEntity
     * @return
     */
    @RequestMapping(value = "/findList",method = RequestMethod.GET)
    @ResponseBody
    public Page<SysRoleEntity> findList(SysRoleEntity roleEntity){
        return roleService.findList(roleEntity);
    }

    /**
     * 新增角色
     * @param roleEntity
     * @param result
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addRole(@Valid SysRoleEntity roleEntity, BindingResult result){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if (result.hasErrors()) {
            resultMap.put("success", false);
            String errorMsg = errorHtml(result.getAllErrors());
            resultMap.put("errorMsg", errorMsg);
        }else{
            boolean flag = roleService.saveRole(roleEntity);
            resultMap.put("success", flag);
            if (!flag){
                resultMap.put("errorMsg", "<li>角色名称不允许重复</li>");
            }
        }
        return resultMap;
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteRole(Long roleId){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        roleService.deleteRole(roleId);
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 禁用角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/lockOrEnableRole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> lockOrEnableRole(Long roleId){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        roleService.lockOrEnableRole(roleId);
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 分配权限的树结构
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/authTree",method = RequestMethod.GET)
    @ResponseBody
    public List<AuthTreeEntity> getAuthTreeByRoleId(Long roleId){
        return authoritiesService.getAuthByRoleId(roleId);
    }

    /**
     * 保存角色的权限
     * @param roleId
     * @param authIdStr
     * @return
     */
    @RequestMapping(value = "/saveAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> saveAuth(Long roleId,String authIdStr,String deleteIdStr){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        authoritiesService.saveAuthByRole(roleId,authIdStr,deleteIdStr);
        resultMap.put("success",true);
        return resultMap;
    }
}
