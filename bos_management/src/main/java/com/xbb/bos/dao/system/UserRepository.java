package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 后台用户管理的dao
 * Created by xbb on 2018/1/14.
 */
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String username);
}
