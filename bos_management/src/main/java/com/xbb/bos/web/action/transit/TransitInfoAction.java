package com.xbb.bos.web.action.transit;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.transit.TransitInfo;
import com.xbb.bos.service.transit.ITransitInfoService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 运单中转配送管理的action
 * Created by xbb on 2018/1/17.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TransitInfoAction extends BaseAction<TransitInfo> {

    @Autowired
    private ITransitInfoService transitInfoService;

    //属性注入
    private String wayBillIds;

    public void setWayBillIds(String wayBillIds) {
        this.wayBillIds = wayBillIds;
    }

    /**
     * 生成中转配送信息
     * @return
     */
    @Action(value = "transit_create",results = {@Result(name = "success",type = "json")})
    public String create(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            //调用业务层,保存transitInfo信息
            transitInfoService.createTransits(wayBillIds);
            //成功
            result.put("success",true);
            result.put("msg","开启中转配送成功");
        } catch (Exception e) {
            e.printStackTrace();
            //失败
            result.put("success",false);
            result.put("msg","开启中转配送失败,异常"+e.getMessage());
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    /**
     * 分页查询运输配送信息
     * @return
     */
    @Action(value = "transit_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        //分页查询
        Pageable pageable = new PageRequest(page-1,rows);
        //调用业务层,查询分页数据
        Page<TransitInfo> pageData = transitInfoService.findPageData(pageable);

        //压入值栈
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

}
