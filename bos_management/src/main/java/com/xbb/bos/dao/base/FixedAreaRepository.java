package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 定区管理dao
 * Created by xbb on 2017/12/28.
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea,String>,JpaSpecificationExecutor<FixedArea>{
}
