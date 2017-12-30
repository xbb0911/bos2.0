package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 分区管理dao
 * Created by xbb on 2017/12/28.
 */
public interface SubAreaRepository extends JpaRepository<SubArea,String>,JpaSpecificationExecutor<SubArea>{
}
