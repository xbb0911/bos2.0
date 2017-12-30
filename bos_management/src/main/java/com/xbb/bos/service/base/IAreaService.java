package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 区域管理业务层接口
 * Created by xbb on 2017/12/27.
 */
public interface IAreaService {

    /**
     * 批量导入area
     *
     * @param areas
     */
    void saveBatch(List<Area> areas);

    /**
     * 区域条件分页查询
     *
     * @param specification
     * @param pageable
     * @return
     */
    Page<Area> findPageData(Specification<Area> specification, Pageable pageable);

    /**
     * 添加区域数据
     *
     * @param model
     */
    void save(Area model);

    /**
     * 查询所有区域信息
     *
     * @return
     */
    List<Area> findAll();

    /**
     * 查询所有的省份信息
     *
     * @return
     */
    List<String> findProvince();

    /**
     * 根据省份信息查询城市信息
     *
     * @param province
     * @return
     */
    List<String> findCity(String province);

    /**
     * 根据省份和城市信息查询区域信息
     *
     * @param province
     * @param city
     * @return
     */
    List<Area> findDistrict(String province, String city);

}