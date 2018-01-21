package com.xbb.bos.dao.transit;

import com.xbb.bos.domain.transit.TransitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 运单中转配送管理的dao
 * Created by xbb on 2018/1/17.
 */
public interface TransitInfoRepository extends JpaRepository<TransitInfo,Integer>{
}
