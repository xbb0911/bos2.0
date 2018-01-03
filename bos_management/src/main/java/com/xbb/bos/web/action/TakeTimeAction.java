package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.base.TakeTime;
import com.xbb.bos.service.base.ITakeTimeService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 收派时间管理Action
 * Created by xbb on 2018/1/2.
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime>{

    @Autowired
    private ITakeTimeService takeTimeService;

    /**
     * 查询所有收派时间
     * @return
     */
    @Action(value = "taketime_findAll",results = @Result(name = "success",type = "json"))
    public String findAll(){
        //调用业务层,查询所有收派时间
        List<TakeTime> takeTimes = takeTimeService.findAll();
        //将列表压入值栈
        ActionContext.getContext().getValueStack().push(takeTimes);
        return SUCCESS;
    }

}
