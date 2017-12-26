package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.StandardRepository;
import com.xbb.bos.domain.base.Standard;
import com.xbb.bos.service.base.IStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xbb on 2017/12/23.
 */
@Service
@Transactional
public class StandardServiceImpl implements IStandardService {
    //注入dao
    @Autowired
    private StandardRepository standardRepository;

    //添加收派标准
    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    //分页查询
    @Override
    public Page<Standard> findPageData(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    //查询所有收派标准
    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }


}
