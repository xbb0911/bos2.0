package com.xbb.bos.dao.transit;

import com.xbb.bos.domain.transit.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 配送信息管理的dao
 * Created by xbb on 2018/1/18.
 */
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Integer>{
}
