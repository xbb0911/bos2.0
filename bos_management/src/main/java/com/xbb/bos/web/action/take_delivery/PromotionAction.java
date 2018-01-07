package com.xbb.bos.web.action.take_delivery;

import com.xbb.bos.domain.take_delivery.Promotion;
import com.xbb.bos.service.take_delivery.IPromotionService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
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

import java.io.File;
import java.io.IOException;

/**
 * 宣传活动管理的action
 * Created by xbb on 2018/1/5.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion>{

    //上传文件属性
    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }
    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    //属性注入Promotion业务层
    @Autowired
    private IPromotionService promotionService;

    /**
     * 宣传信息添加
     * @return
     */
    @Action(value = "promotion_save",results = @Result(name = "success",type = "redirect",
            location = "./pages/take_delivery/promotion_add.html"))
    public String save() throws IOException {
        //宣传图片上传,在数据表保存宣传图路径
        String savePath = "D:\\myupload\\bos";
        //String saveUrl = "http://localhost:9001/upload/";
        String saveUrl ="/upload/";

        //生成随机图片名
        String uuid = RandomStringUtils.randomNumeric(8);
        String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
        String randomFileName = uuid + ext;

        //保存图片
        File destfile = new File(savePath,randomFileName);
        System.out.println(destfile.getAbsolutePath());
        FileUtils.copyFile(titleImgFile,destfile);

        //将保存路径 ,相对于工程的web访问路径保存到model中
        model.setTitleImg(saveUrl+randomFileName);
        //调用业务层,完成活动任务数据保存
        promotionService.save(model);
        return SUCCESS;
    }

    @Action(value = "promotion_pageQuery",results = @Result(name = "success",type = "json"))
    public String pageQuery(){
        //构造分页查询
        Pageable pageable = new PageRequest(page-1,rows);
        //调用业务层完成查询
        Page<Promotion> pageDate = promotionService.findPageDate(pageable);
        //将值压入值栈中
        pushPageDataToValueStack(pageDate);
        System.out.println(pageDate.getContent());
        System.out.println(pageDate.getTotalElements());
        return SUCCESS;
    }

}
