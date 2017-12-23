package com.xbb.bos.web.action;

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
import org.springframework.stereotype.Controller;

/**
 * 收派标准管理
 * Created by xbb on 2017/12/23.
 */
@Namespace("/")
@ParentPackage("struts-default")
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

    @Action(value = "standard_save",
            results = {@Result(name = "success",type = "redirect",
                    location = "./pages/base/standard.html")})
    public String save(){
        System.out.println(standard);
        standardService.save(standard);
        return SUCCESS;
    }
}
