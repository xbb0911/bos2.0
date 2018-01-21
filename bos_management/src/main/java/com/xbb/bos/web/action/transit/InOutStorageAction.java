package com.xbb.bos.web.action.transit;

import com.xbb.bos.domain.transit.InOutStorageInfo;
import com.xbb.bos.service.transit.InOutStorageInfoService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 出入库信息管理的Action
 * Created by xbb on 2018/1/17.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class InOutStorageAction extends BaseAction<InOutStorageInfo>{

    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;

    private String transitInfoId;
    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }


    /**
     * 出入库信息添加
     * @return
     */
    @Action(value = "inoutstore_save",results = {@Result(name = "success",
            type = "redirect",location = "pages/transit/transitinfo.html")})
    public String save(){
        inOutStorageInfoService.save(transitInfoId,model);
        return SUCCESS;
    }

}
