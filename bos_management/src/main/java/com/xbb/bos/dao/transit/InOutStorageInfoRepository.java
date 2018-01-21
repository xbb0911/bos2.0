package com.xbb.bos.dao.transit;

import com.xbb.bos.domain.transit.InOutStorageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 出入库信息管理的dao
 * Created by xbb on 2018/1/17.
 */
public interface InOutStorageInfoRepository extends JpaRepository<InOutStorageInfo,Integer>{
}
