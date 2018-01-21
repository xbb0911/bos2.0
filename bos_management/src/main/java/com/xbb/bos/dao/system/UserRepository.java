package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 后台用户管理的dao
 * Created by xbb on 2018/1/14.
 */
public interface UserRepository extends JpaRepository<User,Integer>{

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
