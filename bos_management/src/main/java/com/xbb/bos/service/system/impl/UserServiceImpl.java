package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.UserRepository;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 后台用户管理的service实现类
 * Created by xbb on 2018/1/14.
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据用户名查询用户的方法
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
