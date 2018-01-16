package com.xbb.bos.dao.system;

import com.xbb.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 后台菜单管理的dao
 * Created by xbb on 2018/1/15.
 */
public interface MenuRepository extends JpaRepository<Menu,Integer>{
}
