package com.xbb.bos.service.base;

import com.xbb.bos.domain.base.TakeTime;

import java.util.List;

/**
 * 收派时间管理的业务层接口
 * Created by xbb on 2018/1/2.
 */
public interface ITakeTimeService {

    /**
     * 查询所有的收派标准
     * @return
     */
    List<TakeTime> findAll();
}
