package com.xbb.bos.dao.take_delivery;

import com.xbb.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单管理的dao
 * Created by xbb on 2018/1/9.
 */
public interface OrderRepository extends JpaRepository<Order,Integer>{

    /**
     * 根据订单号查询订单
     * @param orderNum
     * @return
     */
    Order findByOrderNum(String orderNum);
}
