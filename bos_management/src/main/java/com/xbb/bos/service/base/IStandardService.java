package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 收派标准页面接口
 * Created by xbb on 2017/12/23.
 */
public interface IStandardService {

    //添加收派标准
    public void save(Standard standard);

    //分页查询
    Page<Standard> findPageData(Pageable pageable);

    //查询所有收派标准
    List<Standard> findAll();
}
