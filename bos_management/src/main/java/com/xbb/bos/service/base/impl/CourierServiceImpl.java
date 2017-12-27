package com.xbb.bos.service.base.impl;

import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.dao.base.CourierRepository;
import com.xbb.bos.domain.base.Courier;
import com.xbb.bos.service.base.ICourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递员service实现类
 * Created by xbb on 2017/12/25.
 */
@Service
@Transactional
public class CourierServiceImpl implements ICourierService,ModelDriven<Courier>{

    private Courier courier = new Courier();
    @Override
    public Courier getModel() {
        return courier;
    }

    //注入Dao对象
    @Autowired
    private CourierRepository courierRepository;

    //添加快递员
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    //分页查询
    @Override
    public Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    //批量作废快递员
    @Override
    public void delBatch(String[] idArray) {
        //调用dao实现update修改操作,将deltag修改为1
        for (String idStr : idArray){
            Integer id = Integer.parseInt(idStr);
            courierRepository.updateDeltag(id);
        }
    }

    //批量还原快递员
    @Override
    public void restore(String[] idArray) {
        //调用dao完成还原操作
        for (String str : idArray){
            Integer id = Integer.parseInt(str);
            courierRepository.updateRestore(id);
        }
    }

}
