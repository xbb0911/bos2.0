package com.xbb.bos.service.system.impl;

import com.xbb.bos.dao.system.MenuRepository;
import com.xbb.bos.dao.system.PermissionRepository;
import com.xbb.bos.dao.system.RoleRepository;
import com.xbb.bos.domain.system.Menu;
import com.xbb.bos.domain.system.Permission;
import com.xbb.bos.domain.system.Role;
import com.xbb.bos.domain.system.User;
import com.xbb.bos.service.system.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理的service实现类
 * Created by xbb on 2018/1/14.
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    @Override
    public List<Role> findByUser(User user) {
        //基于用户查询角色
        //admin具有所有角色
        if("admin".equals(user.getUsername())){
            return roleRepository.findAll();
        }else{
            return  roleRepository.findByUser(user.getId());
        }

    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }


    /**
     * 添加角色
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    @Override
    public void saveRole(Role role, String[] permissionIds, String menuIds) {
        //保存角色信息
        roleRepository.save(role);
        //关联权限,持久态关联游离态,自动保存到数据库中
        if(permissionIds != null){
            for (String permissionId : permissionIds) {
                //根据id查询权限
                Permission permission = permissionRepository.findOne(Integer.parseInt(permissionId));
                role.getPermissions().add(permission);
            }
        }

        //角色关联菜单
        if(StringUtils.isNotBlank(menuIds)){
            String[] menuIdArray = menuIds.split(",");
            for (String menuId : menuIdArray) {
                Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
                role.getMenus().add(menu);
            }
        }

    }
}
