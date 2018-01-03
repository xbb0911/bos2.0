package com.xbb.crm.service.test;

import com.xbb.crm.service.ICustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CXF WebService服务测试类
 * Created by xbb on 2017/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {

    //注入service
    @Autowired
    private ICustomerService customerService;

    /**
     * 测试查询未关联的客户
     */
    @Test
    public void testFindNoAssociationCustomers() {
        System.out.println(customerService.findNoAssociationCustomers());
    }

    /**
     * 测试查询关联客户
     */
    @Test
    public void testFindHasAssociationFixedAreaCustomers() {
        System.out.println(customerService
                .findHasAssociationFixedAreaCustomers("dq001"));
    }

    /**
     * 测试客户关联定区
     */
    @Test
    public void testAssociationCustomersToFixedArea() {
        customerService.associationCustomersToFixedArea("1,2", "dq001");
    }

}
