package com.xbb.crm.service.impl;

import cn.itcast.crm.domain.Customer;
import com.xbb.crm.dao.CustomerRepository;
import com.xbb.crm.service.ICustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户表操作的service的实现类
 * Created by xbb on 2017/12/30.
 */
@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

    //注入dao
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 查询所有未关联客户列表
     * @return
     */
    @Override
    public List<Customer> findNoAssociationCustomers() {
        //fixedAreaId is null
        return customerRepository.findByFixedAreaIdIsNull();
    }

    /**
     * 查询已经关联到定区的客户列表
     * @param fixedAreaId
     * @return
     */
    @Override
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    /**
     * 将客户关联到定区上,客户id拼接成字符串,用逗号隔开
     * @param customerIdStr
     * @param fixedAreaId
     */
    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        //解除关联
        customerRepository.clearFixedAreaId(fixedAreaId);

        //切割字符串1,2,3
        if(StringUtils.isBlank(customerIdStr)){
            return;
        }
        String[] customerIdArray = customerIdStr.split(",");
        for (String idStr : customerIdArray) {
            Integer id = Integer.parseInt(idStr);
            customerRepository.updateFixedAreaId(fixedAreaId,id);
        }
    }
}
