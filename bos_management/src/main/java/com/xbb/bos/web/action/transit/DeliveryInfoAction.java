package com.xbb.bos.web.action.transit;

import com.xbb.bos.domain.transit.DeliveryInfo;
import com.xbb.bos.service.transit.IDeliveryInfoService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 配送信息管理的action
 * Created by xbb on 2018/1/18.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class DeliveryInfoAction extends BaseAction<DeliveryInfo>{

    @Autowired
    private IDeliveryInfoService deliveryInfoService;

    //属性注入中转信息的id
    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "delivery_save",results = {@Result(name = "success",type = "redirect",
        location = "pages/transit/transitinfo.html")})
    public String save(){
        deliveryInfoService.save(transitInfoId,model);
        return SUCCESS;
    }

}
