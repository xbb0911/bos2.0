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

    /**
     * 添加区域数据
     * @param model
     */
    @Override
    public void save(Area model) {
        areaRepository.save(model);
    }

    /**
     * 查询所有的区域信息
     * @return
     */
    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    /**
     * 查询所有的省份信息
     * @return
     */
    @Override
    public List<String> findProvince() {
        return areaRepository.findProvince();
    }

    /**
     * 根据省份信息查询所有的城市信息
     * @param province
     * @return
     */
    @Override
    public List<String> findCity(String province) {
        return areaRepository.findCity(province);
    }

    /**
     * 根据省份和城市信息查询区域信息
     * @param province
     * @param city
     * @return
     */
    @Override
    public List<Area> findDistrict(String province, String city) {
        return areaRepository.findDistrict(province,city);
    }


}
