package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 快递员service接口
 * Created by xbb on 2017/12/25.
 */
public interface ICourierService {

    //添加快递员
    void save(Courier courier);

    //分页查询
    Page<Courier> findPageData(Pageable pageable);
}
