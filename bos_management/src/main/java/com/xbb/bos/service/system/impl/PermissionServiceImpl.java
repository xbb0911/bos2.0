package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.PermissionRepository;
import com.xbb.bos.domain.system.Permission;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户管理的service实现类
 * Created by xbb on 2018/1/14.
 */
@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * 根据用户查询权限的方法
     * @param user
     * @return
     */
    @Override
    public List<Permission> findByUser(User user) {
        if("admin".equals(user.getUsername())){
            //返回所有权限
            return permissionRepository.findAll();
        }else {
            //根据用户查询权限
            return permissionRepository.findByUser(user.getId());
        }

    }

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    /**
     * 添加权限
     * @param model
     */
    @Override
    public void save(Permission model) {
        permissionRepository.save(model);
    }
}
