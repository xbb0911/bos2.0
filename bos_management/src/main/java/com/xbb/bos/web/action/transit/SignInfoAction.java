package com.xbb.bos.web.action.transit;

import com.xbb.bos.domain.transit.SignInfo;
import com.xbb.bos.service.transit.ISignInfoService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 签收录入管理的action
 * Created by xbb on 2018/1/18.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class SignInfoAction extends BaseAction<SignInfo>{

    @Autowired
    private ISignInfoService signInfoService;

    //属性注入transitInfo 的id
    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "sign_save",results = {@Result(name = "success",type = "redirect",
        location = "pages/transit/transitinfo.html")})
    public String save(){
        signInfoService.save(transitInfoId,model);
        return SUCCESS;
    }

}
