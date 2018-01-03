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
}
