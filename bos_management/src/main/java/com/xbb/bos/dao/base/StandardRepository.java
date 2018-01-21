package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 收派标准管理dao
 * Created by xbb on 2017/12/23.
 */
public interface StandardRepository extends JpaRepository<Standard,Integer>{

    //1.根据收派标准名称查询
    //基于一列等值查询
    public List<Standard> findByName(String name);
    //基于一列模糊查询
    public List<Standard> findByNameLike(String name);
    //基于两列等值查询findByUsernameAndPassword(String username,String password)

    //2.不按命名规则
    @Query(value = "from Standard where name = ?",nativeQuery = false)
    // nativeQuery 为 false 配置JPQL 、 为true 配置SQL
    public List<Standard> queryName(String name);

    //2.不按命名规则查询,配置@query没有查语句,在实体类中用@NamedQuery定义
    @Query
    public List<Standard> queryName2(String name);

    //带有条件的修改和删除操作
    @Query(value = "update Standard set minLength=?2 where id = ?1")
    @Modifying
    public void updataMinLength(Integer id, Integer minLength);


}
