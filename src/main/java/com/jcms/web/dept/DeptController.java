package com.jcms.web.dept;

import com.jcms.domain.DeptTreeEntity;
import com.jcms.domain.SysDeptEntity;
import com.jcms.service.dept.DeptManagerService;
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
@RequestMapping(value = "/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptManagerService deptManagerService;

    @RequestMapping(value = "/searchDept",method = RequestMethod.GET)
    @ResponseBody
    public List<SysDeptEntity> searchDept(String key){
        return deptManagerService.searchDept(key);
    }
    @RequestMapping(value = "/deptList", method = RequestMethod.GET)
    public String deptList() {
        return "deptList";
    }

    @RequestMapping(value = "/findListForTree",method = RequestMethod.GET)
    @ResponseBody
    public List<DeptTreeEntity> findListForTree(){
        return deptManagerService.findListForTree();
    }

    @RequestMapping(value = "/findListForTreeByParentId",method = RequestMethod.GET)
    @ResponseBody
    public List<DeptTreeEntity> findListForTreeByParentId(long deptPid){
        return deptManagerService.findListForTreeByParentId(deptPid);
    }

    @RequestMapping(value = "/deleteDept", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDept(Long deptId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer childCount = deptManagerService.countByDeptPidEquals(deptId);
        if (childCount > 0){
            resultMap.put("success", false);
            resultMap.put("errorMsg","您选择的部门包含子部门，不能删除");
        }else{
            deptManagerService.removeDept(deptId);
            resultMap.put("success", true);
        }
        return resultMap;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveUser(@Valid SysDeptEntity sysDeptEntity, BindingResult result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            resultMap.put("success", false);
            String errorMsg = errorHtml(result.getAllErrors());
            resultMap.put("errorMsg", errorMsg);
        } else {
            boolean flag = deptManagerService.saveDept(sysDeptEntity);
            resultMap.put("success", flag);
            if (!flag) {
                resultMap.put("errorMsg", "<li>部门编码已存在，部门编码不能重复</li>");
            }
        }
        return resultMap;
    }



}
