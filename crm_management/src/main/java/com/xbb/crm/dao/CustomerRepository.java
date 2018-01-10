package com.xbb.crm.dao;

import cn.itcast.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 客户表操作dao接口
 * Created by xbb on 2017/12/30.
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    /**
     * 查询所有未关联客户列表
     * @return
     */
    public List<Customer> findByFixedAreaIdIsNull();

    /**
     * 查询已经关联到定区的客户列表
     * @param fixedAreaId
     * @return
     */
    public List<Customer> findByFixedAreaId(String fixedAreaId);

    /**
     * 将客户关联到定区上
     * @param fixedAreaId
     * @param id
     */
    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    public void updateFixedAreaId(String fixedAreaId, Integer id);

    /**
     * 解除关联
     * @param fixedAreaId
     */
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    public void clearFixedAreaId(String fixedAreaId);

    /**
     * 根据电话号码查询用户,判断是否已经绑定成功
     * @param telephone
     * @return
     */
    Customer findByTelephone(String telephone);

    /**
     * 进行邮箱激活绑定,修改用户的type属性
     * @param telephone
     */
    @Query("update Customer set type=1 where telephone=?")
    @Modifying
    void updateType(String telephone);

    /**
     * 用户登录
     * @param telephone
     * @param password
     * @return
     */
    Customer findByTelephoneAndPassword(String telephone, String password);

    /**
     * 根据用户地址查询对象的定区编码
     * @param address
     * @return
     */
    @Query("select fixedAreaId from Customer where address=?")
    String findFixedAreaIdByAddress(String address);
}
