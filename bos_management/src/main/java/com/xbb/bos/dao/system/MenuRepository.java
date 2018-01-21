package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 后台菜单管理的dao
 * Created by xbb on 2018/1/15.
 */
public interface MenuRepository extends JpaRepository<Menu,Integer>{

    /**
     * 根据用户id查询具有菜单
     * @param id
     * @return
     */
    @Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id=? order by m.priority")
    List<Menu> findByUser(int id);
}
