package com.xbb.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.take_delivery.WayBill;
import com.xbb.bos.index.WayBillIndexRepository;
import com.xbb.bos.service.take_delivery.IWayBillService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 运单管理action
 * Created by xbb on 2018/1/10.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {

    private static final Logger LOGGER = Logger.getLogger(WayBillAction.class);

    @Autowired
    private IWayBillService wayBillService;

    @Action(value = "waybill_save", results = @Result(name = "success", type = "json"))
    public String save() {
        System.out.println(model.getWayBillNum());
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            //去除没有id的order对象
            if(model.getOrder() != null && (model.getOrder().getId() == null || model.getOrder().getId() == 0)){
                model.setOrder(null);
            }

            wayBillService.save(model);
            //保存成功
            result.put("success", true);
            result.put("msg", "保存运单成功");
            LOGGER.info("保存运单成功,运单号:" + model.getWayBillNum());
        } catch (Exception e) {
            e.printStackTrace();
            //保存失败
            result.put("success", false);
            result.put("msg", "保存运单失败");
            LOGGER.info("保存运单失败,运单号:" + model.getWayBillNum());
        }
        ActionContext.getContext().getValueStack().push(result);

       // System.out.println(wayBillId);
        return SUCCESS;
    }

    /**
     * 运单信息分页查询
     * 基于索引的条件查询
     * @return
     */
    @Action(value = "waybill_pageQuery", results = @Result(name = "success", type = "json"))
    public String pageQuery() {
        //无条件排序查询
        Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        //调用业务层进行查询
        Page<WayBill> pageData =  wayBillService.findPageData(model,pageable);
        //压入值栈返回
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

    /**
     * 根据运单号查询运单
     * @return
     */
    @Action(value = "waybill_findByWayBillNum",results = @Result(name = "success",type = "json"))
    public String findByWayBillNum(){
        System.out.println(model.getWayBillNum());
        //调用业务层查询数据
        WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
        //封装数据
        Map<String, Object> result = new HashMap<String,Object>();
        if(wayBill == null){
            //运单不存在
            result.put("success",false);
        }else{
            result.put("success",true);
            result.put("wayBillData",wayBill);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }



}
