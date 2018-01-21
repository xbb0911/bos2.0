package com.xbb.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.system.Role;
import com.xbb.bos.service.system.IRoleService;
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
 * 角色管理的action
 * Created by xbb on 2018/1/16.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

    @Autowired
    private IRoleService roleService;

    /**
     * 查询所有角色的方法
     * @return
     */
    @Action(value = "role_list",results = {@Result(name = "success",type = "json")})
    public String list(){
        //调用业务层,查询所有角色
        List<Role> roles = roleService.findAll();
        ActionContext.getContext().getValueStack().push(roles);
        return SUCCESS;
    }

    //属性注入
    private String[] permissionIds;
    private String menuIds;

    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    /**
     * 添加角色
     * @return
     */
    @Action(value = "role_save",results = {@Result(name = "success",
            type = "redirect",location = "pages/system/role.html")})
    public String save(){
        //调用业务层,保存角色
        roleService.saveRole(model,permissionIds,menuIds);
        return SUCCESS;
    }

}
