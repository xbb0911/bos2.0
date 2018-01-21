package com.xbb.bos.service.system;

import com.xbb.bos.domain.system.Menu;
import com.xbb.bos.domain.system.User;

import java.util.List;

/**
 * 后台菜单管理的service接口
 * Created by xbb on 2018/1/15.
 */
public interface IMenuService {

    /**
     * 查询所有菜单的方法
     * @return
     */
    List<Menu> findAll();

    /**
     * 添加菜单
     * @param model
     */
    void save(Menu model);

    /**
     * 根据用户查询菜单
     * @param user
     * @return
     */
    List<Menu> findByUser(User user);
}
