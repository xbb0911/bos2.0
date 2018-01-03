package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 快递员service接口
 * Created by xbb on 2017/12/25.
 */
public interface ICourierService {

    /**
     * 添加快递员
     * @param courier
     */
    void save(Courier courier);

    /**
     * 分页查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<Courier> findPageData(Specification<Courier> specification, Pageable pageable);

    /**
     * 批量作废快递员
     * @param idArray
     */
    void delBatch(String[] idArray);

    /**
     * 批量还原快递员
     * @param idArray
     */
    void restore(String[] idArray);

    /**
     * 查询未关联定区的快递员
     * @return
     */
    List<Courier> findNoAssociation();
}
