package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.MenuRepository;
import com.xbb.bos.domain.system.Menu;
import com.xbb.bos.service.system.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台菜单管理的service接口
 * Created by xbb on 2018/1/15.
 */
@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 查询所有菜单的方法
     * @return
     */
    @Override
    public List<Menu> findAll() {

        List<Menu> menus = menuRepository.findAll();

        return menus;
    }

    /**
     * 添加菜单
     * @param menu
     */
    @Override
    public void save(Menu menu) {
        //防止用户没有选中父菜单
        if(menu.getParentMenu() != null && menu.getParentMenu().getId() == 0){
            menu.setParentMenu(null);
        }
        //调用dao
        menuRepository.save(menu);
    }
}
