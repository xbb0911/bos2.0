package com.xbb.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.take_delivery.Order;
import com.xbb.bos.service.take_delivery.IOrderService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import static com.opensymphony.xwork2.Action.SUCCESS;


/**
 * 订单管理action
 * Created by xbb on 2018/1/10.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{

    @Autowired
    private IOrderService orderService;

    /**
     * 根据订单号查询订单
     * @return
     */
    @Action(value = "order_findByOrderNum",results = @Result(name = "success",type = "json"))
    public String findByOrderNum(){
        System.out.println(model.getOrderNum());
        //调用业务成,查询order的信息
        Order order = orderService.findByOrderNum(model.getOrderNum());
        Map<String,Object> result = new HashMap<String,Object>();
        if(order == null){
            //不存在
            result.put("success",false);
        }else {
            result.put("success",true);
            result.put("orderData",order);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}
