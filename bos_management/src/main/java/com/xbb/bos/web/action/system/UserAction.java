package com.xbb.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IUserService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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
 * 后台用户登录的action
 * Created by xbb on 2018/1/14.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @return
     */
    @Action(value = "user_login",results = {
            @Result(name = "login",type = "redirect",location = "login.html"),
            @Result(name = "success",type = "redirect",location = "index.html")})
    public String login(){
        //用户名和密码都保存在model中
        //基于shiro实现登录
        Subject subject = SecurityUtils.getSubject();
        //用户名和密码信息
        AuthenticationToken token = new UsernamePasswordToken(
                model.getUsername(),model.getPassword());

        System.out.println(token.toString());

        try {
            subject.login(token);
            //登录成功
            //将用户信息保存到Session中
            return SUCCESS;
        } catch (Exception e) {

            //捕捉错误异常,会写错误异常
            String msg = "";
            if(e instanceof UnknownAccountException){
                //用户名错误
                msg = "用户名不存在!";
            }
            if(e instanceof IncorrectCredentialsException){
                //密码错误
                msg = "密码错误!";
            }

            //登录失败,错误信息会写
            ActionContext.getContext().getValueStack().push(msg);

            e.printStackTrace();
            return LOGIN;
        }
    }

    /**
     * 用户退出
     * @return
     */
    @Action(value = "user_logout",results = {@Result(name = "success",type = "redirect",location = "login.html")})
    public String logout(){
        //基于shiro完成退出
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    /**
     * 查询所有用户
     * @return
     */
    @Action(value = "user_list",results = {@Result(name = "success",type = "json")})
    public String list(){
        //调用业务层,返回用户列表
        List<User> users = userService.findAll();
        ActionContext.getContext().getValueStack().push(users);
        return SUCCESS;
    }


    //属性注入
    private String[] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 用户添加
     * @return
     */
    @Action(value = "user_save",results = {@Result(name = "success",type = "redirect",
        location = "pages/system/userlist.html")})
    public String save(){
        //调用业务层
        userService.save(model,roleIds);
        return SUCCESS;
    }

}
