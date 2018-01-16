package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 后台角色管理的dao
 * Created by xbb on 2018/1/14.
 */
public interface RoleRepository extends JpaRepository<Role,Integer>{

    /**
     * 根据用户id查询权限
     * @param id
     * @return
     */
    @Query("from Role r inner join fetch r.users u where u.id=?")
    List<Role> findByUser(int id);
}
