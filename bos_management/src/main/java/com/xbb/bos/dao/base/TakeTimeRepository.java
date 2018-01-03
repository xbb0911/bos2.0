package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 收派时间管理的dao
 * Created by xbb on 2018/1/2.
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer>{
}
