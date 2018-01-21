package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.RoleRepository;
import com.xbb.bos.dao.system.UserRepository;
import com.xbb.bos.domain.system.Role;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IUserService;
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
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 根据用户名查询用户的方法
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 添加用户
     * @param user
     * @param roleIds
     */
    @Override
    public void save(User user, String[] roleIds) {
        //保存用户
        userRepository.save(user);
        //用户管联角色
        if(roleIds != null){
            for (String roleId : roleIds) {
                Role role = roleRepository.findOne(Integer.parseInt(roleId));
                user.getRoles().add(role);
            }
        }
    }
}
