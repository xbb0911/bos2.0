package com.xbb.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.system.Menu;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IMenuService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 后台菜单管理的action
 * Created by xbb on 2018/1/15.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu>{

    @Autowired
    private IMenuService menuService;

    /**
     * 查询所有菜单
     * @return
     */
    @Action(value = "menu_list",results = @Result(name = "success",type = "json"))
    public String list(){
        //调用业务层,查询所有菜单数据
        List<Menu> menus = menuService.findAll();
        //存入值栈中
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }

    /**
     * 添加菜单
     * @return
     */
    @Action(value = "menu_save",results = {@Result(name = "success",type = "redirect",
        location = "pages/system/menu.html")})
    public String save(){
        //调用业务层,保存菜单数据
        menuService.save(model);
        return SUCCESS;
    }

    /**
     * 菜单动态显示查询
     * @return
     */
    @Action(value = "menu_showmenu",results = {@Result(name = "success",type = "json")})
    public String showmenu(){
        //调用业务层,查询当前用户具有菜单列表
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menus = menuService.findByUser(user);
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }

}
