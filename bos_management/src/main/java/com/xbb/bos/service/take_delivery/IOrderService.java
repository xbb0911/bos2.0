package com.xbb.bos.service.take_delivery;

import com.xbb.bos.domain.take_delivery.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 订单保存service接口
 * Created by xbb on 2018/1/9.
 */
public interface IOrderService {

    /**
     * 订单保存
     * @param order
     */
    @Path("/order")
    @POST
    @Consumes({"application/xml","application/json"})
    public void saveOrder(Order order);

    /**
     * 根据订单号查询订单
     * @param orderNum
     * @return
     */
    Order findByOrderNum(String orderNum);
}
