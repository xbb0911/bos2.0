package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.AreaRepository;
import com.xbb.bos.domain.base.Area;
import com.xbb.bos.service.base.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域管理业务层实现类
 * Created by xbb on 2017/12/27.
 */
@Service
@Transactional
public class AreaServiceImpl implements IAreaService {

    //注入Dao
    @Autowired
    private AreaRepository areaRepository;

    /**
     * 批量导入区域信息
     * @param areas
     */
    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }

    /**
     * 区域信息分页条件查询
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<Area> findPageData(Specification<Area> specification, Pageable pageable) {
        return areaRepository.findAll(specification,pageable);
    }
}
