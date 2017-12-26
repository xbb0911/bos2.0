package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.domain.base.Courier;
import com.xbb.bos.service.base.ICourierService;
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
 * 快递员管理
 * Created by xbb on 2017/12/25.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

    //模型驱动
    private Courier courier = new Courier();
    @Override
    public Courier getModel() {
        return courier;
    }

    //注入service
    @Autowired
    private ICourierService courierService;

    //添加快递员方法
    @Action(value = "courier_save",
            results = {@Result(name = "success",location = "./pages/base/courier.html",type = "redirect")})
    public String save(){
        //System.out.println(courier);
        courierService.save(courier);
        return SUCCESS;
    }

    //属性驱动接收客户端分页参数
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //分页查询方法
    @Action(value = "courier_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        //封装Pageable对象
        Pageable pageable = new PageRequest(page-1,rows);
        //调用业务层,返回page
        Page<Courier> pageData = courierService.findPageData(pageable);
        //转换page对象的数据格式
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        //将数据压入值栈最上层
        ActionContext.getContext().getValueStack().push(result);

        return SUCCESS;
    }

}
