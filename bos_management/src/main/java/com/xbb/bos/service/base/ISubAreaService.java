package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 分区管理service接口
 * Created by xbb on 2017/12/28.
 */
public interface ISubAreaService {

    /**
     * 增加定区的方法
     * @param model
     */
    void save(SubArea model);

    /**
     * 批量导入分区
     * @param subAreas
     */
    void saveBatch(List<SubArea> subAreas);

    /**
     * 查询所有数据
     * @return
     */
    List<SubArea> findAll();

    /**
     * 分页条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<SubArea> findPageData(Specification<SubArea> specification, Pageable pageable);
}
