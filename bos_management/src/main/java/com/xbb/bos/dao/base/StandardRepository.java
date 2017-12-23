package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 收派标准管理dao
 * Created by xbb on 2017/12/23.
 */
public interface StandardRepository extends JpaRepository<Standard,Integer>{

    //根据收派标准名称查询
    public List<Standard> findByName(String name);

}
