package com.xbb.bos.service.system;

import com.xbb.bos.domain.system.User;

/**
 * 后台用户管理的service接口
 * Created by xbb on 2018/1/14.
 */
public interface IUserService {

    /**
     * 根据用户名查询用户的方法
     * @param username
     * @return
     */
    User findByUsername(String username);

}
