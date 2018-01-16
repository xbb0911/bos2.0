package com.xbb.bos.service.system;

import com.xbb.bos.domain.system.Role;
import com.xbb.bos.domain.system.User;

import java.util.List;

/**
 * 后台角色管理的service接口
 * Created by xbb on 2018/1/14.
 */
public interface IRoleService {

    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    List<Role> findByUser(User user);
}
