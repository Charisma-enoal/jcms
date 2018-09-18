package com.jcms.web.auth;

import com.jcms.domain.AuthTreeEntity;
import com.jcms.domain.SysAuthoritiesEntity;
import com.jcms.service.authorities.AuthoritiesService;
import com.jcms.service.role.RoleService;
import com.jcms.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getAuthList(){
        return "authList";
    }

    @RequestMapping(value = "/findListForTree",method = RequestMethod.GET)
    @ResponseBody
    public List<AuthTreeEntity> findListForTree(){
        return authoritiesService.findAuthTree();
    }

    @RequestMapping(value = "/deleteAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteAuth(Long authId){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        authoritiesService.deleteByAuthId(authId);
        resultMap.put("success",true);
        return resultMap;
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(@Valid SysAuthoritiesEntity authoritiesEntity, BindingResult result){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            resultMap.put("success", false);
            String errorMsg = errorHtml(result.getAllErrors());
            resultMap.put("errorMsg", errorMsg);
        } else {
            boolean flag = authoritiesService.save(authoritiesEntity);
            resultMap.put("success", flag);
            if (!flag) {
                resultMap.put("errorMsg", errorMsg("权限编码或权限名称已存在，编码或名称不能重复"));
            }
        }
        return resultMap;
    }
}
