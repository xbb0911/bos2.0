package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.FixedAreaRepository;
import com.xbb.bos.domain.base.FixedArea;
import com.xbb.bos.service.base.IFixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定区管理service实现类
 * Created by xbb on 2017/12/28.
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements IFixedAreaService {

    //注入dao
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    /**
     * 添加定区信息的方法
     * @param model
     */
    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    /**
     * 定区信息条件查询
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }

    /**
     * 定区查询所有的方法
     * @return
     */
    @Override
    public List<FixedArea> findAll() {
        return fixedAreaRepository.findAll();
    }
}
