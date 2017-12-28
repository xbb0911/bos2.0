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
     * @param areas
     */
    void saveBatch(List<Area> areas);

    /**
     * 区域条件分页查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<Area> findPageData(Specification<Area> specification, Pageable pageable);
}
