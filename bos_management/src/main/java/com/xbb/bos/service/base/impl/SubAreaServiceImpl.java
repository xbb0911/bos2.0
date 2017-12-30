package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.SubAreaRepository;
import com.xbb.bos.domain.base.SubArea;
import com.xbb.bos.service.base.ISubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分区管理service实现类
 * Created by xbb on 2017/12/28.
 */
@Service
@Transactional
public class SubAreaServiceImpl implements ISubAreaService {

    //注入dao
    @Autowired
    private SubAreaRepository subAreaRepository;

    /**
     * 添加分区的方法
     * @param model
     */
    @Override
    public void save(SubArea model) {
        //调用dao
        subAreaRepository.save(model);
    }

    /**
     * 批量导入分区
     * @param subAreas
     */
    @Override
    public void saveBatch(List<SubArea> subAreas) {
        subAreaRepository.save(subAreas);
    }

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<SubArea> findAll() {
        return subAreaRepository.findAll();
    }

    /**
     * 分页条件查询
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<SubArea> findPageData(Specification<SubArea> specification, Pageable pageable) {
        return subAreaRepository.findAll(specification,pageable);
    }
}
