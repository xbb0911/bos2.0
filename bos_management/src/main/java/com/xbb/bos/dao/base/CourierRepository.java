package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 快递员dao
 * Created by xbb on 2017/12/25.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier>{

    //自定义dao修改方法
    @Query(value = "update  Courier set deltag='1' where id=?")
    @Modifying
    public void updateDeltag(Integer id);

    //还原快递员
    @Query(value = "update Courier  set deltag=null where id=?")
    @Modifying
    public void updateRestore(Integer id);
}
