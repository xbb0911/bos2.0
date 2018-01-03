package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定区管理的service接口
 * Created by xbb on 2017/12/28.
 */
public interface IFixedAreaService {

    /**
     * 保存定区数据
     * @param model
     */
    void save(FixedArea model);

    /**
     * 定区条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable);

    /**
     * 定区查询所有的方法
     * @return
     */
    List<FixedArea> findAll();

    /**
     * 关联快递员到定区
     * @param model
     * @param courierId
     * @param takeTimeId
     */
    void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);
}
