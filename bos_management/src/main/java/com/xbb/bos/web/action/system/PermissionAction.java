package com.xbb.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.system.Permission;
import com.xbb.bos.service.system.IPermissionService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 权限管理的action
 * Created by xbb on 2018/1/15.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission>{

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询所有权限
     * @return
     */
    @Action(value = "permission_list",results = {@Result(name = "success",type = "json")})
    public String list(){
        List<Permission> permissions = permissionService.findAll();
        ActionContext.getContext().getValueStack().push(permissions);
        return SUCCESS;
    }

    /**
     * 添加权限
     * @return
     */
    @Action(value = "permission_save",results = {@Result(name = "success",type = "redirect",
        location = "pages/system/permission.html")})
    public String save(){
        permissionService.save(model);
        return SUCCESS;
    }
}
