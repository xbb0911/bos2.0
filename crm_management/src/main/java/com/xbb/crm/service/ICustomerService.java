package com.xbb.crm.service;

import cn.itcast.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

/**
 * 客户表操作的service
 * Created by xbb on 2017/12/30.
 */
public interface ICustomerService {

    /**
     * 查询所有未关联客户列表
     * @return
     */
    @GET
    @Path("/noassociationcustomers")
    @Produces({"application/xml","application/json"})
    public List<Customer> findNoAssociationCustomers();

    /**
     * 查询已经关联到定区的客户列表
     * @param fixedAreaId
     * @return
     */
    @GET
    @Path("/associationfixedareacustomers/{fixedAreaId}")
    @Produces({ "application/xml", "application/json" })
    public List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedAreaId") String fixedAreaId);

    /**
     * 将客户关联到定区上,客户id拼接成字符串,用逗号隔开
     * @param customerIdStr
     * @param fixedAreaId
     */
    @Path("/associationcustomerstofixedarea")
    @PUT
    public void associationCustomersToFixedArea(
            @QueryParam("customerIdStr") String customerIdStr,
            @QueryParam("fixedAreaId") String fixedAreaId);


    /**
     * 用户注册信息保存
     * @param customer
     */
    @Path("/customer")
    @POST
    @Consumes({"application/xml","application/json"})
    public void regist(Customer customer);

    /**
     * 根据电话号码查询用户,判断是否已经绑定成功
     * @param telephone
     * @return
     */
    @Path("/customer/telephone/{telephone}")
    @GET
    @Consumes({"application/xml","application/json"})
    public Customer findByTelephone(@PathParam("telephone") String telephone);


    /**
     * 进行邮箱激活绑定,修改用户的type属性
     * @param telephone
     */
    @Path("/customer/updatetype/{telephone}")
    @GET
    public void updateType(@PathParam("telephone") String telephone);


}
