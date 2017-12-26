package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.CourierRepository;
import com.xbb.bos.domain.base.Courier;
import com.xbb.bos.service.base.ICourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 快递员service实现类
 * Created by xbb on 2017/12/25.
 */
@Service
@Transactional
public class CourierServiceImpl implements ICourierService {

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
    public Page<Courier> findPageData(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
}
