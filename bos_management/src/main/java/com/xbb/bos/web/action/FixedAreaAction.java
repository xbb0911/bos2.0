package com.xbb.bos.web.action;

import cn.itcast.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.domain.base.FixedArea;
import com.xbb.bos.service.base.IFixedAreaService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 定区管理的action
 * Created by xbb on 2017/12/28.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{

    //注入service
    @Autowired
    private IFixedAreaService fixedAreaService;

    /**
     * 定区查询所有的方法
     * @return
     */
    @Action(value = "fixedArea_findAll",results = @Result(name = "success",type = "json"))
    public String findAll(){
        //调用业务层进行查询
        List<FixedArea> fixedAreas = fixedAreaService.findAll();
        //将查询的数据压入栈顶
        ActionContext.getContext().getValueStack().push(fixedAreas);
        return SUCCESS;
    }

    /**
     * 定义信息添加
     * @return
     */
    @Action(value = "fixedArea_save",results = @Result(name = "success",type = "redirect",
            location = "./pages/base/fixed_area.html"))
    public String save(){
        System.out.println("aaa");
        //调用业务层保存数据
        fixedAreaService.save(model);
        return SUCCESS;
    }

    /**
     * 定区分页条件查询
     * @return
     */
    @Action(value = "fixedArea_pageQuery",results = @Result(name = "success",type = "json"))
    public String pageQuery(){
        //构造分页查询对象
        Pageable pageable = new PageRequest(page-1,rows);
        //构造条件查询对象
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                //构建查询条件
                if(StringUtils.isNotBlank(model.getId())){
                    //根据定区编号等值查询
                    Predicate p1 = cb.equal(root.get("id").as(String.class),model.getId());
                    list.add(p1);
                }
                if(StringUtils.isNotBlank(model.getCompany())){
                    //根据所属单位进行模糊查询
                    Predicate p2 = cb.like(root.get("company").as(String.class),model.getCompany());
                    list.add(p2);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };

        //调用业务层
        Page<FixedArea> pageData = fixedAreaService.findPageData(specification,pageable);

        //将查询结果压栈
        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }

    /**
     * 查询未关联定区的列表
     * @return
     */
    @Action(value = "fixedArea_findNoAssociationCustomers",results = @Result(name = "success",type = "json"))
    public String findNoAssociationCustomers(){
        //使用webClient调用webService接口
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:9002/crm_management/services/customerService/noassociationcustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);

        return SUCCESS;
    }

    /**
     * 查询关联定区的所有客户
     * @return
     */
    @Action(value = "fixedArea_findHasAssociationFixedAreaCustomers",results = @Result(name = "success",type = "json"))
    public String findHasAssociationFixedAreaCustomers(){
        //使用webClient调用webservice接口
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:9002/crm_management/services/customerService/associationfixedareacustomers/" + model.getId())
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);

        return SUCCESS;
    }

    //属性驱动,接收选中的已关联定区客户的id
    private String[] customerIds;

    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * 关联客户到定区
     * @return
     */
    @Action(value = "fixedArea_associationCustomersToFixedArea",
            results = @Result(name = "success",type = "redirect",
            location = "./pages/base/fixed_area.html"))
    public String associationCustomersToFixedArea(){
        String customerIdStr = StringUtils.join(customerIds,",");
        System.out.println(customerIdStr);
        WebClient.create("http://localhost:9002/crm_management/services/customerService" +
                "/associationcustomerstofixedarea?customerIdStr="
                +customerIdStr+"&fixedAreaId="+model.getId()).put(null);
        return SUCCESS;
    }

    //属性驱动
    private Integer courierId;
    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    //关联快递员到定区
    @Action(value = "fixedArea_associationCourierToFixedArea",
            results = @Result(name = "success",type = "redirect",
            location = "./pages/base/fixed_area.html"))
    public String associationCourierToFixedArea(){
        //调用业务层,定区关联快递员
        fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
        return SUCCESS;
    }

}
