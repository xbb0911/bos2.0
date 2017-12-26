package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.domain.base.Standard;
import com.xbb.bos.service.base.IStandardService;
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
import java.util.List;
import java.util.Map;

/**
 * 收派标准管理
 * Created by xbb on 2017/12/23.
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    //注入service
    @Autowired
    private IStandardService standardService;

    //模型驱动
    private Standard standard = new Standard();
    @Override
    public Standard getModel() {
        return standard;
    }

    //添加收派标准
    @Action(value = "standard_save",
            results = {@Result(name = "success",type = "redirect",
                    location = "./pages/base/standard.html")})
    public String save(){
        //System.out.println(standard);
        standardService.save(standard);
        return SUCCESS;
    }

    //属性驱动
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //分页列表查询
    @Action(value = "standard_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){

        //调用业务层,查询数据结果
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Standard> pageData = standardService.findPageData(pageable);

        //返回客户端数据,需要total和rows
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());

        //将map转换为json数据返回,使用struts2-json-plugin插件,会自动将栈顶的元素转换为json数据
        ActionContext.getContext().getValueStack().push(result);

        return SUCCESS;
    }

    //查询所有收派标准方法
    @Action(value = "standard_findAll",
            results = @Result(name = "success",type = "json"))
    public String findAll(){
        List<Standard> standards = standardService.findAll();
        ActionContext.getContext().getValueStack().push(standards);
        return SUCCESS;
    }


}