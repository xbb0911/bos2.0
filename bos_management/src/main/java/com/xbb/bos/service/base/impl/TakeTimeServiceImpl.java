package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.TakeTimeRepository;
import com.xbb.bos.domain.base.TakeTime;
import com.xbb.bos.service.base.ITakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收派时间管理业务层实现类
 * Created by xbb on 2018/1/2.
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements ITakeTimeService {

    //注入dao
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
