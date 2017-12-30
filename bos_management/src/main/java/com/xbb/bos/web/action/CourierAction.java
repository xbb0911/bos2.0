package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.domain.base.Courier;
import com.xbb.bos.service.base.ICourierService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递员管理
 * Created by xbb on 2017/12/25.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{

    //模型驱动
    /*private Courier courier = new Courier();
    @Override
    public Courier getModel() {
        return courier;
    }*/

    //注入service
    @Autowired
    private ICourierService courierService;

    /**
     * 添加快递员方法
     * @return
     */
    @Action(value = "courier_save",
            results = {@Result(name = "success",location = "./pages/base/courier.html",type = "redirect")})
    public String save(){
        //System.out.println(courier);
        courierService.save(model);
        return SUCCESS;
    }

    //属性驱动接收客户端分页参数
   /* private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }*/

    /**
     * 分页查询方法,在无条件查询的基础上添加查询添加
     * @return
     */
    @Action(value = "courier_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        //封装Pageable对象
        Pageable pageable = new PageRequest(page-1,rows);

        //添加查询条件
        //根据查询条件构造Specification条件查询对象,(类似hibernate的QBC查询)
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            /**
             * 构造条件查询方法,如果返回null,代表无条件查询,
             * root:参数,获取条件表达式 name=?   age=?
             * CriteriaQuery:参数,构造简单的条件查询条件,提供where方法
             * CriteriaBuilder:参数,构造Predicate对象,构造复杂查询效果
             */
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //查询当前Root根对象Courier
                List<Predicate> list = new ArrayList<Predicate>();
                //单表查询(查询当前对象对应的数据表)
                if(StringUtils.isNotBlank(model.getCourierNum())){
                    //进行快递员工号查询
                    //courierNum = ?
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class),
                            model.getCourierNum());
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCompany())){
                    //进行所属单位查询,模糊查询
                    //company like %?%
                    Predicate p2 = cb.like(root.get("company").as(String.class),model.getCompany());
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(model.getType())){
                    //进行快递员类型查询,等值查询
                    //type = ?
                    Predicate p3 = cb.equal(root.get("type").as(String.class),model.getType());
                    list.add(p3);
                }

                //多表查询(查询当前对象关联对象对应的数据表)
                //使用Courier(Root),关联Standard
                Join<Object,Object> standardRoot = root.join("standard",JoinType.INNER);
                if(model.getStandard() != null &&
                        StringUtils.isNotBlank(model.getStandard().getName())){
                    //进行收派标准名称模糊查找
                    //standard.name like %?%
                    Predicate p4 = cb.like(standardRoot.get("name").as(String.class),
                            "%"+model.getStandard().getName()+"%");
                    list.add(p4);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };

        //调用业务层,返回page
        Page<Courier> pageData = courierService.findPageData(specification,pageable);

        /*//转换page对象的数据格式
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        //将数据压入值栈最上层
        System.out.println(result);
        ActionContext.getContext().getValueStack().push(result);*/

        //将查询结果插入到值栈中
        System.out.println(pageData.getTotalElements());
        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }

    //注入ids
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量作废快递员
     * @return
     */
    @Action(value = "courier_delBatch",results = {
            @Result(name = "success",
            location = "./pages/base/courier.html",type = "redirect")})
    public String delBatch(){
        //按逗号分割ids
        String[] idArray = ids.split(",");
        //调用业务层,批量作废
        courierService.delBatch(idArray);

        return  SUCCESS;
    }

    /**
     * 批量还原快递员
     * @return
     */
    @Action(value = "courier_restore",results = {
            @Result(name = "success",
                    location = "./pages/base/courier.html",type = "redirect")})
    public String restore(){
        //分割ids
        String[] idArray = ids.split(",");
        //调用业务层处理数据
        courierService.restore(idArray);
        return SUCCESS;
    }
}
