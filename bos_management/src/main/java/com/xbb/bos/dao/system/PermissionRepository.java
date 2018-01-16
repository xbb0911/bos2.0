package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 后台权限管理的dao
 * Created by xbb on 2018/1/14.
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer>{

    /**
     * 根据用户查询权限
     * @param id
     * @return
     */
    @Query("from Permission p inner join fetch p.roles r inner join fetch  r.users u where u.id=?")
    List<Permission> findByUser(int id);
}
