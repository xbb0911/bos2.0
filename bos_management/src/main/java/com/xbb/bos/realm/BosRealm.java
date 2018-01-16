package com.xbb.bos.realm;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.system.Permission;
import com.xbb.bos.domain.system.Role;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IPermissionService;
import com.xbb.bos.service.system.IRoleService;
import com.xbb.bos.service.system.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义Realm,实现安全数据连接
 * Created by xbb on 2018/1/14.
 */
//@Service("bosRelm")
public class BosRealm extends AuthorizingRealm{

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;


    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("shiro 授权管理...");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //根据当前登录用户查询对应角色和权限
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //调用业务层,查询角色
        List<Role> roles = roleService.findByUser(user);
        for (Role role : roles) {
            authorizationInfo.addRole(role.getKeyword());
        }
        //调用业务层查询权限
        List<Permission> permissions = permissionService.findByUser(user);
        for (Permission permission : permissions) {
            authorizationInfo.addStringPermission(permission.getKeyword());
        }
        return authorizationInfo;
    }


    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("shiro 认证管理...");

        //转换token
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        //根据用户名查询用户信息
        User user = userService.findByUsername(usernamePasswordToken.getUsername());
        if(user == null){
            //用户名不存在
            /*
                参数一:期望登录后,保存在Subject中的信息
                参数二:如果返回为null,说明用户名不存在
                参数三:realm名称
             */
            return null;
        }else {
            //用户名存在
            //当返回用户密码时,securityManager安全管理器,自动比较返回密码和用户输入密码是否一致
            //如果密码一致,登录成功,如果密码不一致,报密码错误异常
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
    }
}
