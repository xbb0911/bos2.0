package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 区域管理dao
 * Created by xbb on 2017/12/27.
 */
public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area>{
}
