package com.xbb.bos.dao.take_delivery;

import com.xbb.bos.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 运单管理的dao
 * Created by xbb on 2018/1/10.
 */
public interface WayBillRepository extends JpaRepository<WayBill,Integer>{

    /**
     * 根据运单号查询运单
     * @param wayBillNum
     * @return
     */
    WayBill findByWayBillNum(String wayBillNum);
}
