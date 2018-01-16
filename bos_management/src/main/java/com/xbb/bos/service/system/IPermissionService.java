package com.xbb.bos.service.system;

import com.xbb.bos.domain.system.Permission;
import com.xbb.bos.domain.system.User;

import java.util.List;

/**
 * 后台用户权限管理的service接口
 * Created by xbb on 2018/1/14.
 */
public interface IPermissionService {

    /**
     * 根据用户查询权限的方法
     * @param user
     * @return
     */
    List<Permission> findByUser(User user);

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> findAll();

    /**
     * 添加权限
     * @param model
     */
    void save(Permission model);
}
