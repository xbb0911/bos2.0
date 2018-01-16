package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.RoleRepository;
import com.xbb.bos.domain.system.Role;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理的service实现类
 * Created by xbb on 2018/1/14.
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    @Override
    public List<Role> findByUser(User user) {
        //基于用户查询角色
        //admin具有所有角色
        if("admin".equals(user.getUsername())){
            return roleRepository.findAll();
        }else{
            return  roleRepository.findByUser(user.getId());
        }

    }
}
